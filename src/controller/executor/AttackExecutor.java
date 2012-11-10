package controller.executor;

import java.util.Random;

import view.StandardMessageGenerator;

import controller.parser.Command;
import controller.parser.CommandType;

import model.gameEngine.GameEngineInterface;
import model.gameEngine.ExecutorEvent;

import model.world.*;
import view.BrowserTextHandler;


/**
 * This class manages the player attacking NPCs and vice versa. It's separate from the command executor because
 * attacking is quite a complicated process, so we thought separating it from the rest of our code would make
 * things less messy.
 * 
 * This class has an inner class called FightSeqencer that runs in it's own thread to allow wait() calls without blocking
 * the UI thread.
 * 
 * @author Llamas / (Yaks)
 */
public class AttackExecutor extends Executor {
	/*
	 * Instance variables
	 */					
	private boolean attackSequencingDisabled = false;		// Whether attack sequencing is disabled (if it is, attacks don't contain waits)



	/**
	 * Constructor
	 * @param	gameEngine	Object controlling the game
	 */
	public AttackExecutor(GameEngineInterface gameEngine) {
		super(gameEngine);
	}	



	/**
	 * Execute attack based on the input received
	 * @param	event	Input event to execute
	 */
	@Override
	public void handleInput(ExecutorEvent event) {

		Command command = event.getCommand();
		// Ensure command should be executed by this executor
		if(event.getCommand().getType() == CommandType.ATTACK) {
			this.attack(command.getReference());
		}
		// Unregister self as input handler as have completed managing input
		this.gameEngine.getInputHandler().popExecutor();
	}


	/**
	 * Make the player attack the given entity or vice versa
	 * @param	referenceEntity	The entity to be attacked
	 */
	public void attack(WorldEntity referenceEntity) {
		StandardMessageGenerator standardMessageGenerator = this.gameEngine.getStandardMessageGenerator();
		RoomInventory roomInventory =  this.getPlayer().getCurrentRoom().getInventory();

		if(referenceEntity instanceof Npc) {
			if(referenceEntity instanceof HostileNpc) {
				if(!roomInventory.contains((HostileNpc) referenceEntity)) {
					// Hostile NPC not in same room therefore is not accessible
					standardMessageGenerator.nonAccessibleNpcInteractionRequest(
							new Command(CommandType.ATTACK, referenceEntity)
							);
				}
				else {

					FightSequencer fightSequencer = new FightSequencer((HostileNpc) referenceEntity, this);
					if(!this.attackSequencingDisabled) {
						// Thread sequencer
						fightSequencer.start();
					}
					else {
						// Do not thread sequencer (used in testing)
						fightSequencer.startAttack();
					}
				}
			}
			else if(referenceEntity instanceof FriendlyNpc) {
				this.attackFriendlyNpc((FriendlyNpc) referenceEntity);
			}
		}

		else if(referenceEntity instanceof Item) {
			if(!roomInventory.contains((Item) referenceEntity)
					&& !this.getPlayer().getInventory().contains((Item) referenceEntity)) {
				// Item not accessible to use
				standardMessageGenerator.unknownInteractionEntity(CommandType.ATTACK);
			}
			this.attackItem((Item) referenceEntity);
		}

		else if(referenceEntity == null) {
			// No entity specified
			this.gameEngine.getStandardMessageGenerator().unknownInteractionEntity(CommandType.ATTACK);
		}

		else {
			// Not attackable entity
			this.gameEngine.getStandardMessageGenerator().cannotAttack(referenceEntity);
		}
	}


	/**
	 * Disable the attacks from been sequenced (threading then waiting is disabled)
	 * (Used to enable testing) 
	 */
	public void disableAttackSequencing() {
		this.attackSequencingDisabled = true;
	}



	// Private methods
	/*
	 * Attempt to attack a friendly NPC
	 * @param	friendlyNpc	Friendly NPC that an attack attempt should be made upon
	 */
	private void attackFriendlyNpc(FriendlyNpc friendlyNpc) {
		this.gameEngine.getStandardMessageGenerator().cannotAttackFriendlyNpc(friendlyNpc);
	}


	/*
	 * Attack an item
	 * @param	item	Item to attack
	 */
	private void attackItem(Item item) {
		if(item.isAttackable()) {

			//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
			this.addToUndoStack(CommandType.ATTACK);

			CharacterInventory playerInventory = this.getPlayer().getInventory(); 
			if(playerInventory.contains(item)) {
				// Item in player's inventory
				playerInventory.removeItem(item);
			}
			else {
				// Item must be within the same room as the player
				this.getPlayer().getCurrentRoom().getInventory().removeItem(item);
			}
			this.gameEngine.getStandardMessageGenerator().itemAttackedAndDestroyed(item);
		}
		else {
			this.gameEngine.getStandardMessageGenerator().cannotAttack(item);
		}
	}


	/*
	 * Get the player from the current game world
	 * @return	Current game world player
	 */
	private Player getPlayer() {
		return this.gameEngine.getWorld().getPlayer();
	}




	// ----------------------------INNER CLASS BELOW-----------------------
	/*
	 * This inner class handles battles between the player and NPCs.
	 * 
	 * This is an inner class that starts a thread. We start a new thread because you can't wait or sleep in the UI thread but we want
	 * waiting and sleeping so that attack sequences don't end instantly. This thread is started when a battle happens and at that point all user input is 
	 * disabled. The battle happens over the course of a few seconds and then input is enabled again.
	 */
	private class FightSequencer extends Thread {
		/*
		 * Settings
		 */
		private final int WAIT_BETWEEN_STRIKES = 1000;			// Time to wait (in milliseconds) between strikes within a fight



		/*
		 * Instance variables
		 */
		private HostileNpc hostileNpc;							// The NPC player is attacking
		private AttackExecutor attackExecutor;					//The attack executor
		
		private boolean firstRound;								// Whether or not to check for initiative



		/**
		 * Constructor
		 * @param	hostileNpc	NPC player is attacking
		 */
		public FightSequencer(HostileNpc hostileNpc, AttackExecutor attackExecutor) {
			super();
			this.hostileNpc = hostileNpc;
			this.attackExecutor = attackExecutor;
			this.firstRound = true;
		}



		/**
		 * Run as thread
		 */
		@Override
		public void run() {
			this.startAttack();
		}



		/*
		 * Player to attack hostile non-playable character
		 */
		public void startAttack() {
			BrowserTextHandler textInput = AttackExecutor.this.gameEngine.getBrowser();
			StandardMessageGenerator standardMessageGenerator = AttackExecutor.this.gameEngine.getStandardMessageGenerator();
			Random random = new Random();
			Weapon playerWeapon = AttackExecutor.this.getPlayer().getWieldedWeapon();
			Weapon npcWeapon = this.hostileNpc.getWieldedWeapon();

			// Stop the user from interacting with the game while the battle is going on
			textInput.disableInput();

			if (npcWeapon == null && playerWeapon == null) {
				// Player and non hostile NPC have no weapons, therefore attack cannot occur
				standardMessageGenerator.duelNotPossible(this.hostileNpc);
			}
			else {

				//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
				this.attackExecutor.addToUndoStack(CommandType.ATTACK);

				do {	
					// Display to user health levels of adversaries
					standardMessageGenerator.stateAdversariesHealthLevels(this.hostileNpc);

					// Only sequence attacks using waits if attack sequencing has not been disabled
					if(!AttackExecutor.this.attackSequencingDisabled) {
						try {
							Thread.sleep(this.WAIT_BETWEEN_STRIKES);
						}
						catch(InterruptedException e) {
							// Problem occurred when sleeping - bypass sleep
						}
					}

					// Randomly select who strikes in this round of combat
					if(doesPlayerHit(playerWeapon, npcWeapon, random)) {		 // Player doesn't attack if he has no weapon
						// Player to attack first
						int damage = 0;
						if(playerWeapon.getMaxDamage() != 0){
							if (playerWeapon.getMaxDamage() == playerWeapon.getMinDamage()){       
								damage = playerWeapon.getMaxDamage();							//Deal out max damage if max and min are the same
							}else{
								damage = playerWeapon.getMinDamage() + random.nextInt(playerWeapon.getMaxDamage() - playerWeapon.getMinDamage()); 
							}
							this.hostileNpc.decreaseHealth(damage);
						}
						standardMessageGenerator.playerDamagesHostileNpc(this.hostileNpc, damage);
					}
					else { 													// NPC doesn't attack if it has no weapon
						// NPC to attack first
						int damage = 0;
						if(npcWeapon.getMaxDamage() != 0){
							if (npcWeapon.getMaxDamage() == npcWeapon.getMinDamage()){
								damage = npcWeapon.getMaxDamage();
							}else{
								damage = npcWeapon.getMinDamage() + random.nextInt(npcWeapon.getMaxDamage() - npcWeapon.getMinDamage());
							}
							getPlayer().decreaseHealth(damage);

						}
						standardMessageGenerator.hostileNpcDamagesPlayer(this.hostileNpc, damage);
					}
				}
				while (!this.hasACombatantDied()); 		//Loop until either player or NPC dies
			}

			// Allow the user to start interacting with the UI again
			textInput.enableInput(); 
		}



		// Private methods
		/*
		 * Checks to see if the player or the hostileNpc has been killed after that round of combat
		 * @return	Whether either the player or the hostileNpc was killed
		 */
		private boolean hasACombatantDied() {
			if(AttackExecutor.this.getPlayer().getCurrentHealth() == 0) {
				// Player is dead
				AttackExecutor.this.gameEngine.onPlayerDeath();
				return true;
			}
			else if(this.hostileNpc.getCurrentHealth() == 0) {
				// Hostile NPC is dead
				RoomInventory roomInventory = AttackExecutor.this.getPlayer().getCurrentRoom().getInventory(); 

				// Place NPC's items into room inventory
				for(Item item : this.hostileNpc.getInventory().getItems()) {
					roomInventory.addItem(item);
				}

				//Remove hostile NPC from room
				roomInventory.removeCharacter(this.hostileNpc);

				// Inform user that hostile NPC is dead
				AttackExecutor.this.gameEngine.getStandardMessageGenerator().hostileNpcKilled(
						this.hostileNpc
						);

				return true;
			}

			return false;
		}
		
		/*
		 * Determines who strikes this round, using ease of use to weight probabilities.
		 * @return	  true if the player strikes, false if the NPC strikes.
		 */
		private boolean doesPlayerHit(Weapon playerWeapon, Weapon npcWeapon, Random random)
		{
			if (playerWeapon == null) return false;
			if (npcWeapon == null)    return true;
			if (this.firstRound) {
				this.firstRound = false;
				return !this.hostileNpc.hasInitiative();
			}
			return random.nextInt(playerWeapon.getEaseOfUse() + npcWeapon.getEaseOfUse()) < playerWeapon.getEaseOfUse();
		}
	}
}

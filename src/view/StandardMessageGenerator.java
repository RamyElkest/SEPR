package view;

import model.gameEngine.GameEngineInterface;

import model.world.FriendlyNpc;
import model.world.HostileNpc;
import model.world.Item;
import model.world.Npc;
import model.world.Room;
import model.world.Weapon;
import model.world.WorldEntity;

import controller.parser.Command;
import controller.parser.CommandType;


/**
 * Contains methods that generates and print standard messages to the user based on 0 or more arguments, helping to centralise 
 * message creation so that messages can easily be modified in one place.
 * For example, there could be a help() method that prints "You can MOVE, LOOK or get" or a fight(combatant1, combatant2)
 * method that prints "combatant1 just hit combatant2!"
 * @author Llamas / Yaks
 */
public class StandardMessageGenerator {
	/*
	 * Instance variables
	 */
	private GameEngineInterface gameEngine;	//The gameEngine instance



	/**
	 * Constructor - Initialises the field
	 * 
	 * @param	gameEngine	The GameEngine
	 */
	public StandardMessageGenerator(GameEngineInterface gameEngine) {
		super();
		this.gameEngine = gameEngine;
	}


	/**
	 * Outputs a message to the UI signifying a general error inside the program.
	 */
	public void internalError() {
		this.gameEngine.getBrowser().println("Unfortuantely something wrong inside the game.");
	}


	/**
	 * Outputs a message to the UI signifying that there are no more commands to be undone
	 */
	public void undoEmptyStackError() {
		this.gameEngine.getBrowser().println("There are no more commands to be undone!!!");
	}

	/**
	 * Outputs a message to the UI signifying the command type given by the user couldn't be understood.
	 */
	public void invalidCommandType() {
		this.gameEngine.getBrowser().println(
				"What am I doing? I think I'm just shouting jibberish.\n" +
						"I need to think carefully about what I do. I could do with some HELP..."
				);
	}


	/**
	 * Outputs a message to the UI signifying a command that needs an entity to act upon (such as the chair in USE chair)
	 * did not have an entity to act upon or the entity wasn't recognised.
	 */
	public void missingReferenceEntity(CommandType attemptedCommand) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" paused for a second: \"Wait, what am I trying to "+  attemptedCommand.toString() +
				" to? I must specifiy the name of the entity that I want to "+ attemptedCommand.toString() + "; perhaps "+
				"LOOKing around will help me?\""
				);
	}


	/**
	 * Outputs a message to the UI signifying the player successfully moved rooms.
	 */
	public void playerMovedRoom(Room room) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" moved to "+ room.getName() +"\n"+
						room.getName() +": "+ room.getDescription()
				);
	}

	/**
	 * Outputs the Rooms the player is currently in and its decription
	 * @param room
	 */
	public void showCurrentPlayerRoom(Room room){
		this.gameEngine.getBrowser().println(
				this.getPlayerName() + " is currently in the "+ room.getName() +"\n" +
						room.getName() + ": "+ room.getDescription());			
	}

	/**
	 * Outputs a message to the UI signifying the player failed to move rooms because it is not accessible from their current location.
	 */
	public void playerFailedToMoveRoom(String newRoomName, String currentRoomName) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot move to "+ newRoomName
				+" because it is not accessible from their current location: "+ currentRoomName
				);
	}


	/**
	 * Player is already within the room they wish to move to
	 */
	public void playerAlreadyInRoom() {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" is already in "+ this.gameEngine.getWorld().getPlayer().getCurrentRoom().getName()
				);
	}


	/**
	 * Outputs a message to the UI signifying the player failed to move rooms because the given new location isn't a room.
	 */
	public void canOnlyMoveToARoom(String referenceEntityName, String currentRoomName) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot move to "+ referenceEntityName
				+" because it is not a room! " + this.getPlayerName() + " is still in " + currentRoomName
				);
	}


	/**
	 * Outputs a message to the UI signifying the player attempted to talk to an entity but it didn't respond to him/her.
	 */
	public void playerFailedToTalkToEntity(String entityName) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" starts to talk to "+ entityName +" but it does not respond."
				);
	}


	/**
	 * Outputs a help message to the UI.
	 */
	public void help() {
		this.gameEngine.getBrowser().println(
				"You can MOVE somewhere, ATTACK something, TALK to someone, LOOK at something, " +
						"USE something, GET something, look at your INVENTORY, view your remaining OBJECTIVES, " +
						"QUIT, SAVE, LOAD, Save the SCRIPT, DROP something, UNDO or travel to a RANDOM location " +
						"that you've been to before."
				);
	}


	/**
	 * Outputs a message to the UI signifying the player tried to GET something that wasn't an item (maybe an NPC or a Room),
	 * as such, the action failed.
	 */
	public void canOnlyGetItems(String nonItemEntityName) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() + " must be going a bit crazy. He can only GET items! It's impossible to" +
						" GET the " + nonItemEntityName + "."
				);
	}


	/**
	 * A duel is not possible between player and hostile NPC
	 */
	public void duelNotPossible(HostileNpc hostileNpc) {
		this.gameEngine.getBrowser().println(
				"A duel cannot occur between "+ this.getPlayerName() +" and "+ hostileNpc.getName() +
				" because no one has a weapon!"
				);
	}


	/**
	 * Hostile NPC killed
	 */
	public void hostileNpcKilled(HostileNpc hostileNpc) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" defeated "+ hostileNpc.getName() +"!\n" +
						"Any items "+ hostileNpc.getName() +" was carrying have fallen to the floor."
				);
	}


	/**
	 * Cannot attack friendly NPC
	 */
	public void cannotAttackFriendlyNpc(FriendlyNpc friendlyNpc) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot attack "+ friendlyNpc.getName() +" because " +
						friendlyNpc.getName() +" is friendly!"
				);
	}


	/**
	 * Player attacked and destroyed an item
	 */
	public void itemAttackedAndDestroyed(Item item) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" attacked and destroyed the "+ item.getName() +"!"
				);
	}


	/**
	 * Player cannot attack entity in world
	 * @param	worldEntity	World entity player is attempting to attack (if any)
	 */
	public void cannotAttack(WorldEntity worldEntity) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot attack "+ worldEntity.getName() +"."
				);
	}


	/**
	 * Using entity has no effect or can't be used
	 * @param	worldEntity	World entity player is attempting to use (if any)
	 */
	public void usingHasNoEffect(WorldEntity worldEntity) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" wonders how to use the "+ worldEntity.getName() + "... \"I don't think I know how to use this\"."
				);
	}


	/**
	 * Print weapon statistics
	 */
	public void weaponStatistics(Weapon weapon) {
		this.gameEngine.getBrowser().println(
				"Weapon Statistics:\n"+
						"\tMinimum Damage = "+ weapon.getMinDamage() +
						"\tMaximum Damage = "+ weapon.getMaxDamage() +
						"\tEase of Use = "+ weapon.getEaseOfUse()
				);
	}


	/**
	 * Prints the "player died" message
	 */
	public void playerDied() {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() + " died!"
				);
	}


	/**
	 * Prints the "game complete" message
	 */
	public void gameComplete() {
		this.gameEngine.getBrowser().println(
				"Congratulations: " + this.getPlayerName() + " has completed the game!"
				);
	}


	/**
	 * Inform the user that their player were attacked when they entered a room
	 */
	public void attackedUponRoomEnter(HostileNpc attacker) {
		this.gameEngine.getBrowser().println(
				attacker.getName() +" suddenly jumps out at "+ this.getPlayerName() +" and attempts to attack!"
				);
	}


	/**
	 * Player attacks and damages hostile NPC
	 */
	public void playerDamagesHostileNpc(HostileNpc hostileNpc, int damage) {
		String pointsPostfix = (damage == 1 ? "" : "s"); //Puts an s on the end of "point" to make it plural if multiple points of damage have been dealt
		this.gameEngine.getBrowser().println(
				this.getPlayerName() + " attacks "+ hostileNpc.getName() +", dealing "+ damage +
				" point"+ pointsPostfix +" of damage."
				);
	}


	/**
	 * Hostile NPC attacks and damages the player 
	 */
	public void hostileNpcDamagesPlayer(HostileNpc hostileNpc, int damage) {
		String pointsPostfix = (damage == 1 ? "" : "s"); //Puts an s on the end of "point" to make it plural if multiple points of damage have been dealt
		this.gameEngine.getBrowser().println(
				hostileNpc.getName() + " attacks "+ this.getPlayerName() +", dealing "+ damage +
				" point"+ pointsPostfix +" of damage."
				);
	}


	/**
	 * State adversaries health levels
	 */
	public void stateAdversariesHealthLevels(HostileNpc opponent) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +"'s health: "+ this.gameEngine.getWorld().getPlayer().getCurrentHealth() +"\t\t"+
						opponent.getName() +"'s health: "+ opponent.getCurrentHealth()
				);
	}


	/**
	 * Ask if user wishes to save before exiting
	 */
	public void askIfWishToSaveBeforeExitting() {
		this.gameEngine.getBrowser().println(
				"Do you want to save before you exit? (y/n/cancel)"
				);
	}


	/**
	 * Save failed - ask if user wishes to save again
	 */
	public void askIfWishToAttemptToSaveAgain() {
		this.gameEngine.getBrowser().println(
				"Do you want to attempt to save your game again before exiting? (y/n/cancel)"
				);
	}


	/**
	 * Cannot interact (USE, ATTACK, etc) with entity because it is not currently accessible to the player
	 * or doesn't exist
	 * @param	commandType	Type of command being used
	 */
	public void unknownInteractionEntity(CommandType commandType) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" glances around the room: \"Er, what am I trying to "+  commandType.toString() +" again?\n" +
						"I can only "+  commandType.toString() + " things that I saw when I LOOKed around the room or things in my INVENTORY.\""
				);
	}


	/**
	 * Cannot interact (TALK, ATTACK, etc) with NPC because it is not currently accessible to the player
	 * @param	command	Command requesting the interaction
	 */
	public void nonAccessibleNpcInteractionRequest(Command command) {
		Npc npc = (Npc) command.getReference();
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot "+  npc.getName() +" " + command.getType().toString() +
				" because " + npc.getName() + " isn't in this room!"
				);
	}

	/**
	 * Cannot use a weapon because the inventory is full. (Using a weapon adds it to your inventory)
	 * @param weapon The weapon the player attempted to use
	 */
	public void connotUseWeaponBecauseInventoryIsFull(Weapon weapon) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot "+  CommandType.USE.toString() +" the " + weapon.getName() +
				" because " + this.getPlayerName() + "'s inventory is full - drop an item to make room for it." +
				"(Wielded weapons are kept in the inventory and drawn when "+ this.getPlayerName()+ " enters a battle)"
				);
	}


	/**
	 * Ask user if they wish to restart the default game 
	 */
	public void restartGameQuery() {
		this.gameEngine.getBrowser().println(
				"Available Options: Replay the default game world? (d/default), Load a saved game (s/saved), or Quit (q/quit)"
				);
	}


	/**
	 * Weapon is not accessible to player, therefore they cannot wield it
	 * @param	weapon	Weapon the player has attempted to wield
	 */
	public void weaponNotAccessible(Weapon weapon) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot wielded "+ weapon.getName() +" because it is not in their inventory nor in the current room"
				);
	}

	/**
	 * Player tried to drop something that was not an item
	 * @param name - name of the thing they tried to drop
	 */
	public void canOnlyDropItems(String name) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot drop the "+ name +" not only because they are not carrying it but because and it can not be picked up "
				);	
	}

	/**
	 * Player tried to drop an item that was not in their inventory
	 * @param item
	 */		
	public void InventoryitemNotAccessible(Item item) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot drop the "+ item.getName() +" because they are not carrying it"
				);

	}


	/**
	 * Item is not accessible for player to get 
	 * @param	item	Item player attempted to get
	 */
	public void itemNotAccessible(Item item) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot get the "+ item.getName() +" because it does not exist within the current room"
				);
	}


	/**
	 * Player takes item from room  
	 * @param	item	Item player took from the room
	 */
	public void playerGetsItem(Item item) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" took the "+ item.getName() +" from the room and placed it in their inventory"
				);
	}

	/**
	 * Player drops wielded weapon to the room  
	 * @param	item	previously wielded Weapon player drops to the room
	 */
	public void playerDropsWieldedWeapon(Weapon weapon) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" dropped the "+ weapon.getName() +" that they were wielding to the room"
				);
	}
	
	
	/**
	 * The layer drops an item to the room
	 * @param item the item being dropped
	 */
	public void DroppedItem(Item item) {
		this.gameEngine.getBrowser().println("The " + item.getName() + " fell to the floor");
	}

	
	/**
	 * Player cannot carry the item they wish to get
	 * @param	item	Item player wishes to get
	 */
	public void playerCannotCarryItem(Item item) {
		this.gameEngine.getBrowser().println(
				this.getPlayerName() +" cannot carry "+ item.getName()
				);
	}
	
	/**
	 * Player has been moved to a random, previously visited room
	 */
	public void MovedToRandomRoom() {
		this.gameEngine.getBrowser().println(this.getPlayerName() + 
			" is randomly moved to a Previously visited room: ");
	}
		

	/**
	 * The player cancelled the quit command.
	 */
	public void quitCancelled() {
		this.gameEngine.getBrowser().println(
				"The quit was cancelled."
				);
	}
	

	/**
	 * Player transforms an item into something else
	 * @param item
	 * @param transforms
	 */
	public void transformItem(Item item, Item transforms) {
		this.gameEngine.getBrowser().println(getPlayerName()+" casts some magic and turns "+
				item.getName()+" into a "+transforms.getName());
	}
	
	/**
	 * Player tries to transform something into something else
	 * @param name
	 */
	public void cannotTransformItem(WorldEntity entity) {
		gameEngine.getBrowser().println(getPlayerName()+" tried to transform the "+entity.getName()+
				" but failed");
	}
	

	public void itemCanBeTransformed(Item item) {
		gameEngine.getBrowser().println(item.getName()+
			" can be transformed... but not into that.");
	}


	// Private methods
	/*
	 * Get the player's name within the current game world
	 * @return	Player's name
	 */
	private String getPlayerName() {
		return this.gameEngine.getWorld().getPlayer().getName();
	}


	public void InventoryFull(Item item) {
		this.gameEngine.getBrowser().println("The " + item.getName() + " could not be taken as " + this.getPlayerName()+"'s inventory is full" );
	}



	
}
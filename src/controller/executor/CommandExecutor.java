package controller.executor;

import java.util.ArrayList;
import java.util.Random;

import model.gameEngine.GameEngineInterface;
import model.gameEngine.ExecutorEvent;

import model.world.Character;
import model.world.CharacterInventory;
import model.world.HostileNpc;
import model.world.Item;
import model.world.Npc;
import model.world.Player;
import model.world.Room;
import model.world.RoomInventory;
import model.world.Weapon;
import model.world.WorldEntity;

import controller.parser.Command;
import controller.parser.CommandType;
import exceptions.InventoryFullException;


/**
 * The CommandExecutor takes a Command and applies it to the world.
 * 
 * CommandExecutor is responsible for executing commands that interact with the game's world (FR2.1.x).
 * Game system commands (FR2.2.x) require access to the World object, for instance: when a save file is
 * loaded with a LOAD command). They are handled directly by GameEngine as follows:
 * 
 * LOAD  - The World object will be entirely replaced with the new World, created by the WorldFactory
 * SAVE - Access to the World object and WorldStorageHandler is required.
 * SCRIPT - Needs access to the input and output log from TextInput and TextDisplay.
 * RANDOM - The list of Rooms the player has visited is required.
 * UNDO - As with LOAD, the World object will be replaced but with its previous state.
 * 
 * The QUIT command is a special case. When a QUIT command is received, the game puts itself into a
 * 'confirmation state' where the next command is expected to either be a YES or a NO command which can
 * be acted on accordingly.
 * 
 * @author Llamas / Yaks
 */ 
public class CommandExecutor extends Executor{	

	/**
	 * Constructor
	 * @param	gameEngine	The GameEngine in which commands should be executed
	 */
	public CommandExecutor(GameEngineInterface gameEngine) {
		super(gameEngine);
	}



	/**
	 * Execute command based on the input received
	 * @param	event	Input event to execute
	 */
	@Override
	public void handleInput(ExecutorEvent event) {
		this.execute(event.getCommand());
	}


	/**
	 * Executes the given Command within the gameEngine given in the constructor of this CommandExecutor.
	 * @param	command	The command to be executed
	 */
	public void execute(Command command) {

		// The entity referenced by the command. For example if the user types "use key", then the key is the reference entity
		WorldEntity referenceEntity = command.getReference();					
                
		//Switch system commands
		switch (command.getType()) {


		case LOAD:
			//Manage with gameEngine directly
			this.gameEngine.load();
			return;


		case SAVE:
			//Manage with gameEngine directly
			this.gameEngine.save();
			return;


		case SCRIPT:
			//Manage with gameEngine directly
			this.gameEngine.script();
			return;


		case RANDOM:
			this.random();
			return;


		case UNDO:	
			//Manage with gameEngine directly
			this.gameEngine.undo();
			return;


		case INVENTORY:
			this.inventory();
			return;

		case LOOK:
			if(referenceEntity == null) {
				// Default to looking at current room if no referenceEntity is specified
				referenceEntity = this.getPlayer().getCurrentRoom();
			}
			this.look(referenceEntity); 
			return;


		case HELP:
			// Give player advice
			this.help();
			return;


		case OBJECTIVES:
			// Display objects that the player has left to complete
			this.displayObjectives();
			return;


		case INVALID_TYPE:
			//Handle this error by not executing anything and outputting a message to the user
			this.gameEngine.getStandardMessageGenerator().invalidCommandType();
			//Perhaps have a method that outputs general helpful comments on the subject of input syntax and call that method here
			return;
		}


		if(command.getReference() != null) {
			// These commands require a game entity reference to work

			switch (command.getType()) {


			case MOVE:
				if (referenceEntity instanceof Room) { // Check the player is trying to move to a Room and not some other sort of entity
					this.move((Room) referenceEntity);
				}
				else {
					this.gameEngine.getStandardMessageGenerator().canOnlyMoveToARoom(
							referenceEntity.getName(),
							this.getPlayer().getCurrentRoom().getName()
							);
				}
				break;


			case GET:
				if (referenceEntity instanceof Item) { // Check that the player is trying to get an item and not another type of entity (such as a room)
					this.get((Item) referenceEntity);
				}
				else {
					this.gameEngine.getStandardMessageGenerator().canOnlyGetItems(referenceEntity.getName());
				}
				break;


			case DROP:
				if (referenceEntity instanceof Item) { // Check that the player is trying to drop an item and not another type of entity (such as a room)
					this.drop((Item) referenceEntity);
				}
				else {
					this.gameEngine.getStandardMessageGenerator().canOnlyDropItems(referenceEntity.getName());
				}
				break;


			case TALK:	
				this.talk(referenceEntity);
				break;
			}
		} else {
			// Reference entity required for instructions
			this.gameEngine.getStandardMessageGenerator().missingReferenceEntity(command.getType());
		}
	}


	//Private Methods


	/*
	 * Get the player from the current game world
	 * @return	Current game world player
	 */
	private Player getPlayer() {
		return this.gameEngine.getWorld().getPlayer();
	}

	/*
	 * Random moves the player to a random, previously visited room
	 */
	public void random() {
		Random r = new Random();
		this.getPlayer().setCurrentRoom(this.getPlayer().getVisitedRooms().get(r.nextInt(this.getPlayer().getVisitedRooms().size())));
		this.gameEngine.getStandardMessageGenerator().MovedToRandomRoom();
		this.gameEngine.getStandardMessageGenerator().showCurrentPlayerRoom(this.getPlayer().getCurrentRoom());
	}

	/*
	 * Move character to a specified room
	 * @param	newLocation	Location to move character to
	 */
	private void move(Room newLocation) {
		if(this.getPlayer().getCurrentRoom().getAdjoiningRooms().contains(newLocation)) {

			//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
			addToUndoStack(CommandType.MOVE);

			// Move the player to the new location
			this.getPlayer().setCurrentRoom(newLocation);
			this.gameEngine.getStandardMessageGenerator().playerMovedRoom(
					this.getPlayer().getCurrentRoom()
					);

			// There is a possibility that a player may be attacked by a hostileNpc in the room when they enter
			HostileNpc attacker = this.getAttackerUponRoomEnter();
			if(attacker != null) {
				// Player is to be attacked when they enter the room
				this.gameEngine.getStandardMessageGenerator().attackedUponRoomEnter(attacker);
				
				// Attacker strikes first
				attacker.giveInitiative();
				
				// Call the attack executor by attack command to input handler's input executor router 
				this.gameEngine.getInputHandler().routeInputToExecutor(
						null, new Command(CommandType.ATTACK, attacker)
						);
			}
		}
		else if(newLocation == this.getPlayer().getCurrentRoom()) {
			// Player already in room
			this.gameEngine.getStandardMessageGenerator().playerAlreadyInRoom();
		}
		else {
			// Could not move player to room
			this.gameEngine.getStandardMessageGenerator().playerFailedToMoveRoom(
					newLocation.getName(),
					this.getPlayer().getCurrentRoom().getName()
					);
		}
	}


	/*
	 * Get (if any) a hostile NPC that wishes to attack the player upon entering a room
	 * @return	Hostile NPC that attacks the player else null if no-one attacks
	 */
	private HostileNpc getAttackerUponRoomEnter() {

		ArrayList<Character> characters = this.getPlayer().getCurrentRoom().getInventory().getCharacters();
		Random randomGenerator = new Random();

		for(Character character : characters) {
			if(character instanceof HostileNpc) {
				// Test if hostile NPC wishes to attack the player based on their attack probability
				HostileNpc hostileNpc = (HostileNpc) character;
				if(randomGenerator.nextDouble() <= hostileNpc.getAttackProbability()) {
					// Hostile NPC wishes to attack
					return hostileNpc;
				}
			}
		}
		return null;
	}


	/*
	 * Make the player talk to the given entity
	 * @param	newLocation	Location to move character to
	 */
	private void talk(WorldEntity referenceEntity) {
		if(referenceEntity instanceof Npc) {

			//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
			addToUndoStack(CommandType.TALK);

			this.talk((Npc) referenceEntity);
		}else{
			this.gameEngine.getStandardMessageGenerator().playerFailedToTalkToEntity(referenceEntity.getName());
		}
	}


	/*
	 * List the contents of the inventory
	 */
	private void inventory() {
		CharacterInventory characterInventory = this.getPlayer().getInventory();

		if(characterInventory.getNumberOfItems() == 0) {
			this.gameEngine.getBrowser().println("Inventory:\nNothing!");
		}
		else {
			this.gameEngine.getBrowser().println(
					this.getPlayer().getInventory().toString()
					);
		}

		Weapon wieldedWeapon = this.getPlayer().getWieldedWeapon();
		if(wieldedWeapon != null) {
			this.gameEngine.getBrowser().println(
					"Wielded weapon:\n- "+ wieldedWeapon.getName()
					);
		}
	}


	/*
	 * Gives the player some guidance on how to play
	 */
	private void help() {
		this.gameEngine.getStandardMessageGenerator().help();	
	}


	/*
	 * Look at a world entity, printing its description
	 * @param	worldEntity	The entity to be looked at
	 */
	private void look(WorldEntity worldEntity) {
		// Print description of entity that is being looked at
		this.gameEngine.getBrowser().println(
				worldEntity.getName() +": "+ worldEntity.getDescription()
				);

		if(worldEntity instanceof Weapon) {
			// Print weapon statistics
			this.gameEngine.getStandardMessageGenerator().weaponStatistics((Weapon) worldEntity);	
		}		
		else if(this.getPlayer().getCurrentRoom() == worldEntity) {
			int entityID = 1;
			// Print adjoining rooms of the room the player is currently in
			ArrayList<Room> adjoiningRooms = ((Room) worldEntity).getAdjoiningRooms();
			if(adjoiningRooms.size() != 0) {
				this.gameEngine.getBrowser().print(
						"Rooms accessible from "+ worldEntity.getName() +":\n"
						);
				for(int i = 0; i < adjoiningRooms.size(); i++) {
					this.gameEngine.getBrowser().print(
							entityID +") "+ adjoiningRooms.get(i).getName() +"\n"
							);
					entityID++;
				}
			}			

			// Print characters within the room
			ArrayList<Character> characters = ((Room) worldEntity).getInventory().getCharacters();
			if(characters.size() != 0) {
				this.gameEngine.getBrowser().print(
						"\nCharacters within "+ worldEntity.getName() +":\n"
						);
				for(int i = 0; i < characters.size(); i++) {
					this.gameEngine.getBrowser().print(
							entityID+") "+ characters.get(i).getName() +"\n"
						);
						entityID++;
							
				}
			}

			// Print items within the room
			ArrayList<Item> items = ((Room) worldEntity).getInventory().getItems();
			if(items.size() != 0) {
				this.gameEngine.getBrowser().print(
						"\nItems within "+ worldEntity.getName() +":\n"
						);
				for(int i = 0; i < items.size(); i++) {
					this.gameEngine.getBrowser().print(
							entityID +") "+ items.get(i).getName() +"\n"
						);
						entityID++;
				};
			}
		}

		this.gameEngine.getBrowser().addLineBreak();
	}
        

	/*
	 * Get item from room and put in player's inventory
	 * @param	item	Item to get from room
	 */
	private void get(Item item) {
		//Get the inventory of the players current room
		RoomInventory currentRoomInventory = this.getPlayer().getCurrentRoom().getInventory();

		if(this.getPlayer().getCurrentRoom().getInventory().contains(item)) {
			// Item is within player's current room
			if(!item.isCarriable()) {
				// Item is not carriable
				this.gameEngine.getStandardMessageGenerator().playerCannotCarryItem(item);
			}
			else {

				//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
				addToUndoStack(CommandType.GET);

				// Player can get item because it can be carried
				try{
					this.getPlayer().getInventory().addItem(item);
					currentRoomInventory.removeItem(item);
					this.gameEngine.getStandardMessageGenerator().playerGetsItem(item);
				}catch (InventoryFullException e){
					this.gameEngine.getStandardMessageGenerator().InventoryFull(item);
				}
			}
		}else {
				// Item not available in player's current room
				this.gameEngine.getStandardMessageGenerator().itemNotAccessible(item);
			}
		}


		/*
		 * Drop an item from players inventory into the room
		 */
		private void drop(Item item){
			//Get the inventory of the room and the Player
			RoomInventory roomInventory = this.getPlayer().getCurrentRoom().getInventory();
			CharacterInventory playerInventory = this.getPlayer().getInventory();

			//If the Player has the item
			if (playerInventory.contains(item)){

				//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
				addToUndoStack(CommandType.DROP);

				//If the item is the weapon the player is wielding
				if (this.getPlayer().getWieldedWeapon() == item){
					this.getPlayer().setWieldedWeapon(null);
				}

				//Remove the Item from the players inventory and add it to the rooms inventory
				playerInventory.removeItem(item);
				roomInventory.addItem(item);
				this.gameEngine.getStandardMessageGenerator().DroppedItem(item);

			} else {
				// Item not in player's inventory
				this.gameEngine.getStandardMessageGenerator().InventoryitemNotAccessible(item);
			}
		}



		/*
		 * Talk to non-playable character within the game
		 * @param	npc	NPC to talk to
		 */
		private void talk(Npc npc) {
			String narrative = npc.getRandomNarrative();
			this.gameEngine.getBrowser().println(
					npc.getName() +": "+ narrative
					);
		}


		/*
		 * Display remaining objectives to the player 
		 */
		private void displayObjectives() {
			this.gameEngine.getBrowser().println(
					this.gameEngine.getObjectiveHandler().toString()
					);
		}
	}

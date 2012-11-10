package model.gameEngine;

import java.util.ArrayList;
import java.util.Stack;

import controller.executor.AttackExecutor;
import controller.executor.CommandExecutor;
import controller.executor.Executor;
import controller.executor.ExitExecutor;
import controller.executor.TransformExecutor;
import controller.executor.UseExecutor;
import controller.executor.ChangeNameExecutor;
import controller.parser.Command;
import controller.parser.CommandType;
import controller.parser.TextualCommandParser;
import exceptions.NoInputHandlerException;

import model.world.Player;
import model.world.Room;
import model.world.WorldEntity;


/**
 * Routes Commands to their appropriate executor (usually the CommandExecutor, but sometimes the AttackExecutor for example)
 *
 * @author Llamas / Yaks
 */
public class InputHandler {
	/*
	 * Instance variables
	 */
	private GameEngineInterface gameEngine;					// Manages the game
	private Stack<Executor> executorStack;					// Manages the executor stack
	private CommandExecutor commandExecutor;				// Executes standard commands within the current game world
	private ExitExecutor exitExecutor;						// Executes the exit command
	private AttackExecutor attackExecutor;					// Executes attack commands
	private UseExecutor useExecutor;						// Executes use commands
	private ChangeNameExecutor changeNameExecutor;          // Executes name commands
	private TransformExecutor transformExecutor;
	
	
	/**
	 * Constructor
	 * @param	gameEngine	Manages the game
	 */
	public InputHandler(GameEngineInterface gameEngine) {
		super();
		this.gameEngine = gameEngine;
		this.executorStack = new Stack<Executor>();
		
		// Create executors
		this.exitExecutor = new ExitExecutor(this.gameEngine);
		this.attackExecutor = new AttackExecutor(this.gameEngine);
		this.useExecutor = new UseExecutor(this.gameEngine);
		this.changeNameExecutor = new ChangeNameExecutor(this.gameEngine); 
		this.commandExecutor = new CommandExecutor(this.gameEngine);
		this.transformExecutor = new TransformExecutor(gameEngine);
		
		// Set CommandExecutor as the default executor (always bottom of the stack)
		this.pushExecutor(this.commandExecutor);
	}


	/**
	 * gets the commandExecutor
	 * @return commandExecutor
	 */
	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	
        /**
	 * To be called when input is given from the BROWSER
	 * @param	String  Input String
	 */
	public void inputReceived(String inputStr) {
		
		// Parse the command
		Command command = this.parseCommand(inputStr);
		
		// Route to executor
		routeInputToExecutor(inputStr, command);
	}
	
	/**
	 * Route input event (string and command as an "event"), to the correct
	 * execution handler. To handle execution of the input, an execution handler
	 * must implement the appropriate interface. Once active, the executor 
	 * is given all following input events, until it removes it self (popExecutor)
	 * as the active listener.
	 * @param	inputStr	Input string from the user
	 * @param	command	Command found in the input string (if any)
	 */
	public void routeInputToExecutor(String inputStr, Command command) {	
		if(this.executorStack.size() == 1) {
			// Only default CommandExecutor on stack - check if non-default executor should be used 
			
			if(command.getType() == CommandType.NAME){
				// Route Name command
				gameEngine.getBrowser().println("Please Enter your Name: ");
				this.pushExecutor(this.changeNameExecutor);
				//Exit and wait for name input
				return;
			}
			
			if(command.getType() == CommandType.QUIT) {
				// Route exit command
				this.pushExecutor(this.exitExecutor);
			}
			
			else if(command.getType() == CommandType.ATTACK) {
				// Route attack command
				this.pushExecutor(this.attackExecutor);
			}
			
			else if(command.getType() == CommandType.USE) {
				// Route use command
				this.pushExecutor(this.useExecutor);
			}
			
			else if (command.getType() == CommandType.TRANSFORM) {
				this.pushExecutor(this.transformExecutor);
			}
		}
		else {
			// Non-default executor on stack - keep giving them input
		}
				
		// Ensure executor is able to handle command
		if(this.executorStack.isEmpty()) {
			throw new NoInputHandlerException(
				"There is not current input handler.\n"+
				"A previous handler must have poped too many handlers from the stack."
			);
		}
		
		// Construct event and give to executor
		ExecutorEvent event = new ExecutorEvent(inputStr, command);
		this.executorStack.peek().handleInput(event);
			
		// Ensures a world is currently been played
		if(this.gameEngine.getWorld() != null) {		
			// Give command to objectives handler
			this.gameEngine.getObjectiveHandler().handleObjectivesCompletion(
				command,
				this.gameEngine
			);
		}
	}
	
	
	/**
	 * Push the given executor onto the stack
	 * @param	executor	Executor that is to be made active (put on top of stack)
	 */
	public void pushExecutor(Executor executor) {
		this.executorStack.push(executor); 
	}
	
	/**
	 * Pop the given executor off the stack
	 */
	public void popExecutor() {
		this.executorStack.pop();
	}
	
	
	/**
	 * Parse command inputed by the user
	 * @param	commandString	Command string input by the user
	 * @return	Parsed input
	 */
	public Command parseCommand(String commandString) {
		// Parser input from user to get game command
		TextualCommandParser inputParser = new TextualCommandParser(
				this.getWorldEntitiesAccessibleToPlayer(),
				this.getNumberedItems(),
				this.getLetteredItems()
			);
		return inputParser.parse(commandString);
	}
	
	
	/**
	 * 
	 * Gets the items that the command will get by number
	 * @return An array list of world entities
	 */
	private ArrayList<WorldEntity> getNumberedItems() {
		ArrayList<WorldEntity> numberedItems = new ArrayList<WorldEntity>();
		
		if(this.gameEngine.getWorld() == null) {
			// No world loaded
			return numberedItems;
		}
		
		Player player = this.gameEngine.getWorld().getPlayer();
		Room currentRoom = player.getCurrentRoom();
	
		numberedItems.addAll(currentRoom.getAdjoiningRooms());		
		numberedItems.addAll(currentRoom.getInventory().getCharacters());	// Characters within current room
		numberedItems.addAll(currentRoom.getInventory().getItems());	// Items within current room
		
		return numberedItems;
	}
	
	/**
	 * Gets the items that the command will get by letter
	 * @return An array list of world entities
	 */
	private ArrayList<WorldEntity> getLetteredItems() {
		ArrayList<WorldEntity> items = new ArrayList<WorldEntity>();
		
		if(this.gameEngine.getWorld() == null) {
			// No world loaded
			return items;
		}
		
		Player player = this.gameEngine.getWorld().getPlayer();

		
		items.addAll(player.getInventory().getItems());
		return items;
	}
	
	/**
	 * Get world entities that player may be able to currently interact with
	 * @return	Collection of world entities currently accessible to the player
	 */
	public ArrayList<WorldEntity> getWorldEntitiesAccessibleToPlayer() {
		ArrayList<WorldEntity> accessibleEntities = new ArrayList<WorldEntity>();
		
		if(this.gameEngine.getWorld() == null) {
			// No world loaded
			return accessibleEntities;
		}
		
		Player player = this.gameEngine.getWorld().getPlayer();
		Room currentRoom = player.getCurrentRoom();
		if(player.getWieldedWeapon() != null) {
			accessibleEntities.add(player.getWieldedWeapon());					// Weapon player is wielding
		}
		accessibleEntities.addAll(player.getInventory().getItems());			// Items within player's inventory
		accessibleEntities.add(currentRoom);									// Current room
		accessibleEntities.addAll(currentRoom.getInventory().getItems());		// Items within current room
		accessibleEntities.addAll(currentRoom.getInventory().getCharacters());	// Characters within current room
		accessibleEntities.addAll(currentRoom.getAdjoiningRooms());				// Adjoining rooms
		
		return accessibleEntities;
	}
}

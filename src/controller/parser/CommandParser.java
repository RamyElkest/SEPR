package controller.parser;

import model.world.WorldEntity;
import java.util.ArrayList;


/**
 * An abstract class (allowing forms of input other than regular text to interact with the game) that takes an inputed 
 * command and interprets it to derive what game command the input corresponds to.
 * 
 * @author	Llamas
 * @version	03/11/11
 */
public abstract class CommandParser {
	/*
	 * Instance variables
	 */
	private CommandCollection commands;						// Collection of game commands
	private ArrayList<WorldEntity> worldEntities;			// World entities that a command can refer to
	
	
	
	/**
	 * Constructor
	 * @param worldEntities	List of entities within the world that are accessible to the player
	 */
	public CommandParser(ArrayList<WorldEntity> worldEntities) {
		super();
		this.worldEntities = worldEntities;
		this.commands = new CommandCollection();
	}
	
	
	
	// Accessors
	/**
	 * Get the commands that are available in the game 
	 * @return	Hash map of available commands
	 */
	public CommandCollection getCommands() {
		return this.commands;
	}
	
	
	/**
	 * Get the world entities that are accessible to the player and
	 * therefore can be referenced by commands
	 * (This method is protected as there is no reason that external
	 *  classes should be getting the game objects from this class)
	 * @return	World entities that are accessible to the player
	 */
	protected ArrayList<WorldEntity> getGameObjects() {
		return this.worldEntities;
	}
}

package controller.parser;

import model.world.Room;
import model.world.WorldEntity;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parses textually represented commands. Tokenises the command string
 * and then creates a Command object which contains the command type and
 * any game object the command makes reference to
 * You can also use numbers and letters
 *
 * @author	Llamas
 * @version	03/11/11
 */
public class TextualCommandParser extends CommandParser {
	private ArrayList<WorldEntity> numberedItems;
	private ArrayList<WorldEntity> letteredItems;


	/**
	 * The constructor
	 * @param 
	 * @param gameObjects	List of objects that are accessible within the current game position
	 */
	public TextualCommandParser(ArrayList<WorldEntity> gameObjects, ArrayList<WorldEntity> numberedItems, ArrayList<WorldEntity> letteredItems) {
		super(gameObjects);
		this.numberedItems = numberedItems;
		this.letteredItems = letteredItems;
	}
	
	
	
	// Accessors
	/**
	 * Parse string to derive the command it represents
	 * @param textualCommand	String which may contain a game command
	 * @return	Command object representing the textual command in the input
	 * 			If no command could be found, a command of the invalid type
	 */
	public Command parse(String textualCommand) {
		textualCommand = textualCommand.toLowerCase();				// All commands are case-insensitive
		return getCommand(textualCommand);
	}
	
	
	
	// Private methods
	/*
	 * Get the game command from textual input
	 * @param textualCommand	String which may contain a game command
	 * @return	The command found within the input.
	 * 			If no command could be found, a command of the invalid type is returned
	 */
	private Command getCommand(String textualCommand) {
		// Get the command type
		CommandType commandType = this.getCommandType(textualCommand);
		if(commandType == CommandType.INVALID_TYPE) {
			// No valid command found
			return new Command(commandType);
		}
		
		if(!commandType.requiresEntity()) {
			// Command does not require an object reference - command fully known
			return new Command(commandType);
		}
		else {
			// Get reference to game object
			WorldEntity entityReference = this.getEntityReference(textualCommand);
			
			return new Command(commandType, entityReference);
		}
	}
	
	
	/*
	 * Get command type from textual command
	 * @param textualCommand	Textual representation of command to get command type from
	 * @return	Command type indicated by a keyword in the textual representation
	 */
	private CommandType getCommandType(String textualCommand) {
		Scanner tokenizer = new Scanner(textualCommand);
		Pattern pattern = Pattern.compile("[\\w]+"); //Matches all word that contain a-z or 0-9
		
		while(tokenizer.hasNext()) {
			// Strip any unrelated character before or after the word
			Matcher matcher = pattern.matcher(tokenizer.next());
			matcher.find();
			if(!matcher.matches()) {continue;}
			String str = matcher.group();

			// Match to word to keyword
			CommandType commandType = this.getCommands().getCommandType(str);
			if(commandType != CommandType.INVALID_TYPE) {
				return commandType;
			}
		}
		
		return CommandType.INVALID_TYPE;
	}
	
	
	/*
	 * Get game object reference in textual command
	 * @param textualCommand	Textual representation of command to get game object reference from (if any)
	 * @return	GameObject referenced in the command.
	 * 			Null if no reference is found
	 */
	private WorldEntity getEntityReference(String textualCommand) {	
		textualCommand += " ";
		
		for(WorldEntity gameObject : this.getGameObjects()) {
			String objName = gameObject.getName();
			objName = objName.toLowerCase();
			
			Pattern pattern = Pattern.compile(objName + " ");
			Matcher matcher = pattern.matcher(textualCommand);

			if(matcher.find()) {
				// Input includes the object name - object reference is assumed
				return gameObject;
			}
		}
		
		// Match numbers
		Pattern numberPattern = Pattern.compile("[^ ]+ ([0-9]+) .*");
		Matcher numberMatcher = numberPattern.matcher(textualCommand);
		if (numberMatcher.matches()) {
			int number = Integer.parseInt(numberMatcher.group(1))-1;
			if (number < numberedItems.size() && number >= 0)
				return numberedItems.get(number);
		}
		
		// Match singles letter
		Pattern letterPattern = Pattern.compile("[^ ]+ ([A-Za-z]) .*");
		Matcher letterMatcher = letterPattern.matcher(textualCommand);
		if (letterMatcher.matches()) {
			int number = (int)letterMatcher.group(1).toLowerCase().charAt(0) - 'a';
			if (number < letteredItems.size() && number >= 0)
				return letteredItems.get(number);
		}
		
		
		return null;
	}
}
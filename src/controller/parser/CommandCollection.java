package controller.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * Allows access to the command types that are available in the game
 *
 * This class draws on ideas in the Zuul project, described in Objects First with Java,
 * A Practical Introduction using BlueJ D.J. Barnes & M.K?lling
 * 
 * @author	Llamas
 * @version	03/11/11
 */
public class CommandCollection {
	/*
	 * Instance variables
	 */
	private HashMap<String, CommandType> commandTypes;				// Hash map of all game command types

    
    
    /**
     * The constructor
     */
    public CommandCollection() {
    	super();
    	
    	// Initialise collection of command types
    	this.commandTypes = new HashMap<String, CommandType>();
    	for(CommandType commandType : CommandType.values()) {
    		this.commandTypes.put(commandType.toString(), commandType.synonymOf());
    	}
    }

    
    
    // Accessors
    /**
     * Find the CommandType associated with a keyword
     * @param str	Textual representation of the CommandType
     * @return	The CommandType corresponding to keyword.
     * 			If keyword is not identified, returns invalid type
     */
    public CommandType getCommandType(String str) { 
    	CommandType commandType = this.commandTypes.get(str);
    	if(commandType != null) {
    		return commandType;
    	}
    	else {
    		return CommandType.INVALID_TYPE;
    	}
    }
    
        
    /**
     * Get collection of command types, represented as a string
     * @return	Collection of command types as a string
     */
    @Override
    public String toString() {
    	String str = "";
  
    	Iterator<Entry<String, CommandType>> it = this.commandTypes.entrySet().iterator();
    	while(it.hasNext()) {
    		Entry<String, CommandType> entry = it.next();
    		CommandType commandType = entry.getValue();
    		
    		if(commandType != CommandType.INVALID_TYPE) {
    			// Include only valid command types
    			str += entry.getKey() +"\n";
    		}
    	}
    	return str;
    }
}

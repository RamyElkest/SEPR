package controller.parser;

import org.json.JSONException;
import org.json.JSONObject;

import model.world.WorldEntity;


/**
 * Command that, if valid, can be evaluated to change the state of the game's World
 *
 * @author 	Llamas
 * @version	03/11/11
 */
public class Command {
	/*
	 * Instance variables
	 */
	private CommandType commandType;						// Type of the command
	private WorldEntity entityReference = null;				// World entity that the command references



	/**
	 * Constructor
	 * @param commandType	The command's type
	 */
	public Command(CommandType commandType) {
		this(commandType, null);
	}


	/**
	 * Constructor
	 * @param commandType	The command's type
	 * @param referenceEntity	The WorldEntity the command refers to.
	 * 							Null if the command does not need to refer to a WorldEntity.
	 */
	public Command(CommandType commandType, WorldEntity referenceEntity) {
		super();
		this.entityReference = referenceEntity;
		this.commandType = commandType;
	}



	// Accessors
	/**
	 * Get the command's type
	 * @return	CommandType
	 */
	public CommandType getType() {
		return this.commandType;
	}
	
	
	/**
	 * A quick way of doing command.getType().toString(); so that it is clearer
	 * what the intended action is
	 * @return String The command type's textual name
	 */
	public String getName() {
		return getType().toString();
	}
	
	
	/**
	 * Check if commands are equal - required as command my trigger an objective to be completed
	 * @param	obj	Command to be checked
	 * @return 	Whether the commands are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Command) {
			// Compare two command
			Command command = (Command) obj;
			if(this.getType() == command.getType().synonymOf()			// Check for same command type
				&& this.getReference() == command.getReference()) {		// Check for same reference
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	

	/**
	 * Get the world entity (if any) that the command refers to
	 * @return	WorldEntity that the command references.
	 * 			Null if the command does not make a reference to a WorldEntity
	 */
	public WorldEntity getReference() {
		return this.entityReference;
	}


	/**
	 * Get the command as a string in the form: "<CommandType>" or "<CommandType> -> <WorldEntity>"
	 * @return	Textual representation of the command
	 */
	@Override
	public String toString() {
		String str = this.commandType.toString();
		if(this.entityReference != null) {
			str += " -> " + this.entityReference.getName();
		}
		return str;
	}


	/**
	 * Returns a JSONObject containing information about the command
	 * @return The JSONObject
	 * @throws JSONException 
	 */
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("entity-id", this.entityReference.getUniqueId());
		json.put("commandType", this.commandType.name());
		
		return json;
	}
}

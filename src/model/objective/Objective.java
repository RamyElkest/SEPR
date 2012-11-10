package model.objective;

import org.json.JSONException;
import org.json.JSONObject;

import controller.parser.Command;


/**
 * IMPORTANT: Objectives contain Commands (sometimes referred to as playerActions in this context). These commands
 * represent something the player attempts to do inside the game, such as using a key in a lock.
 * 
 * Parsed commands for the user are compared to commands (player actions) in the objectives system. Only valid commands
 * can complete objectives, hence just typing "USE goldkey" out of context won't work, because the player must have access to
 * the relevant key for the command to be valid.
 * 
 * @author Llamas
 *
 */
public class Objective {
	/*
	 * Instance variables
	 */
	private String description;								// Description of the objective
	private String onCompleteMessage;						// Message shown to the user when the objective has been completed
	private Command playerAction;							// Player's action which objective is linked to within the game
	
	
	
	/**
	 * Constructor
	 * @param	description	Description of the objective
	 * @param	onCompleteMessage	Message shown to the user when the objective has been completed
	 * @param	playerAction	Event which objective is linked to within the game
	 */
	public Objective(String description, String onCompleteMessage, Command playerAction) {
		super();
		this.description = description;
		this.onCompleteMessage = onCompleteMessage;
		this.playerAction = playerAction;
	}
	
	
	
	// Accessors
	/**
	 * Get description of the objective
	 * @return	Description of the objective
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	/**
	 * Get message to be shown to the user when the objective has been completed
	 * @return	Message shown when objective is completed
	 */
	public String getOnCompleteMessage() {
		return this.onCompleteMessage;
	}
	
	
	/**
	 * Get what game event the objective relates to
	 * @return	Game event objective is linked to
	 */
	public Command getCommand() {
		return this.playerAction;
	}
	
	
	/**
	 * Return a JSON representation of the objective for saving
	 * @return A JSON string
	 * @throws JSONException 
	 */
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = new JSONObject();
		
		json.put("description", this.description);
		json.put("onCompleteMessage", this.onCompleteMessage);
		json.put("command", this.playerAction.toJSONObject());
		return json;
	}
	
	
	/**
	 * Get the objective as a string
	 * @return	Objective as a string
	 */
	@Override
	public String toString() {
		String str = "Objective:\n";
		str += "Description = "+ this.getDescription() +"\n";
		str += "Game Event = "+ this.getCommand() +"\n";
		str += "OnComplete message = "+ this.getOnCompleteMessage();
		return str;
	}
}

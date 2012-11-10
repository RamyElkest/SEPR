package model.world;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * An entity which exists within the game's World
 * 
 * @author 	
 * @version	03/11/11
 */
public abstract class WorldEntity {
	/*
	 * Instance variables
	 */
	private String uniqueId; 				// Unique identifier of world entity
	private String name;					// Name of world entity
	private String description;				// Description of world entity
	
	
	
	/**
	 * Constructor
	 */
	public WorldEntity() {
		super();
		this.uniqueId = "";
		this.name = "";
		this.description = "";
	}
	
	
	
	// Accessors
	/**
	 * Get the world entity's unique identification
	 * @return	Unique identification
	 */
	public String getUniqueId() {
		return this.uniqueId;
	}
	
	
	/**
	 * Get the world entity's name 
	 * @return	Name
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Get the world entity's description
	 * @return	Description
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	/**
	 * Get textual representation of the world entity
	 * @return	String representation
	 */
	@Override
	public String toString() {
		String str = "uniqueId = "+ this.uniqueId +"\n";
		str += "name = "+ this.name +"\n";
		str += "description = "+ this.description;
		return str;
	}
	
	
	
	// Mutators
	/**
	 * Set the world entity's uniqueId
	 * @param uniqueId	New uniqueId to give to the game object
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	
	/**
	 * Set the world entity's name
	 * @param name	New name to give to the world entity
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Set the world entity's description
	 * @param description	New description to give to the world entity
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	/**
	 * Base toJSONObject function, overridden by subclasses.
	 * Provides basic uniqueId, name and description JSON storage
	 * @return The entity as a JSON object
	 * @throws JSONException Thrown if there is a problem constructing the JSON
	 */
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("uniqueId", this.getUniqueId() );
		json.put("name", this.getName() );
		json.put("description", this.getDescription() );
		
		return json;
	}
}

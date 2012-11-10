package model.world;

import java.util.ArrayList;

import model.objective.Objective;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * The top of the hierarchy that holds all the data about the in game universe.
 * 
 * @author Llamas
 *
 */
public class World {
	/*
	 * Instance variables
	 */
	private String name;						// Name of the world
	private String description;					// Description of the world
	private String created;						// When the world was created
	private String author;						// Author of the world
	
	private Player player;						//The player's character
	
	private ArrayList<Room> rooms;				// Rooms the world contains
	private ArrayList<Objective> objectives;	// World objectives

	
	
	/**
	 * Constructs the World object with the required attributes
	 */
	public World(String name, String description, String created, String author, Player player) {
		super();
		
		//initialise the fields
		this.name = name;
		this.description = description;
		this.created = created;
		this.author = author;
		
		this.player = player;
		
		this.rooms = new ArrayList<Room>();
		this.objectives = new ArrayList<Objective>();
	}
	
	
	
	// Accessors
	/**
	 * Get the name of the world
	 * @return	World's name
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Get a description of the world
	 * @return	World's description
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	/**
	 * Get when the world was created
	 * @return	When the world was created 
	 */
	public String getCreated() {
		return this.created;
	}
	
	
	/**
	 * Get the world's author
	 * @return	World's author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	
	/**
	 * Get the world's objectives
	 * @return	World's objectives
	 */
	public ArrayList<Objective> getObjectives() {
		return this.objectives;
	}
	
	
	/**
	 * Get the rooms in the world
	 * @return	Rooms in the world
	 */
	public ArrayList<Room> getRooms() {
		return this.rooms;
	}
	
	
	/**
	 * Get the player from the world
	 * @return	Player that exists within the world
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	
	/**
	 * Get JSON representation of the world
	 * @return	JSON representation of the world
	 * @throws JSONException 
	 */
	public JSONObject toJSONObject() throws JSONException {
		// Add the basic fields to the root json object
		JSONObject json = new JSONObject();
		
		json.put("player", player.toJSONObject());
		
		json.put("name", this.getName());
		json.put("description", this.getDescription());
		json.put("created", this.getCreated());
		json.put("author", this.getAuthor());
		
		JSONArray roomArray = new JSONArray();
		// Add the rooms to the roomArray
		for(Room room : this.getRooms()) {
			roomArray.put(room.toJSONObject());
		}
		// Add the array to the main json object
		json.put("rooms", roomArray);
		
		
			
		
		
		JSONArray objectiveArray = new JSONArray();
		// Add the objectives to the objectiveArray
		for(Objective objective : this.getObjectives()) {
			objectiveArray.put(objective.toJSONObject());
		}
		// Add the array to the main json object
		json.put("objectives", objectiveArray);
		
		return json;
	}

	/**
	 * Get room from the world by its id
	 * @param	id	Identification string of the room to get
	 * @return	Room with required id, else null
	 */
	public Room getRoomById(String id) {
		for(Room room : this.rooms) {
			if(room.getUniqueId().equals(id)) {
				// Entity is the room
				return room;
			}
		}
		return null;
	}
	
	
	
	// Mutators
	/**
	 * Add a room to the world
	 * @param	room	Room to add to the world
	 */
	public void addRoom(Room room) {
		this.rooms.add(room);
	}
	
	
	/**
	 * Add an objective to the world
	 * @param	objective	Objective to add
	 */
	public void addObjective(Objective objective) {
		this.objectives.add(objective);
	}
	
	
	/**
	 * Add a player to the world (replaces any previously defined player)
	 * @param	player	Player to be used in the world
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
}

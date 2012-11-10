package model.world;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A character in the game world controlled by the user.
 * 
 * @author 	Llamas / Yaks
 */
public class Player extends Character {
	/*
	 * Settings
	 */
	private static final int MAX_INVENTORY_ITEMS = 5;			// Maximum items allowed in a player's inventory
	
	
	
	/*
	 * Instance variables
	 */
	private Room currentRoom;									// Room player is currently within
	private ArrayList<Room> visitedRooms;						// List of rooms player has visited (required for "random" command)
	
	
	
	/**
	 * Constructor
	 * @param	maxHealth	The maximum health the player can have
	 * @param	wieldedWeapon	Whether or not the player is carrying a weapon
	 * @param	currentRoom Room	player is currently within
	 */
	public Player(int maxHealth, Weapon wieldedWeapon, Room currentRoom) {
		super(maxHealth, wieldedWeapon, new PlayerInventory(MAX_INVENTORY_ITEMS));
		this.visitedRooms = new ArrayList<Room>();
		this.setCurrentRoom(currentRoom);
	}
	
	
	
	// Accessors
	/**
	 * Get the room the player is currently within
	 * @return	Room player is within
	 */
	public Room getCurrentRoom() {
		return this.currentRoom;
	}
	
	
	/**
	 * Get rooms player has visited the current game session
	 * @return	List of rooms player has visited during the current game session
	 */
	public ArrayList<Room> getVisitedRooms() {
		return this.visitedRooms;
	}
	
	
	/**
	 * Returns the JSON object representation of this object
	 * @return The JSON object representation of this object
	 */
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = super.toJSONObject();
		
		json.put("currentRoom", this.getCurrentRoom().getUniqueId());
		json.put("inventory", this.getInventory().toJSONObject());
		

		JSONArray visitedRoomsArray = new JSONArray();
		// Add the rooms to the visitedRoomsArray
		for(Room room : this.getVisitedRooms()) {
			visitedRoomsArray.put(room.getUniqueId());
		}
		// Add the array of visited rooms to the main json object
		json.put("visitedRooms", visitedRoomsArray);
		
		if(this.getWieldedWeapon() != null) {
			json.put("wieldedWeapon", this.getWieldedWeapon().getUniqueId());
		}
		
		return json;
	}

	
	
	// Mutators
	/**
	 * Set the room that the player is within
	 * @param	room	Room player is within
	 */
	public void setCurrentRoom(Room room) {
		this.addToVisitedRooms(room);
		this.currentRoom = room;
	}
	
	/**
	 * Add a room to the visited rooms if it is not already in visitedRooms
	 * @param room
	 */
	public void addToVisitedRooms(Room room) {
		if(!visitedRooms.contains(room))
			this.visitedRooms.add(room);	
	}
}

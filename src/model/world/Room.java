package model.world;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An indivisible region of the game that items and characters are either in or not.
 * 
 * @author Llamas
 *
 */
public class Room extends WorldEntity {
	/*
	 * Instance variables
	 */
	private RoomInventory inventory;					// Room's inventory
	private ArrayList<Room> adjoiningRooms;				// Rooms that are linked to
	
	
	
	/**
	 * Constructor
	 */
	public Room(RoomInventory inventory) {
		super();
		if(inventory != null) {
			this.inventory = inventory;
		}
		else {
			// Ensure room always has an inventory
			this.inventory = new RoomInventory();
		}
		this.adjoiningRooms = new ArrayList<Room>();
	}
	
	
	
	// Accessors
	/**
	 * Get the adjoining rooms
	 * @return	Adjoining rooms
	 */
	public ArrayList<Room> getAdjoiningRooms() {
		return this.adjoiningRooms;
	}
	
	
	/**
	 * Get the room's inventory
	 * @return	Room's inventory
	 */
	public RoomInventory getInventory() {
		return this.inventory;
	}
	
	
	/**
	 * Returns the JSON object representation of this object
	 * @return The JSON object representation of this object
	 * @throws JSONException 
	 */
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = super.toJSONObject();
		
		json.put("inventory", this.getInventory().toJSONObject());
		
		JSONArray adjoiningArray = new JSONArray();
		
		for(Room room : this.getAdjoiningRooms()) {
			// Put the ID instead of the JSON here as it would cause infinite recursion if there is a cycle in the tree
			adjoiningArray.put(room.getUniqueId());
		}
		
		json.put("adjoiningRooms", adjoiningArray);
		
		return json;
	}
	
	// Mutators
	/**
	 * Add an adjoining room
	 * @param	adjoiningRoom	Room that is joined and is therefore can be moved into by the player
	 */
	public void addAdjoiningRoom(Room adjoiningRoom) {		
		if(adjoiningRoom == this) {
			throw new IllegalArgumentException(this.getName()+ " cannot have itself added as an adjoining room");
		}
		this.adjoiningRooms.add(adjoiningRoom);
	}
}

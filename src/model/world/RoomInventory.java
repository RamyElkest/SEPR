/**
 * 
 */
package model.world;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An inventory that can contain characters, not just items, and thus is used in Rooms.
 * 
 * @author Llamas
 *
 */
public class RoomInventory extends Inventory {
	/*
	 * Instance variables
	 */
	private ArrayList<Character> characters;			// Characters within the room
	
	
	
	/**
	 * Constructor
	 */
	public RoomInventory() {
		super();
		this.characters = new ArrayList<Character>();
	}
	


	// Accessors
	/**
	 * Get characters within the room
	 * @return	Character within the room
	 */
	public ArrayList<Character> getCharacters() {
		return this.characters;
	}


	/**
	 * Get the number of characters within the room
	 * @return	Number of characters within the room
	 */
	public int getNumberOfCharacters() {
		return this.getCharacters().size();
	}

	
	/**
	 * Returns the JSON object representation of this object
	 * @return The JSON object representation of this object
	 * @throws JSONException 
	 */
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = super.toJSONObject();
		
		JSONArray characterArray = new JSONArray();
		for(Character character : this.getCharacters()) {
			characterArray.put(character.toJSONObject());
		}
		
		json.put("characters", characterArray);
		
		return json;
	}
	
	
	/**
	 * Check if a character exists within the room inventory
	 * @param	character	Character to check if the inventory contains
	 * @return	Whether the character was found in the room inventory
	 */
	public boolean contains(Character character) {
		return this.getCharacters().contains(character);
	}


	
	// Mutators
	/**
	 * Add a character to the inventory
	 * @param	character	Character to add to the room
	 */
	public void addCharacter(Character character) {
		this.characters.add(character);
	}
	
	
	/**
	 * Remove a character from the inventory
	 * @param	character	Character to remove from the room
	 */
	public void removeCharacter(Character character) {
		this.characters.remove(character);
	}
}
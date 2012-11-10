package model.world;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The character in the game world controlled by the program as opposed to the user.
 * 
 * @author 	Llamas
 */
public abstract class Npc extends Character {
	/*
	 * Instance variables
	 */
	private ArrayList<String> narratives;
	
	
	
	// ****************************************************************************************
	// Could we just assume that currentHealth == maxHealth when instantiated?
	// This would cut down on some of the complexity and we'd loose a param
	// ****************************************************************************************
	/**
	 * Constructor:
	 * @param maxHealth The maximum health a character can have.
	 * @param currentHealth The current value of the health of a character.
	 * @param narratives The list of phrases that can be spoken by an Npc.
	 * @param wieldedWeapon The weapon the Npc is carrying.
	 */
	public Npc(int maxHealth, ArrayList<String> narratives, Weapon wieldedWeapon) {
		super(maxHealth, wieldedWeapon, Integer.MAX_VALUE);
		this.narratives = narratives != null ? narratives : new ArrayList<String>();
	}
	
	
	
	// Accessors
	/**
	 * Get the list of phrases spoken by the Npc.
	 * @return narratives List of Narratives
	 */
	public ArrayList<String> getNarratives() {
		return this.narratives;
	}
	
	
	/**
	 * Get a random narrative
	 * @return The narrative in String form
	 */
	public String getRandomNarrative() {
		if(this.narratives.size() == 0) {
			// No narratives
			return this.getName() + " says nothing";
		}
		
		Random random = new Random();
		int randomIndex = random.nextInt(this.narratives.size());
		return this.narratives.get(randomIndex);
	}
	

    /**
     * Returns the JSON object representation of this object
     * @return The JSON object representation of this object
     * @throws JSONException Thrown if there is a problem constructing the JSON
     */
    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = super.toJSONObject();

        // Store the narrative Strings in a JSONArray and put them into the json object
        JSONArray narrativeArray = new JSONArray();

        for(String narrative : narratives) {
            narrativeArray.put(narrative);
        }

        json.put("narratives", narrativeArray);

        return json;
    }
        
        
}

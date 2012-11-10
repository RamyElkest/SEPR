/**
 * 
 */
package model.world;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Medicine - The medicine type (the player uses them to heal)
 * 
 * @author Llamas
 *
 */
public class Medicine extends Item {
	/**
	 * Constructor
	 */
	public Medicine() {
		super(true, true, false);				// Medicine is carriable and usable else it is useless
	}
	
	/**
	 * Returns a JSON representation of the Medicine for saving.
	 * @return The medicine item in JSON form as a JSONObject
	 */
	@Override
	public JSONObject toItemJSONObject() throws JSONException {
		JSONObject json = super.toItemJSONObject();
		json.put("type", "medicine");
		
		return json;
	}
}

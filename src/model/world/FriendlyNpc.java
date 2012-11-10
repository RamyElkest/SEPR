package model.world;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import exceptions.FriendlyNpcCannotHaveWeaponException;

/**
 * A character which exists within the a room within the game's World
 * which will not attack and cannot be attacked by a player.
 * 
 * @author Llamas 	
 */
public class FriendlyNpc extends Npc {
	/**
	 * Constructor
	 * @param narratives The list of phrases that can be said by this Npc
	 */	
	public FriendlyNpc(ArrayList<String> narratives) {
		super(Integer.MAX_VALUE, narratives, null);
	}
	
	
	
	/**
	 * Override methods that associate a friendly npc with a wielded weapon
	 */
	@Override
	public Weapon getWieldedWeapon() {
		throw new FriendlyNpcCannotHaveWeaponException();
	}
	@Override
	public void setWieldedWeapon(Weapon wieldedWeapon) {
		throw new FriendlyNpcCannotHaveWeaponException(); 
	}
	
	
	
	/**
	 * Returns a JSON object representation of this object
	 * @return The JSON object representation of this object
	 */
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = super.toJSONObject();
		json.put("type", "friendly");
		
		return json;
	}	
}

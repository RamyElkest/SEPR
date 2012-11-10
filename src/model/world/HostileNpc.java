package model.world;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A character which exists within the a room within the game's World
 * which will attack and can be attacked by the player.
 * 
 * @author Llamas
 */
public class HostileNpc extends Npc {
	/*
	 * Instance variables
	 */
	private double attackProbability;						// Probability that hostile NPC will attack player on them entering the room
	
	private boolean hasInitiative;							// Whether or not the hostile NPC initiated combat.
	
	/**
	 * Constructor
	 * @param maxHealth The maximum health of the hostile Npc.
	 * @param narratives The list of phrases that can be spoken by an Npc.
	 * @param wieldedWeapon The weapon the hostile Npc is carrying.
	 * @param attackProbability
	 */
	public HostileNpc(int maxHealth, ArrayList<String> narratives, Weapon wieldedWeapon, double attackProbability) {
		super(maxHealth, narratives, wieldedWeapon);
		this.attackProbability = attackProbability;
		this.hasInitiative = false;
	}
	
	// Mutators
	/**
	 * Tells the NPC it initiated combat with the player and so strikes first.
	 */
	public void giveInitiative() {
		this.hasInitiative = true;
	}
	
	// Accessors
	/**
	 * The probability that a hostile Npc will attack a player 
	 * on entry of the room it resides in.
	 * @return attackProbability
	 */
	public double getAttackProbability() {
		return this.attackProbability;
	}
	
	/**
	 * Returns true if the NPC initiated combat, false if the player did
	 * @return hasInitiative
	 */
	public boolean hasInitiative() {
		return this.hasInitiative;
	}
	
	
	/**
	 * Returns the JSON object representation of this object
	 * @return The JSON object representation of this object
	 * @throws JSONException 
	 */
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = super.toJSONObject();
		json.put("attackProbability", this.getAttackProbability());
		json.put("type", "hostile");
		return json;
	}
}

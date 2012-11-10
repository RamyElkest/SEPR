package model.world;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * An item that can be wielded by the player and used to inflict damage in a fight sequence.
 * 
 * 
 * @author Llamas
 *
 */
public class Weapon extends Item {
	/*
	 * Instance variables
	 */
	private int minDamage;					// Minimum damage the item can do
	private int maxDamage;					// Maximum damage the item can do
	private int easeOfUse;					// The character with the easiest weapon to use is most likely to hit
	
	
	
	/**
	 * Constructor 
	 * @param	minDamage	Minimum damage the weapon does
	 * @param	maxDamage	Maximum damage the weapon does
	 */
	public Weapon(int minDamage, int maxDamage) {
		this(minDamage, maxDamage, 1);
	}
	
	/**
	 * Constructor 
	 * @param	minDamage	Minimum damage the weapon does
	 * @param	maxDamage	Maximum damage the weapon does
	 * @param   easeOfUse   The character with the easiest weapon to use is most likely to hit
	 */
	public Weapon(int minDamage, int maxDamage, int easeOfUse) {
		super(true, true, false);					// Weapon usable and carriable else it is pointless
		
		if(minDamage > maxDamage) {
			throw new IllegalArgumentException("minDamage should not be more than maxDamage");
		}
		if(minDamage < 0 || maxDamage < 0) {
			throw new IllegalArgumentException("Damages cannot be set below 0");
		}
		if(easeOfUse < 1) {
			throw new IllegalArgumentException("Ease of use cannot be below 1");
		}
		
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.easeOfUse = easeOfUse;
	}
	
	
	
	// Accessors 
	/**
	 * Get the minimum damage the weapon can cause
	 * @return	Weapon's minimum damage
	 */
	public int getMinDamage() {
		return this.minDamage;
	}
	
	
	/**
	 * Get the maximum damage the weapon can cause
	 * @return	Weapon's maximum damage
	 */
	public int getMaxDamage() {
		return this.maxDamage;
	}
	
	/**
	 * Get the maximum damage the weapon can cause
	 * @return	Weapon's maximum damage
	 */
	public int getEaseOfUse() {
		return this.easeOfUse;
	}

	
	/**
	 * Returns he JSON object representation of this object
	 * @return The JSON object representation of this object
	 */
	@Override
	public JSONObject toItemJSONObject() throws JSONException {
		JSONObject json = super.toItemJSONObject();
		
		json.put("minDamage", minDamage);
		json.put("maxDamage", maxDamage);
		json.put("easeOfUse", easeOfUse);
		
		json.put("type", "weapon");
		
		return json;
	}
	
	
}

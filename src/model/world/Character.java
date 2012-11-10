package model.world;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A character which exists within the a room within the game's World
 * 
 * @author 	Llamas
 */

public abstract class Character extends RoomEntity {
	/*
	 * Instance Variables
	 */	
	private int maxHealth;
	private int currentHealth;
	private Weapon wieldedWeapon;
	private CharacterInventory inventory;
	
	
	
	/**
	 * Constructor
	 * @param maxHealth The maximum health a character can have.
	 * @param currentHealth The current value of the health of a character.
	 * @param wieldedWeapon The weapon the character is carrying.
	 * @param maxInventoryItems The maximum number of items the Character can store. 
	 */
	public Character(int maxHealth, Weapon wieldedWeapon, int maxInventoryItems) {
		super();
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.wieldedWeapon = wieldedWeapon;
		this.inventory = new CharacterInventory(maxInventoryItems);
	}
	
	/**
	 * Constructor
	 * @param maxHealth The maximum health a character can have.
	 * @param currentHealth The current value of the health of a character.
	 * @param wieldedWeapon The weapon the character is carrying.
	 * @param inventory A premade inventory.
	 */
	public Character(int maxHealth, Weapon wieldedWeapon, CharacterInventory inventory) {
		super();
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.wieldedWeapon = wieldedWeapon;
		this.inventory = inventory;
	}

	
	
	// Accessors
	/**
	 * Get the maximum health of a character
	 * @return maxhealth	Maximum health of character
	 */
	public int getMaxHealth() {
		return this.maxHealth;
	}

	/**
	 * Get the current health of a character
	 * @return currentHealth	Current health of the character
	 */
	public int getCurrentHealth() {
		return this.currentHealth;
	}
	
	
	/**
	 * Get the weapon the character is carrying
	 * @return wieldedWeapon	Weapon currently wielded by the character
	 */
	public Weapon getWieldedWeapon() {
		return this.wieldedWeapon;
	}
	
	
	/**
	 * Get the character's inventory
	 * @return The CharacterInventory of the Character
	 */
	public CharacterInventory getInventory() {
		return inventory;
	}
			
	
	/**
	 * Converts the Character to a JSONObject for saving
	 * @return	The JSONObject 
	 * @throws JSONException Thrown if there is a problem when constructing the JSON
	 */
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = super.toJSONObject();

		json.put("maxHealth", maxHealth);
		json.put("currentHealth", currentHealth);
		
        if(wieldedWeapon != null) {
        	json.put("wieldedWeapon", wieldedWeapon.getUniqueId() );
        }
        
        json.put("inventory", inventory.toJSONObject());

		return json;
	}
	
	
	
	// Mutators	
	/**
	 * Change the currentHealth of a character
	 * @param currentHealth 
	 */
	public void setCurrentHealth(int currentHealth) {
		if(currentHealth < 0 || currentHealth > this.maxHealth) {
			throw new IllegalArgumentException("Health value out of range");
		}
		this.currentHealth = currentHealth;
	}
	
	
	/**
	 * Decrease the health of the character
	 * @param	amount	Amount to decrease character's health by
	 * 					Note: Character's health does not decrease past 0
	 */
	public void decreaseHealth(int amount) {
		if(amount > this.getCurrentHealth()) {
			this.setCurrentHealth(0);
		}
		else {
			this.setCurrentHealth(this.getCurrentHealth() - amount);
		}
	}
	
	
	/**
	 * Change the weapon the character is carrying.
	 * @param wieldedWeapon
	 */
	public void setWieldedWeapon(Weapon wieldedWeapon) {
		this.wieldedWeapon = wieldedWeapon;
	}		
}

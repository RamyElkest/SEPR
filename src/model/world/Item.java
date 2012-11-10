package model.world;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Item - The base Item type
 * 
 * Provides flags: carriable, usable, attackable
 * 
 * Extended by more specific item types (Medicine, Weapon, ...)
 * 
 * @author Llamas
 */
public class Item extends RoomEntity {
	/*
	 * Instance variables
	 */
	private boolean carriable;					// Whether the item can be carried (by a character)
	private boolean usable;						// Whether the item can be used (by a character)
	private boolean attackable;					// Whether the item can be attacked
	private Item transformsTo;
	
	
	/**
	 * Constructor
	 */
	public Item(boolean carriable, boolean usable, boolean attackable) {
		super();
		this.carriable = carriable;
		this.usable = usable;
		this.attackable = attackable;
		this.transformsTo = null;
	}
	
	
	public void setTransformsTo(Item transformsTo) {
		this.transformsTo = transformsTo;
	}
	public Item getTransformsTo() {
		return transformsTo;
	}
	
	
	// Accessors
	/**
	 * Get whether the item is carriable
	 * @return	Whether the item is carriable
	 */
	public boolean isCarriable() {
		return this.carriable;
	}
	
	
	/**
	 * Get whether the item is usable
	 * @return	Whether the item is usable
	 */
	public boolean isUsable() {
		return this.usable;
	}
	
	
	/**
	 * Get whether the item is attackable
	 * @return	Whether the item is attackable
	 */
	public boolean isAttackable() {
		return this.attackable;
	}
	
	
	/**
	 * Returns a JSON representation of the Item for saving.
	 * @return The item in JSON form as a JSONObject
	 */
	final public JSONObject toJSONObject() throws JSONException {
		JSONObject json = toItemJSONObject();
		
		if (transformsTo != null)
			json.put("transformsTo", transformsTo.toItemJSONObject());
		
		return json;
	}
	
	/**
	 * Returns a JSON representation of the Item for saving.
	 * This creates the item without including the transformsTo attribute
	 * You should override this instead of toJSONObject
	 * @return The item in JSON form as a JSONObject
	 */
	public JSONObject toItemJSONObject() throws JSONException {
		JSONObject json = super.toJSONObject();
		json.put("type", "item");
		json.put("usable", this.isUsable());
		json.put("carriable", this.isCarriable());
		
		//only need to serialize this if it is false
		if(!this.isAttackable())
			json.put("attackable", this.isAttackable());
		
		return json;
	}
	
	
}

	
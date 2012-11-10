package model.world;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.InvalidCapacityException;
import exceptions.InventoryFullException;
import exceptions.ItemNotCarriableException;


/**
 * The inventory of a character in the game (the player or an NPC). This differs from a room inventory because a charcter
 * inventory cannot hold a character but a room inventory can.
 * 
 * @author Llamas
 *
 */
public class CharacterInventory extends Inventory {
	/*
	 * Instance variables
	 */
	private int maxItems;					// Maximum items allowed in the character's inventory
	
	
	
	/**
	 * Constructor
	 * @param	maxItems	Maximum items that can exist within the inventory
	 */
	public CharacterInventory(int maxItems) {
		super();
		this.setMaxItems(maxItems);
	}
	
	
	
	// Accessors
	/**
	 * Get the maximum number of items allowed in the inventory
	 * @return	Maximum number of items that can go in the inventory
	 */
	public int getMaxItems() {
		return this.maxItems;
	}


	/**
	 * Get whether the inventory is full
	 * @return	Whether the inventory is full
	 */
	public boolean isFull() {
		return this.getNumberOfItems() >= this.getMaxItems();
	}
	
	
	/**
	 * Add carriable item to the inventory
	 * @param	item	Carriable item to add to the inventory
	 */
    @Override
    public void addItem(Item item) {
    	if(!item.isCarriable()) {
    		throw new ItemNotCarriableException();
    	}
    	if(this.getNumberOfItems() < this.maxItems) {
    		super.addItem(item);
    	}  else{
    		throw new InventoryFullException();
    	}
    	
    		
    }
    

    /**
     * Convert to JSON representation
     * @return	JSON representation of the character inventory
     */
    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = super.toJSONObject();
        json.put("maxItems", this.maxItems);

        return json;
    }
    
    
    // Mutators
    /**
     * Set the maximum number of items that can be stored in the character's invetory
     * @param	maxItems	Maximum number of items that can be stored within the inventory
     */
    public void setMaxItems(int maxItems) {
    	if(maxItems < 0) {
			throw new InvalidCapacityException();
		}
		this.maxItems = maxItems;
    }
}

package model.world;

import java.util.ArrayList;
import java.util.ListIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exceptions.NonExistantItemException;


/**
 * Inventory - Provides functionality for storing and retrieving Items
 * 
 * @author Llamas 
 *
 */
public abstract class Inventory {
	/*
	 * Instance variables
	 */
	private ArrayList<Item> items;					// Items within the inventory
	
	
	
	/**
	 * Constructor 
	 */
	public Inventory() {
		super();
		this.items = new ArrayList<Item>(); 
	}
	
	
	
	// Accessors
	/**
	 * Get the items in the inventory
	 * @return	Items in the inventory 
	 */
	public ArrayList<Item> getItems() {
		return this.items;
	}
	
	
	/**
	 * Get the number of items in the inventory
	 * @return	Number of items in the inventory
	 */
	public int getNumberOfItems() {
		return this.items.size();
	}
	
	
	/**
	 * Get string representation of the inventory
	 * @return	String representation of the items within inventory
	 */
	@Override
	public String toString() {
		String str = "Inventory:";
		for(int i = 0; i < this.items.size(); i++) {
			str += "\n";
			str += (char)('A'+i) +") ";
			str += this.items.get(i).getName();
		}
		return str;
	}	
	
	
	// Mutators
	/**
	 * Add an item to the inventory
	 * @param	item	Item to add
	 */
	public void addItem(Item item) {
		this.getItems().add(item);
		
		assert(this.contains(item));
	}


	/**
	 * Remove an item from the inventory
	 * @param	item	Item to remove
	 */
	public void removeItem(Item item) {
		if(this.contains(item)) {
			this.getItems().remove(item);
		}
		else {
			throw new NonExistantItemException("Cannot remove item "+ item.getName() +" from inventory as it does not exist within the inventory");
		}
		assert(!this.contains(item));
	}
	
	
	/**
	 * Get whether the inventory contains a specific world entity
	 * @param	item	Item to check for
	 * @return	Whether the inventory contains the item	
	 */
	public boolean contains(Item item) {
		return this.getItems().contains(item);
	}
	
	/**
	 * Sorts the inventory, placing the:
	 * - best weapon at the front slot
	 * - medicine after that
	 * - misc items
	 * - other weapons after that
	 */
	public void sort() {
		ListIterator<Item> iterator = this.getItems().listIterator();
		Item bestWeapon = null;
		int bestValue = 0;
		
		ArrayList<Item> medicine = new ArrayList<Item>();
		ArrayList<Item> weapons = new ArrayList<Item>();
		ArrayList<Item> misc = new ArrayList<Item>();
		while(iterator.hasNext())
		{
			Item currentItem = iterator.next();
			if (currentItem instanceof Weapon)
			{
				int currentValue = ((Weapon) currentItem).getEaseOfUse() * (((Weapon) currentItem).getMaxDamage() + ((Weapon) currentItem).getMinDamage()) / 2;
				if (bestWeapon != null)
				{
					if (currentValue > bestValue)
					{
						weapons.add(bestWeapon);
						bestWeapon = currentItem;
						bestValue = currentValue;
					}
					else
					{
						weapons.add(currentItem);
					}
				}
				else
				{
					bestWeapon = currentItem;
					bestValue = currentValue;
				}
			}
			else if (currentItem instanceof Medicine)
			{
				medicine.add(currentItem);
			}
			else
			{
				misc.add(currentItem);
			}
		}
		this.items.clear();
		if (bestWeapon != null)
		{
			this.items.add(bestWeapon);
		}
		this.items.addAll(medicine);
		this.items.addAll(misc);
		this.items.addAll(weapons);
	}
	
	
	/**
	 * Returns a JSONObject containing a representation of the Items in the inventory
	 * @return The JSONObject
	 * @throws JSONException 
	 */
	public JSONObject toJSONObject() throws JSONException {
		// Create a JSONObject to return
		JSONObject json = new JSONObject();

		// Create a JSONArray to store the Item data
		JSONArray inventoryArray = new JSONArray();

		// Put all items into the inventory array
		for(Item item : items) {
			inventoryArray.put(item.toJSONObject());
		}
		json.put("items", inventoryArray);   //I know this seems dumb but it's clean and simple

		return json;
	}
}

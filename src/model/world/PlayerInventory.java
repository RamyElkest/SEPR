package model.world;

public class PlayerInventory extends CharacterInventory {

	public PlayerInventory(int maxItems) {
		super(maxItems);
	}
	
	/**
	 * Add carriable item to the inventory
	 * @param	item	Carriable item to add to the inventory
	 */
    @Override
    public void addItem(Item item) {
		super.addItem(item);
		sort();
    }
    
    /**
	 * Remove an item from the inventory
	 * @param	item	Item to remove
	 */
	public void removeItem(Item item) {
		super.removeItem(item);
		sort();
	}

}

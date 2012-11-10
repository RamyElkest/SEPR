package controller.executor;

import controller.parser.CommandType;
import exceptions.NonExistantItemException;
import model.gameEngine.ExecutorEvent;
import model.gameEngine.GameEngineInterface;
import model.world.Inventory;
import model.world.Item;
import model.world.Player;
import model.world.Weapon;
import model.world.WorldEntity;

public class TransformExecutor extends Executor {

	public TransformExecutor(GameEngineInterface gameEngine) {
		super(gameEngine);
	}

	@Override
	public void handleInput(ExecutorEvent event) {
		
		onInput(event);
		this.gameEngine.getInputHandler().popExecutor();
		
		
	}
	
	public void onInput(ExecutorEvent event) {
		String input = event.getInput();
		WorldEntity entity = event.getCommand().getReference();
		if (entity == null) {
			this.gameEngine.getStandardMessageGenerator().missingReferenceEntity(event.getCommand().getType());
			return;
		}
		
		
		if (entity instanceof Item) {
			Item item = (Item) entity;

			Item transforms = item.getTransformsTo();
			// transform knife
			// transform knife into banana
			// transform knife to banana
			if (input.matches("transform .+ (?:to|into) "+transforms.getName().toLowerCase())) {
				this.addToUndoStack(CommandType.TRANSFORM);
				this.transform(item);
				gameEngine.getStandardMessageGenerator().transformItem(item, transforms);
				return;
			}
			else {
				gameEngine.getStandardMessageGenerator().itemCanBeTransformed(item);
				return;
			}
			
		}
		gameEngine.getStandardMessageGenerator().cannotTransformItem(entity);
		
		
		
	}

	// Transform the item into its transform equivalent 
	public void transform(Item item) {
		
		Player player = gameEngine.getWorld().getPlayer();
		Inventory playerInv = player.getInventory();
		Inventory roomInv = player.getCurrentRoom().getInventory();
		
		Inventory currentInv = null;
		if (roomInv.contains(item)) {
			currentInv = roomInv;
		}
		else if(playerInv.contains(item)) {
			currentInv = playerInv;
		}
		else {
			throw new NonExistantItemException("Item "+item.getName()+" is out of reach");
		}
		
		Item newItem = item.getTransformsTo();
		
		item.setTransformsTo(null);		 // switch the item and the newItem
		newItem.setTransformsTo(item); // 
		
		// Take the old item out of the inventory
		currentInv.removeItem(item);
		
		// Add new item into current inventory unless player can't carry it
		if (currentInv == playerInv && !newItem.isCarriable()) {
			roomInv.addItem(newItem);
		}
		else {
			currentInv.addItem(newItem);
		}
		
		// Unwield item if the transformed item cannot be used
		if (player.getWieldedWeapon() == item) {
			if (newItem.isUsable() && newItem instanceof Weapon) {
				player.setWieldedWeapon((Weapon) newItem);
			}
			else {
				player.setWieldedWeapon(null);
			}
		}
		
		
	}

}

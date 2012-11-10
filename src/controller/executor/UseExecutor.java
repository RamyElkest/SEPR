package controller.executor;

import controller.parser.Command;
import controller.parser.CommandType;

import exceptions.WieldedWeaponNotInInventoryException;

import model.gameEngine.GameEngineInterface;
import model.gameEngine.ExecutorEvent;

import model.world.CharacterInventory;
import model.world.Medicine;
import model.world.Player;
import model.world.RoomInventory;
import model.world.Weapon;
import model.world.WorldEntity;
import model.world.Item;


/**
 * Handles the execution of the USE command.
 * 
 * @author Llamas/Yaks
 */
public class UseExecutor extends Executor {

	/**
	 * Constructor
	 * @param	gameEngine	Controls the game
	 */
	public UseExecutor(GameEngineInterface gameEngine) {
		super(gameEngine);
	}



	/**
	 * Execute use based on the input received
	 * @param	event	Input event to execute
	 */
	@Override
	public void handleInput(ExecutorEvent event) {
		Command command = event.getCommand();
		
		// Ensure command should be executed by this executor
		if(command.getType() == CommandType.USE) {
			this.use(command.getReference());
		}
		// Unregister self as input handler as have completed managing input
		this.gameEngine.getInputHandler().popExecutor();
	}	


	/**
	 * Make the player use the given entity
	 * @param	referenceEntity	Entity to use
	 */
	public void use(WorldEntity referenceEntity) {
		if(referenceEntity instanceof Weapon) {
			this.useWeapon((Weapon) referenceEntity);
		}
		else if(referenceEntity instanceof Medicine) {
			this.useMedicine((Medicine) referenceEntity);
		}
		else if(referenceEntity instanceof Item) {
			Item item = (Item) referenceEntity;
			if(!item.isUsable()) {
				// Item is not usable therefore it will not do anything when used
				this.gameEngine.getStandardMessageGenerator().usingHasNoEffect(item);
			}
			else {
				
				//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
				this.addToUndoStack(CommandType.USE);
				
				// If an item is usable, it will be an objective, which will subsequently 
				// be completed and show its "onComplete" message now
				this.gameEngine.getBrowser().println("You use "+ item.getName() +"...");
			}
		}
		else if(referenceEntity == null) {
			this.gameEngine.getStandardMessageGenerator().unknownInteractionEntity(CommandType.USE);
		} else {
			this.gameEngine.getStandardMessageGenerator().usingHasNoEffect(referenceEntity);
		}
	}



	// Private methods
	/*
	 * Use for medicine
	 * @param	medicine	Medicine to use (restores player's health)
	 */
	private void useMedicine(Medicine medicine) {

		CharacterInventory playerInventory = this.getPlayer().getInventory();
		RoomInventory roomInventory = this.getPlayer().getCurrentRoom().getInventory();

		boolean canUse = true;
		if(playerInventory.contains(medicine)) {

			//STORE THE STATE OF THE GAME BEFORE EXECUTING THE COMMAND
			this.addToUndoStack(CommandType.USE);

			// Medicine in player's inventory
			playerInventory.removeItem(medicine);
		}
		else if(roomInventory.contains(medicine)) {
			// Medicine in room's inventory
			roomInventory.removeItem(medicine);
		}
		else {
			// Medicine not accessible
			canUse = false;
		}

		if(canUse) {
			this.getPlayer().setCurrentHealth(this.getPlayer().getMaxHealth());
			this.gameEngine.getBrowser().println(
					this.getPlayer().getName() +" used "+ medicine.getName() +" and is now fully healed"
					);
		}
		else {
			this.gameEngine.getBrowser().println(
					this.getPlayer().getName() +" cannot use "+ medicine.getName() +" because it is not in their inventory nor in the current room"
					);
		}
	}


	/*
	 * Use for weapon
	 * @param	weapon	Weapon to use
	 */
	private void useWeapon(Weapon weapon) {

		CharacterInventory playerInventory = this.getPlayer().getInventory();
		RoomInventory roomInventory = this.getPlayer().getCurrentRoom().getInventory();

		if(this.getPlayer().getWieldedWeapon() == weapon) {
			// Player already wielding the given weapon
			this.gameEngine.getBrowser().println(
					this.getPlayer().getName() +" is already wielding "+ weapon.getName()
					);
		}

		else if(this.getPlayer().getWieldedWeapon() == null
				&& this.getPlayer().getInventory().getNumberOfItems() >= this.getPlayer().getInventory().getMaxItems() + (roomInventory.contains(weapon) ? 1 : 0)) {
			// Player will not drop a weapon (as no weapon to drop) and their inventory is already full.
			// Therefore they cannot take the new weapon.
			this.gameEngine.getStandardMessageGenerator().connotUseWeaponBecauseInventoryIsFull(weapon);
		}

		else {

			if(roomInventory.contains(weapon)) {
				if (this.getPlayer().getInventory().getNumberOfItems() >= this.getPlayer().getInventory().getMaxItems())
				{
					// Drop player's current weapon to room (if player is carrying one and can't carry any more items)
					Weapon oldWeapon = this.getPlayer().getWieldedWeapon();
					if(oldWeapon != null) {
						if(!playerInventory.contains(oldWeapon)) {					// Ensure that logic errors with weapon wielding are detected
							throw new WieldedWeaponNotInInventoryException();
						}					
						this.getPlayer().setWieldedWeapon(null);
						playerInventory.removeItem(oldWeapon);
						this.gameEngine.getStandardMessageGenerator().playerDropsWieldedWeapon(oldWeapon);
						roomInventory.addItem(oldWeapon);
					}
				}
						
				// Remove weapon from current location
				roomInventory.removeItem(weapon);
						
				// Add new weapon to player's inventory
				playerInventory.addItem(weapon);
					
				// Set as wielded weapon
				this.getPlayer().setWieldedWeapon(weapon);
					
				// Display textual feedback
				this.gameEngine.getBrowser().println(
					this.getPlayer().getName() +" wielded "+ weapon.getName()
				);
			}
			else if(playerInventory.contains(weapon)) {
				// Set as wielded weapon
				this.getPlayer().setWieldedWeapon(weapon);
					
				// Display textual feedback
				this.gameEngine.getBrowser().println(
					this.getPlayer().getName() +" wielded "+ weapon.getName()
				);
			}
			else {
				this.gameEngine.getStandardMessageGenerator().weaponNotAccessible(weapon);
			}
		}
        }


	/*
	 * Get the player from the current game world
	 * @return	Current game world player
	 */
	private Player getPlayer() {
		return this.gameEngine.getWorld().getPlayer();
	}
}

package model.world.dummyclasses;

import model.world.Character;
import model.world.Weapon;


/**
 * A dummy Character implementation for testing
 * 
 * @author Llamas
 *
 */
public class DummyCharacter extends Character {
	/**
	 * Constructor
	 * @param maxHealth
	 * @param wieldedWeapon
	 */
	public DummyCharacter(int maxHealth, Weapon wieldedWeapon, int maxInventoryItems) {
		super(maxHealth, wieldedWeapon, maxInventoryItems);
	}
};	

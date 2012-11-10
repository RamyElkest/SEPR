package model.world.dummyclasses;

import java.util.ArrayList;

import model.world.Npc;
import model.world.Weapon;


/**
 * A dummy Npc implementation for testing
 * 
 * @author Llamas
 *
 */
public class DummyNpc extends Npc {
	/**
	 * Constructor
	 * @param currentHealth
	 * @param narratives
	 * @param wieldedWeapon
	 */
	public DummyNpc(int currentHealth, ArrayList<String> narratives, Weapon wieldedWeapon) {
		super(currentHealth, narratives, wieldedWeapon);
	}
}

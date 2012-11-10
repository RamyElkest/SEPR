package model.world.dummyclasses;

import java.util.ArrayList;

import model.world.HostileNpc;
import model.world.Weapon;


/**
 * A dummy HostileNpc implementation for testing
 * 
 * @author Llamas
 *
 */
public class DummyHostileNpc extends HostileNpc {
	/**
	 * Constructor
	 */
	public DummyHostileNpc(int maxHealth, ArrayList<String> narratives, Weapon wieldedWeapon, double attackProbability) {
		super(maxHealth, narratives, wieldedWeapon, attackProbability);
	}
}

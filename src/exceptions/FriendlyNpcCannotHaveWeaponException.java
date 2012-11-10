package exceptions;

/**
 * Thrown when a FriendlyNpc object is given a Weapon 
 * (this should not happen in the map - it is just to detect incorrect code)
 * @author Llamas
 *
 */
public class FriendlyNpcCannotHaveWeaponException extends RuntimeException {

}

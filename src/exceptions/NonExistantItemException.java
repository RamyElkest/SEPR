package exceptions;

/**
 * Thrown if an item is referred to in the map which does not actually exist
 * @author Llamas
 *
 */
public class NonExistantItemException extends RuntimeException {
	public NonExistantItemException(String message) {
		
	}
}

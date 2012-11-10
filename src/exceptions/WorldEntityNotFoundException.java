package exceptions;

/**
 * Thrown if a WorldEntity is referred to which does not actually exist
 * @author Llamas
 *
 */
public class WorldEntityNotFoundException extends Exception {
	String entityId;
	
	public WorldEntityNotFoundException(String entityId) {
		this.entityId = entityId;
	}
	
	/**
	 * Returns the message contained within this exception
	 * @return The message contained within this exception
	 */
	@Override
	public String getMessage() {
		return entityId;
	}
}

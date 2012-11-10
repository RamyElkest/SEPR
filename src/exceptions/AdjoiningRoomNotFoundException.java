package exceptions;

/**
 * Thrown when a map refers to an adjoining Room which does not exist
 * @author Llamas
 *
 */
public class AdjoiningRoomNotFoundException extends Exception {
	private String uniqueId;
	
	public AdjoiningRoomNotFoundException(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	@Override
	public String getMessage() {
		return this.uniqueId;
	}
}

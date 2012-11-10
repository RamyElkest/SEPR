package exceptions;

/**
 * Thrown if the InputHandler stack is empty and an event is recieved
 * (should never happen)
 * @author Llamas
 *
 */
public class NoInputHandlerException extends RuntimeException {
	/*
	 * Instance variables
	 */
	private String message;
	
	
	
	/**
	 * Constructor
	 * @param	message	Message associated with the exception
	 */
	public NoInputHandlerException(String message) {
		super();
		this.message = message;
	}
	
	
	/**
	 * 
	 */
	@Override
	public String getMessage() {
		return this.message;
	}
}

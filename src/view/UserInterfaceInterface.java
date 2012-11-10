package view;


/**
 * UserInterfaceInterface
 * 
 * Provides a generic interface for game UI classes
 * 
 * @author Llamas
 *
 */
public interface UserInterfaceInterface {

        /**
	 * Get object responsible for getting text from the user via browser
	 * @return	Object (Applet) that gets user input
	 */
	public BrowserTextHandler getBrowser();
        
        /**
	 * Called when an user input is given from TextInput
	 * @param	inputText	String
	 */
	public void printInput(String inputText);
}

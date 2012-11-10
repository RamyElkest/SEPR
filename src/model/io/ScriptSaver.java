package model.io;

import model.gameEngine.GameEngine;
import exceptions.InvalidFileLocation;


/**
 * Coordinates the saving of the user's session script (SCRIPT)
 * @author Llamas
 */
public class ScriptSaver {
	/*
	 * Instance variables
	 */
	private GameEngine gameEngine;					// Controls the game
	
	
	
	/**
	 * Constructor
	 * @param	gameEngine	Object that controls the game
	 */
	public ScriptSaver(GameEngine gameEngine) {
		super();
		this.gameEngine = gameEngine;
	}
	
	
	
	/**
	 * Start user dialogue as to where they wish the session script to be saved
	 */
	public void startUserSaveDialogue() {
		try {
			// Prompt user for save location
			this.gameEngine.getBrowser().println("Please select the location of where you wish the session log to be saved\n");
			
			// Get location to save file to
			
			// Save file
			FileOutput fileOutput = new FileOutput();
			if (fileOutput.saveStringToFile(
				null,										
				this.gameEngine.getBrowser().getAllDisplayed()
			)) {
			
                            // Give confirmation to user
                            this.gameEngine.getBrowser().println("Session saved successfully");
                        } else {
                            
                            this.gameEngine.getBrowser().println("Save cancelled");
                        }
		}
		/*catch(InvalidFileLocation e) {
			this.gameEngine.getBrowser().println(
				"The location you selected was invalid - please try to save your session script again at a location that you have write access to."
			);
		}*/
		catch(Exception e) {
			this.gameEngine.getBrowser().println(
				"Unfortuately an error occurred whilst attempting to save the script for this session in the specifed location. "+
				"Error message: "+ e.getMessage()
			);
		}
	}
}

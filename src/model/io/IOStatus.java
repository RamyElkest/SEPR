package model.io;


/**
 * I/O statuses that may be encountered (provides a mapping between exceptions and useful messages, 
 *  used by WorldSaver/WorldLoader
 *  
 * @author Llamas
 */
public enum IOStatus {
	/*
	 * Statuses
	 */
	SAVE_SUCCESS("Your game was saved successfully."),
	LOAD_SUCCESS("Game loaded successfully."),
	CONVERSION_ERROR("An error occurred whilst converting your game world to be saved as JSON.\nPlease try again later."),
	
	PARSE_ERROR("An error occurred whilst parsing your saved game world.\nUnfortunately, this indicates that the save is corrupt and cannot be played."),
	WRITE_ERROR("Your game could not be saved to the location you requested.\nPlease ensure you have write permissions to this location or save the game elsewhere"),
	
	INVALID_LOAD_LOCATION("The location you selected to load a game from was invalid.\nPlease select a valid location of which you have read access."),
	INVALID_SAVE_LOCATION("The location you selected to save your game to was invalid.\nPlease select a valid location of which you have write access."),
	
	GENERAL_LOAD_ERROR("A game was not loaded."),
	GENERAL_SAVE_ERROR("Your game was not saved.");
		
	
	
	/*
	 * Instance variables
	 */
	private String message;				// Message associated to status			
	
	
	
	/*
	 * Constructor 
	 * @param	message	Message contained within the i/o message
	 */
	private IOStatus(String message) {
		this.message = message;
	}
	
	
	
	// Accessors
	/**
	 * Get the status as a string (message)
	 * @return	Message associated to the status
	 */
	@Override
	public String toString() {
		return this.message;
	}
}

package model.io;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.InvalidFileLocation;

import model.gameEngine.GameEngineInterface;
import model.world.World;
import view.BrowserTextHandler;


/**
 * Coordinates the saving of a game world from memory to disk (as JSON)
 * @author Llamas / Yaks
 */
public class WorldSaver {
	/*
	 * Instance variables
	 */
	private GameEngineInterface gameEngine;					// gameEngine Allows access the world
	private BrowserTextHandler browserText;						//Allows printing to the UI



	/**
	 * Constructor
	 * @param	gameEngine	The gameEngine that wants to save with this WorldSaver
	 */
	public WorldSaver(GameEngineInterface gameEngine) {
		this.gameEngine = gameEngine;
		this.browserText = gameEngine.getBrowser();
	}



	/**
	 * Start user controlled save of the game world
	 * @return	Whether the user successfully saved their game
	 */
	public boolean startUserSaveDialogue() {

		// Inform user how to save
		browserText.println("Please select where you wish to save your game using the dialogue box displayed.");

		boolean successfulSave = false;
		// Get location where user wishes to save their game file to
		String saveLocation = this.getUserDefinedSaveLocation();

		try {
			if(saveLocation != null) {
				// Attempt to save game file
				successfulSave = this.saveWorldtoFile(this.gameEngine.getWorld(), saveLocation);
				if (successfulSave)
                                    browserText.println(IOStatus.SAVE_SUCCESS.toString());
			}
			//If game file cannot be saved
		}
		catch(JSONException e) {
			browserText.println(IOStatus.CONVERSION_ERROR.toString());
		}
		catch(IOException e) {
			browserText.println(IOStatus.WRITE_ERROR.toString());
		}


		if(!successfulSave) {
			// Saving unsuccessful
			browserText.println(IOStatus.GENERAL_SAVE_ERROR.toString());
		} 
		return successfulSave;
	}


	/**
	 * Save game world to an accessible location
	 * @param 	world	World that is to be saved
	 * @param	saveLocation	Location to save world to
	 * @throws	JSONException	Error converting game world to JSON
	 * @throws	IOException	Error saving game to the requested location
         * @return True if file was saved
	 */
	public boolean saveWorldtoFile(World world, String saveLocation) throws JSONException, IOException {

		//Convert the World to JSon and return the string
		String worldAsJson = this.saveWorldtoString(world);

		// Pretty print the JSON
		JsonPrettyFormatter prettyPrinter = new JsonPrettyFormatter();
		worldAsJson = prettyPrinter.format(worldAsJson);

		// Save the data
		FileOutput fileOutput = new FileOutput();
		return fileOutput.saveStringToFile(saveLocation, worldAsJson);
	}

	/**
	 * Converts the game world to a JSON string and returns
	 * @return
	 * @throws JSONException Error converting world to JSON
	 * @throws IOException Error Saving
	 */
	public String saveWorldtoString(World world)throws JSONException, IOException {
		// Convert the world to JSON 
		JSONObject worldAsJSONObject = world.toJSONObject();
		String worldAsJson =  worldAsJSONObject.toString();
		
		// return String of JSON
		return worldAsJson;
	}



	// Private methods
	/*
	 * Ask user for location to save game to
	 * @return	Location user wishes to save game to, null if invalid location
	 */
	private String getUserDefinedSaveLocation() {
		String saveLocation = null;
		try {
			LocationChooser locationChooser = new LocationChooser();
			saveLocation = locationChooser.getSaveLocation();
		}
		catch (InvalidFileLocation e) {
			// Invalid location selected
			browserText.println(IOStatus.INVALID_SAVE_LOCATION.toString());
		}
		return saveLocation;
	}
}

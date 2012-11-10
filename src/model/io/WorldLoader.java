package model.io;

import java.io.IOException;

import org.json.JSONException;

import controller.executor.EndOfGameExecutor;

import exceptions.AdjoiningRoomNotFoundException;
import exceptions.InvalidFileLocation;
import exceptions.WorldEntityNotFoundException;

import java.util.Scanner;
import view.BrowserTextHandler;

import model.gameEngine.GameEngineInterface;
import model.world.World;



/**
 * Coordinate the loading of worlds, Whether stored in a file as JSON, or as a string on the undo Stack, into memory
 * @author Llamas / Yaks
 *
 */
public class WorldLoader {
	/*
	 * Instance variables
	 */
	private GameEngineInterface gameEngine;						// Controls the game 
	private WorldFactory worldFactory;                          // Creates a world from a string of JSON
	private BrowserTextHandler browserText;                            // Outputs text to the UI TextArea


	/**
	 * Constructor
	 * @param	gameEngine	Object that controls the game
	 */
	public WorldLoader(GameEngineInterface gameEngine) {
		super();
		this.gameEngine = gameEngine;
		this.worldFactory = new WorldFactory();
		this.browserText = gameEngine.getBrowser();
	}



	/**
	 * Start user controlled load of a game world
	 */
	public boolean startUserLoadDialogue(){	

		// Inform user how to load
		browserText.println("Please select where you wish to LOAD your game from using the dialogue box displayed.");

		boolean successfulLoad = false;

		// Get location where user wishes to LOAD their file from 
		try{
                                //pass null to prompt the file dialog with user
				this.loadWorld(null);
				successfulLoad = true;

				browserText.println(IOStatus.LOAD_SUCCESS.toString());
				// Display player's name location and description
				gameEngine.getStandardMessageGenerator().showCurrentPlayerRoom(gameEngine.getWorld().getPlayer().getCurrentRoom());	
			//}
			//If world cannot be loaded
		} catch (JSONException e) { 
			browserText.println("A fatal error occurred whilst parsing the default world json string\nError details: "+ e.getMessage());
			this.onLoadFail();
		}
		catch (IOException e) {
			browserText.println("A fatal error occurred whilst getting the file holding the default world\nError details: "+ e.getMessage());
			this.onLoadFail();
		}
		catch (WorldEntityNotFoundException e) {
			browserText.println("A fatal error occurred whilst parsing the default world json string\nError details: "+ e.getMessage());
			this.onLoadFail();
		}
		catch (AdjoiningRoomNotFoundException e) {
			browserText.println("A fatal error occured whilst linking adjoining rooms. Room not found: "+ e.getMessage());
			this.onLoadFail();
		}

		//Return whether the game has successfully loaded
		return successfulLoad;
	}



	/**
	 * Loads the world from a String of JSON when the undo Command is executed
	 * @param worldStringContents
	 */
	public void loadWorldOnUndo(String worldStringContents){
		
		try {
			//Create a World entity from the JSON String
			World world = worldFactory.createWorld(worldStringContents);
			// Set world as one being currently played
			this.gameEngine.setWorld(world);
			// Signify to user that the change to the world state  was loaded successfully
			browserText.print("Undo Complete: ");

		}
		catch (JSONException e) {
			browserText.println("A fatal error occurred whilst parsing the world json string\nError details: "+ e.getMessage());
			this.onLoadFail();
		}
		catch (WorldEntityNotFoundException e) {
			browserText.println("A fatal error occurred whilst parsing the world json string\nError details: "+ e.getMessage());
			this.onLoadFail();
		}
		catch (AdjoiningRoomNotFoundException e) {
			browserText.println("A fatal error occured whilst linking adjoining rooms. Room not found: "+ e.getMessage());
			this.onLoadFail();
		}
	}



	/**
	 * Load a world from a specific location
	 * @throws WorldEntityNotFoundException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws AdjoiningRoomNotFoundException 
	 */
	public void loadWorld(String worldLocation) throws JSONException, IOException, WorldEntityNotFoundException, AdjoiningRoomNotFoundException {
		
		//Add Logo to display
		gameEngine.displayLogo();
		
		// Parse world
		World world = this.parseWorld(worldLocation);

                this.gameEngine.getBrowser().print("Parsed World ffs");
                
		// Set game as one being currently played
		this.gameEngine.setWorld(world);

		// Signify to user that the world was loaded successfully
		browserText.println(world.getName() + " successfully loaded!");

		// Display details of loaded world
		browserText.print("Name:\t\t"+ world.getName() +"\n");
		browserText.print("Author:\t\t"+ world.getAuthor() +"\n");
		browserText.print("Created:\t"+ world.getCreated() +"\n");
		browserText.println("Description:\t"+ world.getDescription());		

		// Display objectives
		browserText.println(this.gameEngine.getObjectiveHandler().toString());

	}


	/**
	 * Parse game world from an accessible JSON file
	 * @param	loadLocation	Location to load world from
	 * @return	Game world as World object
	 * @throws	JSONException	Error converting game world to JSON
	 * @throws	IOException	Error saving game to the requested location
	 * @throws	WorldEntityNotFoundException 	Thrown if the JSON refers to a WorldEntity which does not exist
	 * @throws AdjoiningRoomNotFoundException 	Thrown if the JSON refers to an adjoining Room which does not exist
	 */
	 public World parseWorld(String loadLocation) throws JSONException, IOException, WorldEntityNotFoundException, AdjoiningRoomNotFoundException {

               String worldAsJson = "";
               try {
                    //Create FileInput
                    FileInput loadFile = new FileInput();
                    //Get file as string from url
                    worldAsJson = loadFile.getFileAsString(loadLocation);
                    
                } catch (InvalidFileLocation e) {
                    // Invalid location selected
                    this.gameEngine.getBrowser().println(IOStatus.INVALID_LOAD_LOCATION.toString());
                } finally {
                // Convert data into world and return
                return worldFactory.createWorld(worldAsJson);
                }
        }


	/**
	 * Handles when a world fails to load
	 */
	public void onLoadFail() {
		this.gameEngine.getBrowser().println(
				"Unfortunately the game cannot be played as the game map is broken :("
				);
		this.gameEngine.getStandardMessageGenerator().restartGameQuery();
		this.gameEngine.getInputHandler().pushExecutor(
				new EndOfGameExecutor(this.gameEngine)
				);
	}



	// Private methods



	/*
	 * Ask user for location to load game from
	 * @return	Location user wishes to load game from, null if invalid location
	 */
/*	private String getUserDefinedLoadLocation() throws IOException {
		String LoadLocation = null;
		try {
			FileInput3 loadFile = new FileInput3();
                        LoadLocation = loadFile.getLoadFile();
		}
		catch (InvalidFileLocation e) {
			// Invalid location selected
			this.gameEngine.getBrowser().println(IOStatus.INVALID_LOAD_LOCATION.toString());
		}
		//Return a string containing the location of the load file
		return LoadLocation;
	}
*/

}


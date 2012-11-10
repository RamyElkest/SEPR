package model.gameEngine;


import java.io.IOException;
import org.json.JSONException;

import controller.executor.EndOfGameExecutor;
import controller.parser.Command;
import controller.parser.CommandType;
import exceptions.AdjoiningRoomNotFoundException;
import exceptions.WorldEntityNotFoundException;

import model.io.*;

import model.objective.ObjectiveHandler;

import model.world.World;

import view.BrowserTextHandler;
import view.StandardMessageGenerator;
import view.UserInterface;


/**
 * Controls the game, coordinating input, output and the manipulation of the game world.
 * Brings together all model, view and controller subsystems.
 * 
 * @author Llamas / Yaks
 */
public class GameEngine implements GameEngineInterface {
	/*
	 * Settings
	 */
	private final String DEFAULT_WORLD_LOCATION = "/resources/worlds/defaultWorld.js";			// Location of the default world to load 
	private final String ASCII_LOGO_LOCATION = "/resources/ascii-art/llamasLogo.txt";			// Location of (really awesome super cool) ascii logo
	
	
	
	/*
	 * Instance variables
	 */
	private World world;										// Current game's world
	private UserInterface userInterface;						// Object that controls the user interface
	private StandardMessageGenerator standardMessageGenerator; 	// A object that allows common messages to be generated easily
	private InputHandler inputHandler;							// Handles input, connecting view classes to model classes
	private ObjectiveHandler objectiveHandler;					// Handles the objectives of the game world that is currently being played
	private WorldLoader worldLoader;                            // Allows loading worlds
	private WorldSaver worldSaver;   							//Allows saving of worlds
	private UndoHandler undoHandler;

	
	
	/**
	 * Constructor
	 */
	public GameEngine(BrowserTextHandler browserText) {
		super();
                
		// Create UI
		this.userInterface = new UserInterface(browserText);
                
		// Create object to allow standard messages to be generated
		this.standardMessageGenerator = new StandardMessageGenerator(this);
                
		// Manage input from the user
		this.inputHandler = new InputHandler(this); 
                
		// Start default game world
		this.loadDefaultWorld();
                
		//Create a new world loader
		this.worldLoader = new WorldLoader(this);
                
		//Create a new world Saver
		this.worldSaver = new WorldSaver(this);
                
		this.undoHandler = new UndoHandler(this);
	}

        
	/**
	 * Gets the undoHandler
	 * @return undoHandler handles the execution of undo command
	 */
	public UndoHandler getUndoHandler() {
		return undoHandler;
	}

	/**
	 * Get game UI text input
	 * @return	Object controlling for getting input from the user
	 */
        @Override
	public BrowserTextHandler getBrowser() {
		return this.userInterface.getBrowser();
	}
        
        
	/**
	 * Get input handler
	 * @return	Handler for input
	 */
	public InputHandler getInputHandler() {
		return this.inputHandler;
	}

	
	/**
	 * Handle the death of a player within a game
	 */
	public void onPlayerDeath() {
		this.getStandardMessageGenerator().playerDied();	
		this.world = null;
		this.inputHandler.pushExecutor(new EndOfGameExecutor(this));
		this.getStandardMessageGenerator().restartGameQuery();
		undoHandler.clear();
	}
	
	
	/**
	 * To be called when the current game world has been completed
	 */
	public void onGameComplete() {
		this.displayLogo();
		this.getStandardMessageGenerator().gameComplete();
		this.world = null;
		this.inputHandler.pushExecutor(new EndOfGameExecutor(this));
		this.getStandardMessageGenerator().restartGameQuery();		
	}
	
	
	/**
	 * Get object that generates standard messages that are used within the game 
	 * @return 	The standard message generator
	 */
	public StandardMessageGenerator getStandardMessageGenerator() {
		return this.standardMessageGenerator;
	}
	
	
	/**
	 * Get the current world's objective handler
	 * @return	Objective handler for the current world
	 */
	public ObjectiveHandler getObjectiveHandler() {
		return this.objectiveHandler;
	}
		
	
	/**
	 * Get the game world that is currently being played
	 * @return	Current game world
	 */
	public World getWorld() {
		return this.world;
	}
	
	
	/**
	 * Change the game world that is being played
	 * @param	world	World to load in instead of the one being played
	 */
	public void setWorld(World world) {
		this.world = world;
		this.objectiveHandler = new ObjectiveHandler(this.world.getObjectives());
	}
	
	
	/**
	 * Prompt the user to load a game world from file
	 * @throws AdjoiningRoomNotFoundException 
	 * @throws WorldEntityNotFoundException 
	 * @throws JSONException 
	 */
	public void load() {
		if(worldLoader.startUserLoadDialogue())
			undoHandler.clear();
	}
	

	/**
	 * Prompt the user to save the game world to file (as JSON)
	 * If the world successfully saves wipe the stacks for undo.
	 */
	public void save() {
		if(worldSaver.startUserSaveDialogue())
			undoHandler.clear();
	}
	

	/**
	 * Save recording of the player's session in an ASCII text file, so that it can be reviewed later
	 */
	public void script() {
		ScriptSaver scriptSaver = new ScriptSaver(this);
		scriptSaver.startUserSaveDialogue();
	}

	
	/**
	 * Re-load the game world to the state that it was in before the last move was made
	 * (If player hasn't died, and there is a command to be undone)
	 */
	public void undo() {
		undoHandler.undo();
	}
	
	/**
	 * Load the default world 
	 */
	@Override
	public void loadDefaultWorld() {
		//Load the Default World
	try{
		this.getBrowser().println("Starting game with default game world.");
		this.worldLoader = new WorldLoader(this);
		this.worldLoader.loadWorld(this.DEFAULT_WORLD_LOCATION);
		//Simulate a NAME command: Take the User's Name and store it
		inputHandler.routeInputToExecutor("",new Command(CommandType.NAME) );
	}
	
	catch (JSONException e) {
	getBrowser().println("A fatal error occurred whilst parsing the default world json string\nError details: "+ e.getMessage());
		this.worldLoader.onLoadFail();
	}
	catch (IOException e) {
		getBrowser().println("A fatal error occurred whilst getting the file holding the default world\nError details: "+ e.getMessage());
		this.worldLoader.onLoadFail();
	}
	catch (WorldEntityNotFoundException e) {
		getBrowser().println("A fatal error occurred whilst parsing the default world json string\nError details: "+ e.getMessage());
		this.worldLoader.onLoadFail();
	}
	catch (AdjoiningRoomNotFoundException e) {
		getBrowser().println("A fatal error occured whilst linking adjoining rooms. Room not found: "+ e.getMessage());
		this.worldLoader.onLoadFail();
	}
	}
	
	
	

	
	
	/**
	 * Display game's logo to user
	 */
	public void displayLogo() {
		try {
                        /*
                        //get the line seperator for later use
                        String newline = System.getProperty("line.separator");
                        
                        Scanner sc = new Scanner(this.getClass().getResourceAsStream(this.ASCII_LOGO_LOCATION));
                        String logo = "";
                        while( sc.hasNextLine() )
                        {        
                            logo += sc.nextLine();
                            logo += '\n';
                        }
                        */
                        FileInput loadFile = new FileInput();
                        String logo = loadFile.getFileAsString(this.ASCII_LOGO_LOCATION);
			this.getBrowser().println(logo);
		}
		catch (Exception e) {
			this.getBrowser().println("(Logo could not be loaded from "+ this.ASCII_LOGO_LOCATION +") " + e.getMessage());
                        this.getBrowser().println("The current working directory is " + System.getProperty("user.dir"));
		}
	}
}

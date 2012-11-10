package model.gameEngine;

import org.json.JSONException;

import exceptions.AdjoiningRoomNotFoundException;
import exceptions.WorldEntityNotFoundException;

import view.StandardMessageGenerator;

import model.objective.ObjectiveHandler;
import model.world.World;
import view.BrowserTextHandler;


/**
 * An interface for GameEngine so we can write other classes without having implemented the GameEngine fully.
 * 
 * @author Llamas
 */
public interface GameEngineInterface {
	/**
	 * Get the game world that is currently being played
	 * @return	The player's current game world
	 */
	public World getWorld();
	
	
	/**
	 * Set the game world that is currently being played
	 * @param	world	World that is currently being played
	 */
	public void setWorld(World world);
	
	
	/**
	 * Load a saved game world from a JSON file
	 * @throws AdjoiningRoomNotFoundException 
	 * @throws WorldEntityNotFoundException 
	 * @throws JSONException 
	 */
	public void load();

	
	/**
	 * Save the current game world as a JSON file
	 */
	public void save();
	
	
	/**
	 * Saves the a log of the player's session to text file
	 */
	public void script();
	
	
	/**
	 * Undo player's previous move
	 * @param string 
	 */
	public void undo();
	
	/**
	 * Load the default world 
	 */
	public void loadDefaultWorld();
	
	
        /**
	 * Get game Browser text handler
	 * @return	Object controlling for getting input and outputting to browser
	 */
	public BrowserTextHandler getBrowser();
	
	/**
	 * Get input handler
	 * @return	Handler for input
	 */
	public InputHandler getInputHandler();
	
	
	/**
	 * Get the current world's objective handler
	 * @return	Objective handler for the current world
	 */
	public ObjectiveHandler getObjectiveHandler();
	

	/**
	 * To be called when the player has died, signalling the end of the current world's game 
	 */	
	public void onPlayerDeath();
	
	
	/**
	 * To be called when the user has completed their current game
	 */
	public void onGameComplete();

	
	/**
	 * Return a object which allows common output to be written automatically by
	 * calling the relevant methods
	 * @return 	The standard message generator
	 */
	public StandardMessageGenerator getStandardMessageGenerator();

	/**
	 * Displays the games Logo
	 */
	public void displayLogo();


	public UndoHandler getUndoHandler();



}

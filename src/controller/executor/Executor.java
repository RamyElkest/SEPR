package controller.executor;

import controller.parser.CommandType;

import java.io.IOException;

import org.json.JSONException;

import model.gameEngine.ExecutorEvent;
import model.gameEngine.GameEngineInterface;

/**
 *An abstract class for the Command Executor classes, that holds the game engine in which
 * the command should be executed, the command that the player entered and the addToUndoStackMethod 
 * which is used to save the state of the world before the command is executed
 *
 * @author Yaks
 */
public abstract class Executor {
	/*
	 * Declarations
	 */
	protected GameEngineInterface gameEngine;

	
	/**
	 * Constructor
	 * @param	gameEngine	The GameEngine in which commands should be executed
	 */
	public Executor(GameEngineInterface gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	
	
	/**
	 * Called by InputHandler,
	 * Transforms an ExecutorEvent for execution
	 */
	public abstract void handleInput(ExecutorEvent event);
	
	
	
	/**
	 * Adds a Json String representing the world (before the most recent command is 
	 * executed) and adds it to a stack.
	 * Adds the type of command to the executedCommandStack, to be output
	 * to the user when undo is called in format GET undone , MOVE undone e.c.t
	 * @param commandType 
	 */
	protected void addToUndoStack(CommandType commandType){
		try {
			gameEngine.getUndoHandler().addToStacks(commandType);
		} catch (JSONException e) {
			gameEngine.getBrowser().println("A fatal error occurred whilst the last command was being undone \nError details: "+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			gameEngine.getBrowser().println("A fatal error occurred whilst executing undo and saving the world \nError details: "+ e.getMessage());
			e.printStackTrace();
		}
	}

}

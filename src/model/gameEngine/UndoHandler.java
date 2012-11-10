package model.gameEngine;

import java.io.IOException;
import java.util.Stack;

import org.json.JSONException;

import model.io.WorldLoader;
import model.io.WorldSaver;

import controller.parser.CommandType;

/**
 * Class handing the execution of the undo command by adding JSON strings of the world and command types to stacks, when undo is called the most recent world and command
 * are popped of the stack and the world is loaded, effectively restoring the world to the 
 * @author Rosy
 *
 */
public class UndoHandler {
	
	private Stack<String> undoStack = new Stack<String>();	    // Stack of strings holding the world's state (in JSON) 
	protected Stack<CommandType> executedCommandStack = new Stack<CommandType>() ; //A stack holding all executed commands
	private WorldLoader worldLoader;                                                                   
	private WorldSaver worldSaver;
	private GameEngineInterface gameEngine;
	
	/**
	 * Constructor for class
	 * @param gameEngine  A GameEngineInterface
	 */
	public UndoHandler(GameEngineInterface gameEngine){
		this.gameEngine = gameEngine;
		this.worldLoader = new WorldLoader(gameEngine);	
		this.worldSaver = new WorldSaver(gameEngine);	
	}
	
	/**
	 * Wipe the Stacks for undo. Once the Game is saved/loaded previous commands cannot be undone.
	 */
	public void clear(){
		undoStack.clear();				
		executedCommandStack.clear();
	}
	
	
	/**
	 * Add a JSON String representing the world to the undoStack and the type of command 
	 * being executed to the executedCommandStack
	 */
	public void addToStacks(CommandType commandType) throws JSONException, IOException{
			undoStack.push(worldSaver.saveWorldtoString(gameEngine.getWorld()));
			executedCommandStack.push(commandType);	
	}
	
	/**
	 * Undo the last executed command by loading the World before the last command was executed from the undoStack then pop that world off
	 * the undoStack
	 */
	public void undo(){
		if(gameEngine.getWorld().getPlayer().getCurrentHealth() > 0){       //If the player isn't dead 
			if(!undoStack.isEmpty() && !executedCommandStack.isEmpty()){    //If there is something to be undone
				worldLoader.loadWorldOnUndo(undoStack.pop());
				
				//Output to the user what Command has been undone
				gameEngine.getBrowser().println(executedCommandStack.pop().name() + " Successfully Undone");
			}else{
				gameEngine.getStandardMessageGenerator().undoEmptyStackError();
			}
		}
	}
	
	
	
	
}

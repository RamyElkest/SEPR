package controller.executor;

import model.gameEngine.ExecutorEvent;
import controller.executor.Executor;
import model.gameEngine.GameEngineInterface;
import model.io.WorldSaver;

 
/**
 * Handles the EXIT/QUIT command. The ExitExecutor asks the user if they want to save the game
 * or cancel the quitting process and continue their game.
 *
 * @author Llamas
 */
public class ExitExecutor extends Executor{


	/**
	 * Constructor
	 *
	 * @param	gameEngine	Object controlling the game
	 */
	public ExitExecutor(GameEngineInterface gameEngine){
		super(gameEngine);
	}


	/**
	 * Called by InputHandler,
	 * Transforms an ExecutorEvent into a String for onInput for execution
	 *
	 * @param	event	Input event to execute
	 */
	@Override
	public void handleInput(ExecutorEvent event){
		this.onInput(event.getInput());
	}


	/**
	 * Handles input from the user
	 *
	 * @param	input	Input from the user
	 */
	public void onInput(String input){
		input = input.toLowerCase();
		if (input.equals("y") || input.equals("yes")){
			WorldSaver worldSaver = new WorldSaver(this.gameEngine);

			if (worldSaver.startUserSaveDialogue()){
				// Saved successfully
				this.exitGame();
			}
			else{
				// Save failed, ask user if they wish to try to save again (conveniently, the response to this question is handled the same as the quit question!)
				this.gameEngine.getStandardMessageGenerator().askIfWishToAttemptToSaveAgain();
			}
		}
		else if (input.equals("n") || input.equals("no")){
			// Exit application without saving
			this.exitGame();
		}
		else if (input.equals("c") || input.equals("cancel")){
			// Cancel the quit operation
			this.gameEngine.getStandardMessageGenerator().quitCancelled();
			// Unregister self as the input handler as not longer needed
			this.gameEngine.getInputHandler().popExecutor();
		}
		else{
			// Invalid input - request valid response
			this.gameEngine.getStandardMessageGenerator().askIfWishToSaveBeforeExitting();
		}
	}


	/*
		 * Exit the game application
		 */
	private void exitGame(){
                this.gameEngine.getBrowser().exit();
		System.exit(0);
	}
}

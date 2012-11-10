package controller.executor;

import model.gameEngine.ExecutorEvent;
import model.gameEngine.GameEngineInterface;

 
/**
 * Handles when the game ends due to a death the game being complete or a fail in loading.
 * 
 * @author Llamas /Yaks
 *
 */
public class EndOfGameExecutor extends Executor {
	
	/**
	 * Constructor 
	 * @param	gameEngine	Controls the game 
	 */
	public EndOfGameExecutor(GameEngineInterface gameEngine) {
		super(gameEngine);
	}
	
	
	
	/**
	 * Execute based on the input received
	 * @param	event	Input event to execute
	 */
	@Override
	public void handleInput(ExecutorEvent event) {
		this.onInput(event.getInput());
	}
	
	
	
	/**
	 * Handles input from the user
	 * @param	input	Input from the user
	 */
	public void onInput(String input) {
		input = input.toLowerCase();
		
		if(input.equals("default") || input.equals("d")) {
			// Unregister self as active input handler
			this.gameEngine.getInputHandler().popExecutor();
			// Restart game to Default World
			this.gameEngine.loadDefaultWorld();
			
		} else 	if(input.equals("saved") || input.equals("s")) {
			// Unregister self as active input handler
			this.gameEngine.getInputHandler().popExecutor();
			// Allow the User to load a Saved world
			this.gameEngine.load();
			
			
		}else if(input.equals("q") || input.equals("quit")) {
			// Exit application
			this.exitGame();
			
		} else {
			this.gameEngine.getStandardMessageGenerator().restartGameQuery();
		}
	}
	

	/*
	 * Exit the game application
	 */
	private void exitGame() {
                this.gameEngine.getBrowser().exit();
		System.exit(0);
	}
}
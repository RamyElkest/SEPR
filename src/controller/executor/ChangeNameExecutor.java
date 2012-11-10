package controller.executor;

import controller.parser.CommandType;
import model.gameEngine.GameEngineInterface;
import model.gameEngine.ExecutorEvent;

import model.world.Player;

 
/**
 * Handles the NAME command. The NameExecutor asks the user for their name
 * and changes the players name to the user input
 * 
 * @author Yaks
 */
public class ChangeNameExecutor extends Executor {
	


	/**
	 * Constructor 
	 * @param	gameEngine	Object controlling the game
	 */
	public ChangeNameExecutor(GameEngineInterface gameEngine) {
		super(gameEngine);
	}
	
	
	
	/**
	 * Called by InputHandler,
	 * Transforms an ExecutorEvent into a String for onInput for execution
	 * @param	event	Input event to execute
	 */
	@Override
	public void handleInput(ExecutorEvent event) {
		// Ensure the Name entered is not "quit", if it is simulate a quit command.
		if(event.getCommand().getType() == CommandType.QUIT){
			this.gameEngine.getStandardMessageGenerator().askIfWishToSaveBeforeExitting();
			this.gameEngine.getInputHandler().pushExecutor(new ExitExecutor(this.gameEngine));
			return;
		}
		this.onInput(event.getInput());
	}


		
	
	
	
	
	/**
	 * Handles input from the user, a call to this method is simulated on loading the default world 
	 * in the gameEngine, with an input parameter of "".
	 * @param	input	Input from the user
	 */
	public void onInput(String input) {
		
		//If the user has entered a string as an input
		if(input != null){
			
			// Split name into words. 
			String [] words = input.split("[ \t]+");
			
			// Don't accept a blank or whitespace name.
			if (words.length == 0) {
				gameEngine.getBrowser().println("Name cannot be blank.");
			} else {
				
				// Skip the first index if blank - ignore leading whitespace.
				int first = ((words[0].length() == 0) ? 1 : 0);
				
				// Name is also blank if words.length == 1 and first == 1.
				// words.length == first is a shortcut for this as we already know words.length != 0
				if (words.length == first)
				{
					gameEngine.getBrowser().println("Name cannot be blank.");
				}
				else
				{
				
					// Titlecase the name if all lowercase (otherwise assume it's properly capitalised), and normalise whitespaces to 1.
					String name;
					if (input.toLowerCase().equals(input)) {
						name = Character.toUpperCase(words[first].charAt(0)) + words[first].substring(1);
						for (int i = first + 1; i < words.length; i++) {
							name += " " + Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1).toLowerCase();
						}
					} else {
						name = words[first];
						for (int i = first + 1; i < words.length; i++) {
							name += " " + words[i];
						}
					}
					
					//Set the player's name to the input entered
					gameEngine.getWorld().getPlayer().setName(name);
					this.gameEngine.getInputHandler().popExecutor();
					
					//Get the Player
					Player player = gameEngine.getWorld().getPlayer();
					
					//Output to user that their name has been set
					gameEngine.getBrowser().println("Name set as: "+ player.getName());
					
					// Display player's name location and description
					gameEngine.getStandardMessageGenerator().showCurrentPlayerRoom(player.getCurrentRoom());
				}
			}
		}
	}
}


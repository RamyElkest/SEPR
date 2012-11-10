package model.objective;

import java.util.ArrayList;

import controller.parser.Command;

import model.objective.Objective;
import model.gameEngine.GameEngineInterface;


/**
 * Holds the (ordered) list of objectives to be completed to finish the game.
 * 
 * @author Llamas
 *
 */
public class ObjectiveHandler {
	/*
	 * Instance variables
	 */
	private ArrayList<Objective> objectives;					// Remaining world objectives
	
	
	
	/**
	 * Constructor
	 * @param	objectives	Objectives that within the game and are to be controlled
	 * @param	gameEngine	Object that controls the 
	 */
	public ObjectiveHandler(ArrayList<Objective> objectives) {
		super();
		this.objectives = objectives;
	}
	
	
	
	// Accessors
	/**
	 * Check if all objectives are complete
	 * @return	Whether all objectives have been completed
	 */
	public boolean isAllObjectivesComplete() {
		return this.objectives.size() == 0;
	}

	
	/**
	 * Get the number of objectives remaining
	 * @return	Number of remaining objectives
	 */
	public int getNumberOfRemainingObjectives() {
		return this.objectives.size();
	}
	
	
	/**
	 * Matches a command to an objective.
	 * If no match is found, returns null
	 * @param	command	Command to match to an objective
	 * @return	The objective the command relates to, else null
	 */
	public Objective matchCommandToObjective(Command command) {
		for(Objective objective : this.objectives) {
			if(objective.getCommand().equals(command)) {
				return objective;
			}
		}
		return null;
	}
	
	
	/**
	 * Get string representation of all objectives
	 * @return	String representation of objectives
	 */
	@Override
	public String toString() {
		if(this.objectives == null || this.objectives.size() == 0) {
			return "No remaining objectives.";
		}
		String str = "Remaining Objectives:";
		for(int i = 0; i < this.objectives.size(); i++) {
			str += "\n"+ (i+1) +") "+ this.objectives.get(i).getDescription();
		}
		return str;
	}
	

	// Mutators
	/**
	 * Remove an objective
	 * @param	objective	The objective to remove
	 */
	public void removeObjective(Objective objective) {
		this.objectives.remove(objective);
	}
	
	
	/**
	 * Check if the command has fulfilled an objective and react accordingly
	 * using the game engine
	 * @param	command	Command that was just executed within the game
	 * @param	gameEngine	Object that controls the game
	 */
	public void handleObjectivesCompletion(Command command, GameEngineInterface gameEngine) {
		ObjectiveHandler objectiveHandler = gameEngine.getObjectiveHandler();
		
		Objective matchedObjective = objectiveHandler.matchCommandToObjective(command);
		if(matchedObjective != null) {
			// Command has satisfied an objective - remove it from the active commands
			objectiveHandler.removeObjective(matchedObjective);
			
			// Display current objective's on-complete message
			gameEngine.getBrowser().println(
					matchedObjective.getOnCompleteMessage()
			);
						
			if(objectiveHandler.getNumberOfRemainingObjectives() == 0) {
				// All objectives have been completed
				gameEngine.onGameComplete();
			}
		}
	} 
}

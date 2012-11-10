package model.gameEngine.dummyclasses;

import controller.parser.Command;
import controller.parser.CommandType;

import view.StandardMessageGenerator;

import model.gameEngine.GameEngineInterface;
import model.gameEngine.InputHandler;
import model.gameEngine.UndoHandler;
import model.objective.ObjectiveHandler;
import model.world.World;
import view.BrowserTextHandler;


/**
 * DummyGameEngine is an implementation  to GameEngine
 * such that a dummy game engine can be easily set up and
 * manipulated during tests of the command executor
 * 
 * @author Llamas / Yaks
 */
public class DummyGameEngine implements GameEngineInterface {
	/*
	 * Instance variables
	 */
	private boolean deadPlayer;									// Whether the player within the world is dead
	private boolean gameCompleted;								// Whether the current game has been completed
	private Command commandGivenToRouter;						// Command given to the input router
	private World world;										// World in dummy game
        private BrowserTextHandler browserText;                                                             // get Browser (GUI not used)
	private CommandType responsibility;							// Command type that engine was made responsibility to process
	private StandardMessageGenerator standardMessageGenerator;	// Generates standard messages used within the game
	private ObjectiveHandler objectiveHandler;					// Objective handler
	private InputHandler inputHandler;							// Dummy input handler
	private UndoHandler undoHandler;
	
	
	
	/*
	 *  Dummy input handler 
	 */
	private class DummyInputHandler extends InputHandler {		
		
		public DummyInputHandler() {
			super(DummyGameEngine.this);
		}
		
		/*
		 * Override command routing
		 * (Required commands may trigger other commands to be executed so need to be checked)
		 */
		@Override
		public void routeInputToExecutor(String inputStr, Command command) {
			// Save command that is to be routed
			DummyGameEngine.this.commandGivenToRouter = command;
		}
	}
	
	
	
	/**
	 * Constructor
	 */
	public DummyGameEngine() {
		super();
		this.world = new World(null, null, null, null, null);
                this.browserText = new BrowserTextHandler();
		this.deadPlayer = false;
		this.standardMessageGenerator = new StandardMessageGenerator(this);
		this.objectiveHandler = new ObjectiveHandler(null);
		this.undoHandler = new UndoHandler(this);
		this.inputHandler = new DummyInputHandler();
	}
	
	
	
	// Accessors
         /**
	 * Get game UI text input
	 * @return	Object controlling for getting input from the user
	 */
	@Override
	public BrowserTextHandler getBrowser() {
		return this.browserText;
	}
	
	/**
	 * Get world
	 * @return	World in dummy game
	 */
	@Override
	public World getWorld() {
		return this.world;
	}	
	
	
	/**
	 * Set the world
	 * @param	world	World to use
	 */
	@Override
	public void setWorld(World world) {
		this.world = world;
	}
	
	
	/**
	 * Load the default world - JSON World not needed for testing
	 */
	@Override
	public void loadDefaultWorld() {
		//Not needed in tests
	}
	
	
	/**
	 * Returns a StandardMessageGenerator (which allows common output to be written automatically by
	 * calling the relevant method).
	 */
	@Override
	public StandardMessageGenerator getStandardMessageGenerator() {
		return this.standardMessageGenerator;
	}
	
	
	/**
	 * Get input handler
	 * @return	Handler for input
	 */
	@Override
	public InputHandler getInputHandler() {
		return this.inputHandler;
	}
	
	
	/**
	 * Get the current world's objective handler
	 * @return	Objective handler for the current world
	 */
	@Override
	public ObjectiveHandler getObjectiveHandler() {
		return this.objectiveHandler;
	}

	
	/**
	 * Test if the player is dead
	 * @return	Whether the player has died in the world
	 */
	public boolean isPlayerDead() {
		return this.deadPlayer;
	}
	
	
	/**
	 * Test if the game is complete
	 * @return	Whether the game is complete
	 */
	public boolean isGameComplete() {
		return this.gameCompleted;
	}
	
	
	/**
	 * Get command that another command created
	 * @return	Command created by executed command (if any)
	 */
	public Command getGeneratedCommand() {
		return commandGivenToRouter;
	}
	
	/**
	 * Get intercepted command type (if any)
	 */
	public CommandType getResponsibility() {
		return this.responsibility;
	}
		
	
	/**
	 * Functions game engine is responsible for
	 * (has to handle as required by GameEngineInterface)
	 */
	public void load() {
		this.responsibility = CommandType.LOAD;
	}
	
	public void save() {
		this.responsibility = CommandType.SAVE;
	}
	
	public void script() {
		this.responsibility = CommandType.SCRIPT;		
	}
	
	public void undo() {
		this.responsibility = CommandType.UNDO;
	}
	
	public void onPlayerDeath() {
		this.deadPlayer = true;
	}
	
	public void onGameComplete() {
		this.gameCompleted = true;
	}

	
	public void displayLogo() {	
	}


	public UndoHandler getUndoHandler() {
		return this.undoHandler;
	}
	

}

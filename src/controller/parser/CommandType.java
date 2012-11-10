package controller.parser;


/**
 * Type of command in the game, such as ATTACK, with associated data such as it's textual representation in commands and whether
 * it takes a WorldEntity as a parameter.
 * 
 * @author 	Llamas
 * @version	03/11/11
 */
public enum CommandType {
	/*
	 * Game command types
	 * 
	 * Form: ENUM_NAME("keyword_used_in_pattern_matching", is_a_world_entity_taken_as_a_parameter);
	 */
	MOVE("move", true), GOTO("goto", MOVE), GO("go", MOVE), WALK("walk", MOVE),
	INVENTORY("inventory", false), ITEMS("items", INVENTORY),
	LOOK("look", true), EXAMINE("examine", LOOK), ABOUT("about", LOOK), DESCRIBE("describe", LOOK),
	GET("get", true), TAKE("take", GET), PICKUP("pickup", GET),
	QUIT("quit", false), EXIT("exit", QUIT),
	LOAD("load", false),
	SAVE("save", false),
	DROP("drop", true), DISCARD("discard", DROP),
	ATTACK("attack", true), FIGHT("fight", ATTACK), STRIKE("strike", ATTACK), HIT("hit", ATTACK),
	TALK("talk", true), CHAT("chat", TALK), SPEAK("speak", TALK),
	UNDO("undo", false),
	RANDOM("random", false),
	SCRIPT("script", false),
	HELP("help", false),
	OBJECTIVES("objectives", false),
	USE("use", true),
	NAME("name", false),
	TRANSFORM("transform", true),
	INVALID_TYPE("invalid command", false);
	
	
	
	/*
	 * Instance variables
	 */
	private String keyword;							// Command type's keyword
	private boolean requiresEntityReference;		// Whether CommandType requires a reference to a game entity
	private CommandType synonymOf;                  // If the CommandType is a synonym, the base CommandType it is a synonym of.
	
	
	
	/*
	 * Constructor
	 * @param keyword	CommandType's textual representation (the command keyword)
	 * @param requiresObjReference	Whether the CommandType is required to reference a game entity
	 */
	private CommandType(String keyword, boolean requiresEntityReference) {
		this.keyword = keyword;
		this.requiresEntityReference = requiresEntityReference;
		this.synonymOf = this;  // If the CommandType is not a synonym of another base CommandType, it is set to be a synonym of itself. 
	}
	
	/*
	 * Constructor
	 * @param keyword	CommandType's textual representation (the command keyword)
	 * @param synonymOf  The base CommandType this is a synonym of.
	 */
	private CommandType(String keyword, CommandType synonymOf) {
		this.keyword = keyword;
		this.requiresEntityReference = synonymOf.requiresEntity();
		this.synonymOf = synonymOf;
	}
	
	
	
	// Accessors
	/**
	 * Get whether the CommandType requires a reference to a game entity
	 * @return	Whether the CommandType requires a reference to a game entity 
	 */
	public boolean requiresEntity() {
		return this.requiresEntityReference;
	}
	
	/**
	 * Get the base CommandType this is (or is a synonym of)
	 * @return	The base CommandType this is (or is a synonym of)
	 */
	public CommandType synonymOf() {
		return this.synonymOf;
	}
	
	
	/**
	 * String representation of the command type
	 * @return	The command type as a string 
	 */
	@Override
	public String toString() {
		return this.keyword;
	}
}

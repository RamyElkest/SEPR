package model.gameEngine;

import controller.parser.Command;


/**
 * Holds a Command and the String that was used to create it so that the String can be referenced.
 * 
 * @author Llamas
 */
public class ExecutorEvent {
	/*
	 * Instance variables
	 */
	private String input;
	private Command command;
	
	
	
	/**
	 * Contructor
	 * @param	input	Input string that triggered the event
	 * @param	command	Input command that was derived
	 */
	public ExecutorEvent(String input, Command command) {
		this.input = input;
		this.command = command;
	}
	
	
	
	// Accessors
	/**
	 * Get the string used
	 * @return	String used
	 */
	public String getInput() {
		return this.input;
	}
	
	
	/**
	 * Get the command used
	 * @return	Command used
	 */
	public Command getCommand() {
		return this.command;
	}
}

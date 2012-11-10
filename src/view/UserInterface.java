package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * Represents the whole user interface, including input and output.
 * 
 * @author Llamas
 *
 */
public class UserInterface implements UserInterfaceInterface {
	/*
	 * Settings
	 */
	private final String TITLE = "Unidentified Flying KLY!! (KoupreyLlamaYak)";					// Title of the application
	
	
	
	/*
	 *  Instance variables
	 */
	private BrowserTextHandler browserText;                                                                                 // Object that manages input and output to and from browser applet
	
	
	/**
	 * Constructor
	 */
	public UserInterface(BrowserTextHandler browserText) {
               this.browserText = browserText;
	}

	
        /**
	 * Called when an user input is given from TextInput
	 * @param	e	Action listener
	 */
	@Override
	public void printInput(String inputText) {
		
		String lineBreak = System.getProperty("line.separator");
		this.browserText.print(				// Pass input text to display
			"> " + inputText + lineBreak
		);
	}
	

	/**
	 * Get object responsible for user I/O
	 * @return	Object that outputs and gets to and from user
	 */
	@Override
	public BrowserTextHandler getBrowser() {
		return this.browserText;
	}     
}
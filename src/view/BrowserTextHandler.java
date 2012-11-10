/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.applet.Applet;
import java.util.ArrayList;
import model.gameEngine.GameEngine;
import netscape.javascript.*;
import model.gameEngine.InputHandler;
import model.world.*;

/**
 *
 * @author re588
 */
public class BrowserTextHandler extends Applet implements BrowserTextHandlerInterface{
    
    //The browser window
    private JSObject window;
    //String that is going to be displayed at flush
    private String toDisplay;
    //String that saves all the output (used for script)
    private String allDisplayed;
    //The current Browser Instance
    private BrowserTextHandler browserText;
    //Current instance of the game
    private GameEngine currentGame;
    //Flag to printStatus only after command has been inputted
    private boolean commandReceived;
    
    /**
     * Overiding the Applet start method
     */
    @Override
    public void start() {
        try {
            //get the browser window instance
            window = JSObject.getWindow(this);
            if(currentGame == null)
            {
                window.call("attemptGameStart", null);                  //Attempt to run the game is the game wasn't already running
            }
        }
        catch(JSException jse)
        {
            System.out.println("Cannot find window.");
            jse.printStackTrace();
        }
    }
    
    /**
     * Constructor calls start method
     */
    public BrowserTextHandler()
    {
        commandReceived = false;
        toDisplay = "";
    }
    
    /*
     * Starts the game
     */
    public void startGame()
    {
       currentGame = new GameEngine(this);
    }
    
    /**
     * outputs from java to javaScript in the browser
     * Adds a line break at the end of the input
     * @return 
     */
    @Override
    public void println(String output) {
        toDisplay += output;
        addLineBreak();
        flush();  
        this.allDisplayed += output;			// Log output
        if (commandReceived && currentGame != null && currentGame.getWorld() != null) printStatus(getPlayer().getCurrentRoom());
    }

    /**
     * outputs from java to javaScript in the browser
     * @return 
     */
    public void print(String output) {
        toDisplay += output;
    }
    
    /**
     * Print line No Flush
     * Print to buffer without flushing to browser
     */
    private void println_NF(String output) {
        toDisplay += output;
        addLineBreak();
    }
    
    /**
     * flushes the output to the browser
     */
    private void flush()
    {
        // send output to browser
        window.call("write", new Object[] {toDisplay});
        toDisplay = "";
    }
    
     /**
     * flushes the output to the browser
     */
    public void flushStatus()
    {
        // send output to browser
        window.call("writeStatus", new Object[] {toDisplay});
        toDisplay = "";
    }

    /**
     * Add line break to display
     */
    public void addLineBreak() {
        toDisplay += "\n";
    }

    /**
     * Get all text that has been displayed
     *
     * @return	All text that has been displayed
     */
    @Override
    public String getAllDisplayed() {
        return this.allDisplayed;
    }

    /**
     * Allow the user to submit input 
     */
    @Override
    public void enableInput() {
        // Enable input on browser
        window.call("enableInput", null);
    }
    
    /**
     * Prevent the user to submit input
     */
    @Override
    public void disableInput() {
        // Disable input on browser
       window.call("disableInput", null);
    }
    /**
     * exit the game by closing the tab/window
     */
    @Override
    public void exit() {
        // close the tab
       window.call("closeTab", null);
    }
    /*
     * Receive command from bowser and route to handler
     * @param   received    String
     */
    public void getFromBrowser(String received) {
        if (currentGame==null) 
        {
            throw new NullPointerException("currentGame is null in getFromBrowser.");
        }
        commandReceived = true;
        println(received);
        currentGame.getInputHandler().inputReceived(received);
    }
    
       /*
        * Print room and inventory status on the browser
        * NOTE: This function seperately prints to writeStatus in the browser and
        * does not affect default output
        */
        private void printStatus(WorldEntity worldEntity) {
            
            int entityID = 1;
            // Print description of entity that is being looked at
            println_NF(
                    worldEntity.getName() + ": " + worldEntity.getDescription());
            // Print adjoining rooms of the room the player is currently in
            ArrayList<Room> adjoiningRooms = ((Room) worldEntity).getAdjoiningRooms();
            if (adjoiningRooms.size() != 0) {
                println_NF(
                        "Rooms accessible from " + worldEntity.getName() + ":");
                for (int i = 0; i < adjoiningRooms.size(); i++) {
                    println_NF(
                            entityID + ") " + adjoiningRooms.get(i).getName());
                    entityID++;
                }
            }

            // Print characters within the room
            ArrayList<model.world.Character> characters = ((Room) worldEntity).getInventory().getCharacters();
            if (characters.size() != 0) {
                println_NF(
                        "\nCharacters within " + worldEntity.getName() + ":");
                for (int i = 0; i < characters.size(); i++) {
                    println_NF(
                            entityID + ") " + characters.get(i).getName());
                    entityID++;
                }
            }

            // Print items within the room
            ArrayList<Item> items = ((Room) worldEntity).getInventory().getItems();
            if (items.size() != 0) {
                println_NF(
                        "\nItems within " + worldEntity.getName() + ":");
                for (int i = 0; i < items.size(); i++) {
                    println_NF(
                            entityID + ") " + items.get(i).getName());
                    entityID++;
                };
            }
            
            addLineBreak();
            
            //Print Inventory
            CharacterInventory characterInventory = this.getPlayer().getInventory();

		if(characterInventory.getNumberOfItems() == 0) {
			println_NF("Inventory:\nNothing!");
		}
		else {
			println_NF(
					this.getPlayer().getInventory().toString()
					);
		}

		Weapon wieldedWeapon = this.getPlayer().getWieldedWeapon();
		if(wieldedWeapon != null) {
			println_NF(
					"Wielded weapon:\n- "+ wieldedWeapon.getName()
					);
		}
                
            addLineBreak();
            
            println_NF("Health: " + this.getPlayer().getCurrentHealth());
        flushStatus();
        
        }
    
	/*
	 * Get the player from the current game world
	 * @return	Current game world player
	 */
	private Player getPlayer() {
		return this.currentGame.getWorld().getPlayer();
	}
    public static void main(String [] args)
    {
        
    }
}


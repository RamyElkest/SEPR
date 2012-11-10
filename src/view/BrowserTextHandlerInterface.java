/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * BrowserTextHandlerInterface
 *
 * Provides an interface for BrowserTextHandler
 *
 * 
 * @author re588
 */

public interface BrowserTextHandlerInterface {
    
    /**
     * Overiding the Applet start method
     */
    public void start();
    
    /*
     * Starts the game
     */
    public void startGame();
    
    /**
     * outputs from java to javaScript in the browser
     * Adds a line break at the end of the input
     * @return 
     */
    public void println(String output);
    
    /**
     * outputs from java to javaScript in the browser
     * @return 
     */
    public void print(String output);
    
    /**
     * Add line break to display
     */
    public void addLineBreak();
    
    /**
     * Print line No Flush
     * Print to buffer without flushing to browser
     */
    public String getAllDisplayed();
    
     /*
     * Receive command from bowser and route to handler
     * @param   received    String
     */
    public void getFromBrowser(String received);
    
    /**
     * Allow the user to submit input 
     */
    public void enableInput();
    

    /**
     * Disallow the user to submit input
     */
    public void disableInput();

    /**
     * exit the game by closing the tab/window
     */
    public void exit();
}

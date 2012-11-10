/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.io;

import exceptions.InvalidFileLocation;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.jnlp.*;

/**
 *
 * @author Kouprey
 */
public class FileInput {
    /*
     * Instance variables
     */

    private FileContents FC;						// File Contents
    private FileOpenService FOS;                                            // File opening instance
    private FileSaveService FSS;                                            // File Saving instance

    /**
     * Constructor initialize FOS and FSS
     */
    public FileInput() {
        super();
        try {
            FC = null;
            FOS = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
            FSS = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
        } catch (UnavailableServiceException e) {
            FOS = null;
            FSS = null;
        }
    }

    /**
     * Get file to load as String
     *
     * @param loadLocation if null start file dialog else load the file from this abstract location
     * @return	Location of file to be opened
     * @throws InvalidSaveLocation	Error attaining valid location to open file
     * from
     */
    public String getFileAsString(String loadLocation) throws IOException, InvalidFileLocation {

        //No location has been passed, start file dialog
        if (loadLocation == null) {

            String data = "";

            if (FOS != null) {
                //Get a file with FileOpenService
                FC = FOS.openFileDialog(null, null);

                //Make sure FileContents has been assigned
                if (FC == null) {
                    throw new InvalidFileLocation();
                }

                // Get input stream from the file
                // Allocate enough space for the entire file
                byte[] dataBuffer = new byte[(int) FC.getLength()];

                // Read the file into an input stream
                BufferedInputStream fileInputBuffer = new BufferedInputStream(FC.getInputStream());
                fileInputBuffer.read(dataBuffer);

                data = new String(dataBuffer);
            } else {
                throw new NullPointerException("FOS Failed to initialise");
            }
            // Return the data as a String
            return data;
        } else {
            //Assert the location is valid
            if (this.getClass().getResourceAsStream(loadLocation) == null) {
                throw new InvalidFileLocation();
            }
            //get the line seperator for later use
            String newline = System.getProperty("line.separator");
            // Load the data
            Scanner sc = new Scanner(this.getClass().getResourceAsStream(loadLocation));
            String data = "";
            //Read all the file lines into worldAsJson
            while (sc.hasNextLine()) {
                data += sc.nextLine();
                data += newline;
            }

            return data;
        }
    }
}

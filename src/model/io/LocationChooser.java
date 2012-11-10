package model.io;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;

import exceptions.InvalidFileLocation;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.jnlp.*;


/**
 * Allows locations accessible by the user's OS to be selected 
 * by the user and retrieved as paths 
 * @author Llamas
 */
public class LocationChooser {
	/*
	 * Instance variables
	 */
	private FileContents FC;						// File Contents
        private FileOpenService FOS;                                            // File opening instance
        private FileSaveService FSS;                                            // File Saving instance
	
	
	
	/**
	 * Constructor
	 */
	public LocationChooser() {
		super();
		try
                {
                    FOS = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
                    FSS = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
                }
                catch (UnavailableServiceException e)
                {
                    FOS = null;
                    FSS = null;
                }
	}
	
        
	
	
	/**
	 * Get location of file to load
	 * @return	Location of file to be opened 
	 * @throws InvalidSaveLocation	Error attaining valid location to open file from
	 */
	public String getLoadFile() throws IOException{
            
                String data = "";
            
		if (FOS != null)
                {                       
                    //Get a file with FileOpenService
                    FC = FOS.openFileDialog(null, null);

                    // Get input stream from the file
                    // Allocate enough space for the entire file
                    byte[] dataBuffer = new byte[(int) FC.getLength()];

                    // Read the file into an input stream
                    BufferedInputStream fileInputBuffer = new BufferedInputStream(FC.getInputStream());
                    fileInputBuffer.read(dataBuffer);

                    data = new String(dataBuffer);          
                }
                // Return the data as a String
                return data;
	}
	
 	/**
	 * Get location of file to load
	 * @return	Location of file to be opened 
	 * @throws InvalidSaveLocation	Error attaining valid location to open file from
	 */
	public String getLoadFile(String loadLocation) throws IOException{
            
                // Get input stream from the file
                // Allocate enough space for the entire file
		byte[] dataBuffer = new byte[(int) new File(loadLocation).length()];
                
                // Read the file into an input stream
                BufferedInputStream fileInputBuffer = new BufferedInputStream(this.getClass().getResourceAsStream(loadLocation));
                fileInputBuffer.read(dataBuffer);

                // Return the data as a String
		return new String(dataBuffer);        
            }
        
	/**
	 * Get location of where to save file
	 * @return	Location where file is to be saved
	 * @throws InvalidSaveLocation 	Error attaining valid location to save file to
	 */	
	public String getSaveLocation() throws InvalidFileLocation {
		/*int returnVal = chooser.showSaveDialog(this.chooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return this.chooser.getSelectedFile().getAbsolutePath();
		}
		throw new InvalidFileLocation();
	*/return "";}
}

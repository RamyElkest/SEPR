/*
 * File Output Class
 * Use to start a file dialog and save files via jnlp
 */
package model.io;

import java.io.*;
import javax.jnlp.*;

/**
 *
 * @author Kouprey
 */
public class FileOutput {

        /*
        * Instance variables
        */
        private FileContents FC;                                                // File Contents
        private FileSaveService FSS;                                            // File Saving instance

        /**
        * Constructor initialize FOS and FSS
        */
        public FileOutput() {
            super();
            try {
                FC = null;
                FSS = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
            } catch (UnavailableServiceException e) {
                FSS = null;
            }
        }

        /**
        * Saves a String to the specified file for use by WorldStorageHandler
        *
        * @param fileName The file name
        * @param data	The string to be saved
        * @return True if file was saved
        */
        public boolean saveStringToFile(String fileName, String data) throws IOException {
            if (FSS != null)
            { 
                FC = FSS.saveFileDialog(null, new String[]{ "txt" }, new ByteArrayInputStream(data.getBytes()), "kouprey.txt");
                return FC != null;
            }
            return false;
        }
}

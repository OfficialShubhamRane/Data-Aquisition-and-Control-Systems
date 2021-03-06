import javafx.fxml.FXML;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/** This class is tend to generate logs of each session of system use with great details **/
public class SessionDocGenerator {

    /** Create file in suitable format to store meta-data and logs of machine */

    /** Name file with "System Time + MachineID" or any suitable key*/
    static File sessionFolderName;
    static String systemDateTime;
    public static void directoryGenerator(){

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        systemDateTime = DateFor.format(date);
        systemDateTime = systemDateTime.replaceAll("\\s", "");
        systemDateTime = systemDateTime.replaceAll(":", "");
        
        sessionFolderName = new File("src/SessionData/"+ systemDateTime);
        boolean isfileCreated = sessionFolderName.mkdir();
        if (isfileCreated) {
            System.out.println("System: Successfully created new directory for session data");
        }else{
            System.out.println("System: Un-successful in creating new directory for session data");
        }
    }

    /** Decide what things should be stored in file eg.-
     * Operator name
     * Machine ID
     * Battery start and end status
     * System Logs
     * Images and HeatChart if possible
     * Future scope:    Location (latitude-longitude)
     *                  Weather conditions
     *                  etc */

    /** Persist file somewhere on local machine */
    
}

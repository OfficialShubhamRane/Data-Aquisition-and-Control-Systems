import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/** This class is tend to generate logs of each session of system use with great details **/
public class SessionDocGenerator {

    /** Object for date format **/


    static LocalDate myObj = LocalDate.now(); // Create a date object

    /** Creating file and declaring writter for it**/
    static File sessionFile = new File( Constants.save_sessionLogsDirectory.concat( myObj.toString() ).concat(".txt") );
    static FileWriter writer;
    static {
        try {
            writer = new FileWriter( Constants.save_sessionLogsDirectory.concat( myObj.toString() ).concat(".txt") );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println( Constants.save_sessionLogsDirectory.concat( myObj.toString() ) );
        logWritter();
        writer.close();
    }

    /** Log writer append system logs with date and time **/
    public static void logWritter() throws IOException {
        if (sessionFile.createNewFile()) {
            System.out.println("Now i can write in file");
            writer.append(" add content to be appended here ");
        } else {
            System.out.println("File does not exist or already exists");
        }
    }


}

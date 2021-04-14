import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** This class is tend to generate logs of each session of system use with great details **/
public class SessionDocGenerator {

    /** Creating file and declaring writter for it**/
    static File sessionFile = new File(Constants.save_sessionLogsDirectory);
    static FileWriter writer;
    static {
        try {
            writer = new FileWriter(Constants.save_sessionLogsDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Object for date format **/
    static SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    static Date date = new Date();

    public static void main(String[] args) throws IOException {
        /** Called logger multiple times to check if it is appending successfully or not**/
        logWritter();
        logWritter();
        logWritter();
        writer.close();
    }

    /** Log writter append system logs with date and time **/
    public static void logWritter() throws IOException {
        if (sessionFile.exists()) {
            writer.append(simpleDateFormatter.format(date)).append(" \n");
        } else {
            System.out.println("File does not exist.");
        }
    }


}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Ipapi {

    public static void main(String[] args) throws IOException {

        /** Gets public IP */
        String public_IP;
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader( url_name.openStream() ) );
            public_IP = sc.readLine().trim();
        }
        catch (Exception e)
        {
            public_IP = "Cannot Execute Properly";
        }
        System.out.println("Public IP Address: " + public_IP +"\n");

        /** Gets location data from public IP */
        URL urlName = new URL("https://ipinfo.io/"+ public_IP +"/loc/");
        BufferedReader reader = new BufferedReader( new InputStreamReader( urlName.openStream() ) );

        String line;
        while ( reader.ready() ){
            line = reader.readLine().trim();
            System.out.println( line );
        }
        reader.close();

    }

}

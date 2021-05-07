import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/** This class is responsible for fetching latitude and logitude of machine using public IP of the machine */
public class LocalMapGenerator {

    /** Retrives public IP address of mahchine */
    public static String publicIP_Finder() {

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
        return public_IP;
    }

    /** Fetches Latitude of the machine */
    public static String latitudeGetter( String public_IP ) throws IOException {
        URL urlForLatitude = new URL("https://ipapi.co/"+ public_IP + "/latitude/");
        return jsonValueRetreiver(urlForLatitude);
    }

    /** Fetches Logitude of the machine */
    public static String longitudeGetter( String public_IP ) throws IOException {
        URL urlForLongitude = new URL("https://ipapi.co/"+ public_IP + "/longitude/");
        return jsonValueRetreiver(urlForLongitude);
    }

    /** Retrives the json value from the URL */
    static String jsonValueRetreiver(URL urlName) throws IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader( urlName.openStream() ) );

        String line = null;
        while ( reader.ready() ){
            line = reader.readLine().trim();
        }
        reader.close();
        return line;

    }




}

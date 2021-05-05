import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class LocalMapGenerator {

    public static void main(String[] args) throws IOException {
        locationGenerator( ip_Finder() );

    }

    private static void locationGenerator( String public_ip) throws IOException {
        URL urlForLatitude = new URL("https://ipapi.co/"+ public_ip + "/latitude/"); // 108.5.129.48 -----------  192.168.1.49
        URL urlForLongitude = new URL("https://ipapi.co/"+ public_ip + "/longitude/");

        System.out.println( "Latittude :" + jsonValueRetreiver(urlForLatitude) );
        System.out.println( "Logitude :" + jsonValueRetreiver(urlForLongitude) );
    }

    static String jsonValueRetreiver(URL ipapi) throws IOException {

        URLConnection c = ipapi.openConnection();
        c.setRequestProperty("User-Agent", "java-ipapi-client");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(c.getInputStream())
        );

        String line = reader.readLine();
        System.out.println("log: " + line);
        reader.close();

        return line;

    }

    /** Retrives public IP address of mahchine */
    private static String ip_Finder() throws UnknownHostException {

        // Find public IP address
        String system_IP = null;
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            system_IP = sc.readLine().trim();
        }
        catch (Exception e)
        {
            system_IP = "Cannot Execute Properly";
        }
        System.out.println("Public IP Address: " + system_IP +"\n");
        return system_IP;

    }


}

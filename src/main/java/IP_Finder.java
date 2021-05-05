import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class IP_Finder {

    public static void main(String[] args) throws Exception
    {

        // Find public IP address
        String system_IP = "";
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
    }

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Ipapi {


    public static void main(String[] args) throws IOException {
        URL ipapi = new URL("https://ipapi.co/108.5.129.48/json/"); // 108.5.129.48 -----------  192.168.1.49
        LocalMapGenerator.jsonValueRetreiver(ipapi);
    }

}

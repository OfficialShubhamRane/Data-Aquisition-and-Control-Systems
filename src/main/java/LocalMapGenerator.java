import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/** This class is responsible for fetching latitude and longitude of machine using public IP of the machine */
public class LocalMapGenerator {

    /** Retrieves public IP address of machine */
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
        return latlongJsonValueRetriever(urlForLatitude);
    }

    /** Fetches Longitude of the machine */
    public static String longitudeGetter( String public_IP ) throws IOException {
        URL urlForLongitude = new URL("https://ipapi.co/"+ public_IP + "/longitude/");
        return latlongJsonValueRetriever(urlForLongitude);
    }

    /** Retrieves the json values of Latitude and longitude from the URL */
    private static String latlongJsonValueRetriever(URL urlName) throws IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader( urlName.openStream() ) );

        String line = null;
        while ( reader.ready() ){
            line = reader.readLine().trim();
        }
        reader.close();
        return line;

    }

    /** Get Weather Data */
    public static String getWeatherData( String latitude, String longitude) throws IOException {
        URL urlForWeather = new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+
                "&unit=metric" +
                "&exclude=daily,minutely,hourly" +
                "&appid=" + Constants.weatherAPI_key);

        System.out.println("Weather URL: " + urlForWeather);

        String weatherJson = weatherJsonValueRetriever(urlForWeather);
        String mainWeather = dominantWeatherFinder( weatherJson );
        return mainWeather;
    }

    /** Retrieves the json values of weather from the URL */
    private static String weatherJsonValueRetriever(URL urlName) throws IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader( urlName.openStream() ) );

        String line = null;
        while ( reader.ready() ){
            line = reader.readLine().trim();
        }
        reader.close();
        return line;
    }

    /** Get main weather of that hour */
    private static String dominantWeatherFinder( String weatherJson ){

        int indexOfMain =  weatherJson.lastIndexOf("id");
        String weatherID = weatherJson.substring(indexOfMain+4, indexOfMain+7);

        System.out.println("Weather ID:" + weatherID);

        return Constants.getWeatherReport(weatherID);

    }



}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/** This class is responsible for fetching latitude and longitude of machine using public IP of the machine */
public class LocalMapGenerator {

    /** Retrieves public IP address of machine */
    public static void publicIP_Finder() {

        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader( url_name.openStream() ) );

            NavigationPanelController.public_IP = sc.readLine().trim();
        }
        catch (Exception e)
        {
            System.out.println("Location: Cannot fetch public IP of the system");
        }

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

        String line = null;

        try{
            BufferedReader reader = new BufferedReader( new InputStreamReader( urlName.openStream() ) );

            while ( reader.ready() ){
                line = reader.readLine().trim();
            }
            reader.close();

        }catch( Exception e){
//            System.out.println("Location: Error: 429, IPAPI not replying");
        }
        return line;
    }

    /** Get Weather Data */
    public static String getWeatherData( String latitude, String longitude) throws IOException {
        URL urlForWeather = new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+
                "&unit=metric" +
                "&exclude=daily,minutely,hourly" +
                "&appid=" + Constants.weatherAPI_key);

        String weatherJson = weatherJsonValueRetriever(urlForWeather);
        String mainWeather = dominantWeatherFinder( weatherJson );
        return mainWeather;
    }

    /** Retrieves the json values of weather from the URL */
    private static String weatherJsonValueRetriever(URL urlName) throws IOException {

        String line = null;
        try{
            BufferedReader reader = new BufferedReader( new InputStreamReader( urlName.openStream() ) );

            while ( reader.ready() ){
                line = reader.readLine().trim();
            }
            reader.close();
        }catch(Exception e){
            System.out.println("Location: Error 400, No weather data without latitude longitude");
        }


        return line;
    }

    /** Get main weather of that hour */
    private static String dominantWeatherFinder( String weatherJson ){

        String weatherID = "-1";
        try{
            int indexOfMain =  weatherJson.lastIndexOf("id");
             weatherID = weatherJson.substring(indexOfMain+4, indexOfMain+7);
        }catch (Exception e){
            System.out.println("Location: No weather data available");
        }

        return Constants.getWeatherReport(weatherID);

    }



}

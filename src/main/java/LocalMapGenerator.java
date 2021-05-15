import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> weatherMap = new HashMap<>();
        weatherMap.put("200","Thunderstorm with light rain");
        weatherMap.put("201","Thunderstorm with  rain");
        weatherMap.put("202","Thunderstorm with heavy rain");
        weatherMap.put("210","Light thunderstorm");
        weatherMap.put("211","Thunderstorm");
        weatherMap.put("212","Heavy thunderstorm");
        weatherMap.put("221","Ragged thunderstorm");
        weatherMap.put("230","Thunderstorm with light drizzle");
        weatherMap.put("231","Thunderstorm with drizzle");
        weatherMap.put("232","Thunderstorm with heavy drizzle");

        weatherMap.put("300","Light intensity drizzle");
        weatherMap.put("301","Drizzle");
        weatherMap.put("302","Heavy intensity drizzle");
        weatherMap.put("310","Light intensity drizzle rain ");
        weatherMap.put("311","drizzle rain ");
        weatherMap.put("312","heavy intensity drizzle rain ");
        weatherMap.put("313","shower rain and drizzle ");
        weatherMap.put("314","heavy shower rain and drizzle ");
        weatherMap.put("321","shower drizzle ");

        weatherMap.put("500","light rain ");
        weatherMap.put("501","moderate rain ");
        weatherMap.put("502","heavy intensity rain");
        weatherMap.put("503","very heavy rain");
        weatherMap.put("504","extreme rain");
        weatherMap.put("511","freezing rain");
        weatherMap.put("520","light intensity shower rain");
        weatherMap.put("521","shower rain");
        weatherMap.put("522","heavy intensity shower rain");
        weatherMap.put("531","ragged shower rain");

        weatherMap.put("600","light snow ");
        weatherMap.put("601","Snow");
        weatherMap.put("602","Heavy snow");
        weatherMap.put("611","Sleet");
        weatherMap.put("612","Light shower sleet");
        weatherMap.put("613","Shower sleet");
        weatherMap.put("615","Light rain and snow");
        weatherMap.put("616","Rain and snow");
        weatherMap.put("620","Light shower snow");
        weatherMap.put("621","Shower snow");
        weatherMap.put("622","Heavy shower snow");

        weatherMap.put("701","mist");
        weatherMap.put("711","Smoke");
        weatherMap.put("721","Haze");
        weatherMap.put("731","sand/ dust whirls");
        weatherMap.put("741","fog");
        weatherMap.put("751","sand");
        weatherMap.put("761","dust");
        weatherMap.put("762","volcanic ash");
        weatherMap.put("771","squalls");
        weatherMap.put("781","tornado");

        weatherMap.put("800","Clear Sky");

        weatherMap.put("801","few clouds: 11-25%");
        weatherMap.put("802","scattered clouds: 25-50%");
        weatherMap.put("803","broken clouds: 51-84%");
        weatherMap.put("804","overcast clouds: 85-100%");

        return weatherMap.get(weatherID);

    }


}

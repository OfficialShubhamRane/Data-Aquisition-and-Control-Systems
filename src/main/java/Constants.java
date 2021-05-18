import java.util.HashMap;
import java.util.Map;

public class Constants {

    final public static String URL = "jdbc:mysql://127.0.0.1:3306/java_projects_schema?autoreconnect=true";
    final public static String USERNAME = "root";
    final public static String PASSWORD = "1234";

    final public static String frontalFaceCascadeDirectory = "src/main/resources/haarcascade_frontalface_alt.xml";
    public static String save_heatMapDirectory = "src/SessionData/navigation-heatChart.png";
    public static String save_capturedPhotoDirectory = "src/SessionData/faceDetected_img";

    public static String heatMapImage = "src/SessionData/navigation-heatChart.png";
//    public static String googleMapsImage = "src/main/resources/GoogleMaps_WashingtonPark.png";
    public static String googleMapsImage = "src/main/resources/GoogleMaps.png";
    public static String blendedHeatMap = "src/SessionData/blendedNavHeatChart.png";

    final public static String weatherAPI_key = "4a35f0f50ffdfc2aaa5966bbcec3f638";
    final public static String googleMapAPI_key = "AIzaSyDwFYHpPzpWqrWIZa5R-KL3QYlQQTmSKqs";
    final public static String congressStreetHardCoded_Link = "https://maps.googleapis.com/maps/api/staticmap?center=40.75028544,-74.04422841&zoom=19&size=883x904&maptype=satellite&key=";
    // My perfect co-ordinates are: 40.75028544,-74.04422841


    /** Holds weather ID  and weather Description mapping */
    final public static String getWeatherReport(String weatherID){

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
        weatherMap.put("311","Drizzle rain ");
        weatherMap.put("312","Heavy intensity drizzle rain ");
        weatherMap.put("313","Shower rain and drizzle ");
        weatherMap.put("314","Heavy shower rain and drizzle ");
        weatherMap.put("321","Shower drizzle ");

        weatherMap.put("500","Light rain ");
        weatherMap.put("501","Moderate rain ");
        weatherMap.put("502","Heavy intensity rain");
        weatherMap.put("503","Very heavy rain");
        weatherMap.put("504","Extreme rain");
        weatherMap.put("511","Freezing rain");
        weatherMap.put("520","Light intensity shower rain");
        weatherMap.put("521","Shower rain");
        weatherMap.put("522","Heavy intensity shower rain");
        weatherMap.put("531","Ragged shower rain");

        weatherMap.put("600","Light snow ");
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

        weatherMap.put("701","Mist");
        weatherMap.put("711","Smoke");
        weatherMap.put("721","Haze");
        weatherMap.put("731","Sand/ dust whirls");
        weatherMap.put("741","Fog");
        weatherMap.put("751","Sand");
        weatherMap.put("761","Dust");
        weatherMap.put("762","Volcanic ash");
        weatherMap.put("771","Squalls");
        weatherMap.put("781","Tornado");

        weatherMap.put("800","Clear Sky");

        weatherMap.put("801","Few clouds: 11-25%");
        weatherMap.put("802","Scattered clouds: 25-50%");
        weatherMap.put("803","Broken clouds: 51-84%");
        weatherMap.put("804","Overcast clouds: 85-100%");

        return weatherMap.get(weatherID);
    }
}

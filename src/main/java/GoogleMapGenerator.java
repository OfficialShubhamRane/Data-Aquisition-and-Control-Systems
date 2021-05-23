import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/** Generates Google map with suitable size to be blended with heatmap*/
public class GoogleMapGenerator {

    /** Gets google map for current co-ordinates in 640x640 */
    public static void googleMapImageGetter() throws IOException{

        GoogleMapGenerator googleMapGeneratorObj = new GoogleMapGenerator();
        URL urlForMap;

        try{

            String latitude = LocalMapGenerator.latitudeGetter( LocalMapGenerator.publicIP_Finder() );
            String longitude = LocalMapGenerator.longitudeGetter( LocalMapGenerator.publicIP_Finder() );

            urlForMap  = new URL("https://maps.googleapis.com/maps/api/staticmap?center="+ latitude +","+ longitude +"&zoom=19&size=883x904&maptype=satellite&key="
                    + Constants.googleMapAPI_key);

            /** For exact Congress Street co-ordinates*/
//            urlForMap  = new URL(Constants.congressStreetHardCoded_Link + Constants.googleMapAPI_key);

        }catch ( IOException ie ){
            /** URL with hard coded lat and long for the moments when lat & long API is not responding*/
            urlForMap  = new URL(Constants.congressStreetHardCoded_Link + Constants.googleMapAPI_key);
            System.out.println(" Latitude-Longitude API not responding");
        }

        InputStream is = urlForMap.openStream();
        OutputStream os = new FileOutputStream(Constants.googleMapsImage);

        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();

        /** To convert 640x640 to 883x904 */
        googleMapGeneratorObj.imageResizer();

    }

    /** Resizes Map Image from 640x640 to 883x904 */
    public void imageResizer() throws IOException {

        /** Because 21x21 heatmap is 883x904 */
        final int targetWidth = 883;
        final int targetHeight = 904;

        File inputFile = new File(Constants.googleMapsImage);
        BufferedImage originalImage = ImageIO.read(inputFile);

        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        File outputFile = new File(Constants.googleMapsImage);
        ImageIO.write(outputImage, "png", outputFile);

    }


}

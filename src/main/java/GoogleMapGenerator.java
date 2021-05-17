import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/** Generates Google map with suitable size to be blended with heatmap*/
public class GoogleMapGenerator {

    /** Gets google map for current co-ordinates in 640x640 */
    public static void googleMapImageGetter() throws IOException{

//        String latitude = LocalMapGenerator.latitudeGetter( LocalMapGenerator.publicIP_Finder() );
//        String longitude = LocalMapGenerator.longitudeGetter( LocalMapGenerator.publicIP_Finder() );

//        URL urlForMap  = new URL("https://maps.googleapis.com/maps/api/staticmap?center="+ latitude +","+longitude+"&zoom=19&size=883x904&maptype=satellite&key=" + Constants.googleMapAPI_key);
        URL urlForMap  = new URL("https://maps.googleapis.com/maps/api/staticmap?center=40.75028544,-74.04422841&zoom=19&size=883x904&maptype=satellite&key=" + Constants.googleMapAPI_key);
        GoogleMapGenerator obj = new GoogleMapGenerator();

        InputStream is = urlForMap.openStream();
        OutputStream os = new FileOutputStream(Constants.TEST_googleMapsImage);

        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();

        obj.imageResizer();

    }

    /** Resizes Map Image from 640x640 to 883x904 */
    public void imageResizer() throws IOException {

        final int targetWidth = 883;
        final int targetHeight = 904;

        File folderInput = new File(Constants.TEST_googleMapsImage);
        BufferedImage originalImage = ImageIO.read(folderInput);

        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        File outputfile = new File(Constants.TEST_googleMapsImage);
        ImageIO.write(outputImage, "png", outputfile);

    }
}

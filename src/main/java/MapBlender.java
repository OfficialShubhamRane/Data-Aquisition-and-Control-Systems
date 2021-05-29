import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;

public class MapBlender {

    /** Combines heatmap with Local map */
    public void combineHeatmapWithGoogleMap() throws IOException {
        double alpha = 0.3; double beta = 0.5;

        /** Three matrixes which will be used */
        Mat src1, src2, dst = new Mat();

        /** 2 source images which are to be added, NEED TO BE OF SAME PIXEL SIZES */
        src1 = Imgcodecs.imread(Constants.heatMapImage);        // value of alpha

        GoogleMapGenerator.googleMapImageGetter();
        src2 = Imgcodecs.imread(Constants.googleMapsImage);     // value of beta

        /** This method generates result image in dst matrix */
        Core.addWeighted( src1, alpha, src2, beta, 0.0, dst);
        Imgcodecs.imwrite(Constants.blendedHeatMap,dst);
    }

}

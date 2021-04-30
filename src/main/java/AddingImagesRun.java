import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Locale;
import java.util.Scanner;

public class AddingImagesRun {

    public void run() {
        double alpha = 0.3; double beta = 0.5;

        /** Three matrixes which will be used */
        Mat src1, src2, dst = new Mat();

        /** 2 source images which are to be added, NEED TO BE OF SAME SIZES */
        src1 = Imgcodecs.imread("src/SessionData/navigation-heatChart.png");       // value of alpha
        src2 = Imgcodecs.imread("src/SessionData/GoogleMaps.png");      // value of Beta


        /** This method generates result image in dsk matrix */
        Core.addWeighted( src1, alpha, src2, beta, 0.0, dst);

        Imgcodecs.imwrite("src/SessionData/blendedNavHeatChart.png",dst);

//        HighGui.imshow("Linear Blend", dst);
//        HighGui.waitKey(0);
//        System.exit(0);
    }

}

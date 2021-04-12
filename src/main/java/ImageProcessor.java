import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImageProcessor {

    /** Simple reading and writting image from specified path - Testing*/
    public Mat readImg(){
        Imgcodecs.imread("src/main/resources/img1.jpg");
        System.out.println("Read successful");
        return Imgcodecs.imread("src/main/resources/img1.jpg");
    }
    public void writeImg(){

        Imgcodecs.imwrite("src/main/resources/img2.jpg", readImg());
        System.out.println("Written Successfully");
    }

    /** Detecting faces from specifies image
     * @return*/
    public static Mat detectFaceFromImages(Mat capturedMat, boolean isCaptureClicked){

        MatOfRect facesDetected = new MatOfRect();

        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        int minFaceSize = Math.round(capturedMat.rows() * 0.1f);
        cascadeClassifier.load("src/main/resources/haarcascade_frontalface_alt.xml");
        cascadeClassifier.detectMultiScale(capturedMat,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );

        Rect[] facesArray = facesDetected.toArray();
        for(Rect face : facesArray) {
            Imgproc.rectangle(capturedMat, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
        }
        if(isCaptureClicked){
            writeFaceDetectedImg(capturedMat, "src/CaptureImages/faceDetected_img");
        }
        return capturedMat;
    }

    /** Writing face detected image on specified path */
    static int pictureId = 1;
    public static void writeFaceDetectedImg(Mat srcImage, String targetImagePath) {
        Imgcodecs.imwrite(targetImagePath + pictureId +".jpg", srcImage);

        pictureId++;
    }

    /** Converts matrix to Img */
    public static  Image mat2Img(Mat mat) {
        MatOfByte bytes = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, bytes);
        InputStream inputStream = new ByteArrayInputStream(bytes.toArray());
        return new Image(inputStream);
    }

    /** Called from NavigationController to start video streaming*/
    public static Image getCapture() {

        Mat mat = new Mat();
        NavigationPanelController.capture.read(mat);
        Mat haarClassifiedImg = detectFaceFromImages(mat,false);
        return mat2Img(haarClassifiedImg);
    }

    /** Temporarily stops video capture to click photo */
    public static void stopCapture(){
        NavigationPanelController.capture.release();
    }


}

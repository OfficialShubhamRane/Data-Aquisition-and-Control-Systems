
/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

import com.github.sarxos.webcam.Webcam;
import com.sun.jdi.InvocationException;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.UnknownHostException;


public class NavigationPanelController {

    public TextArea systemLogTA_ID;
    public TextField opName_ID;
    public Button captureImageBtn_ID;
    public TextField batteryLife_ID;
    public TextField distanceCovered_ID;
    public TextField latitude_ID;
    public TextField longitude_ID;
    public ImageView imageView_ID;



    public void initialize() throws IOException {

        /** Sets operator name fetching from user_ID field from login */
        opName_ID.setText(LoginController.operatorName);

        /** Battery of laptop showing here can be replaced with vehicles battery */
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        batteryLife_ID.setText(batteryStatus.toString());

        /** Turn on Video Cam*/
        turnOnVideoCam();

        /** Sets Latitude and Longitude */
        latitude_ID.setText( LocalMapGenerator.latitudeGetter( LocalMapGenerator.publicIP_Finder() ) );
        longitude_ID.setText( LocalMapGenerator.longitudeGetter( LocalMapGenerator.publicIP_Finder() ) );

    }

    /**
     * Calculating for How long key was pressed
     * */
    KeyCode currKey;
    KeyCode lastKey = null;
    long keyPressedMillis = 0;
    long keyPressLength = 0;

    /** Time when key was pressed */
    public void arrowKeyStrokesHandler(KeyEvent keyEvent) {
        currKey = keyEvent.getCode();
        if(currKey != lastKey){
            lastKey = currKey;
            if(currKey == KeyCode.W){ //UP
                keyPressedMillis = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.D){ //RIGHT
                keyPressedMillis = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.A){ //LEFT
                keyPressedMillis = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.S){ //DOWN/BACK
                keyPressedMillis = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.SPACE){ // Stop/Space
                keyPressedMillis = System.currentTimeMillis();
            }
        }
    }

    /** System time - time when key was released */
    StringBuilder backTrackingLog = new StringBuilder();
    public void arrowKeyReleaseHandler(KeyEvent keyEvent) throws IOException {

        /** Values initializers for heatmap */
        String direction = null;
        long distance = 0;

        /** Ches which key was released to map its released system time*/
        KeyCode releasedKey = keyEvent.getCode();
        if (currKey == releasedKey) {
            keyPressLength = System.currentTimeMillis() - keyPressedMillis;
            keyPressedMillis = 0;
            lastKey = null;
        }

        /** Controls and SystemLogging on TextArea */
        if (currKey == KeyCode.W) {

            if(keyPressLength / 1000 > 0){
                systemLogTA_ID.appendText("Forward : " + keyPressLength/1000 + " sec ");
                systemLogTA_ID.appendText(keyPressLength % 1000 + " millisec");
                timeTravelled += keyPressLength;

                backTrackingLog.append("Reverse : ").append(keyPressLength / 1000).append(" sec ");
                backTrackingLog.append(keyPressLength % 1000).append(" millisec");

                direction = "Forward";
                distance = keyPressLength/1000;

            }else{
                systemLogTA_ID.appendText("Forward : " + keyPressLength%1000 + " millisec");
                backTrackingLog.append("Reverse : ").append(keyPressLength % 1000).append(" millisec");
            }

        }
        else if (currKey == KeyCode.A){
            if(keyPressLength/1000 > 0){
                systemLogTA_ID.appendText("Left        : " + keyPressLength/1000 + " sec ");
                systemLogTA_ID.appendText(keyPressLength%1000 + " millisec");
                timeTravelled += keyPressLength;

                backTrackingLog.append("Right      : ").append(keyPressLength / 1000).append(" sec ");
                backTrackingLog.append(keyPressLength % 1000).append(" millisec");

                direction = "Left";
                distance = keyPressLength/1000;

            }else{
                systemLogTA_ID.appendText("Left        : " + keyPressLength%1000 + " millisec");
                backTrackingLog.append("Right      : ").append(keyPressLength % 1000).append(" millisec");
            }

        }
        else if(currKey == KeyCode.S){
            if(keyPressLength/1000 > 0){
                systemLogTA_ID.appendText("Reverse : " + keyPressLength/1000 + " sec ");
                systemLogTA_ID.appendText(keyPressLength%1000 + " millisec");
                timeTravelled += keyPressLength;

                backTrackingLog.append("Forward : ").append(keyPressLength / 1000).append(" sec ");
                backTrackingLog.append(keyPressLength % 1000).append(" millisec");


                direction = "Reverse";
                distance = keyPressLength/1000;

            }else{
                systemLogTA_ID.appendText("Reverse : " + keyPressLength%1000 + " millisec");
                backTrackingLog.append("Forward : ").append(keyPressLength % 1000).append(" millisec");
            }

        }
        else if(currKey == KeyCode.D){
            if(keyPressLength/1000 > 0){
                systemLogTA_ID.appendText("Right     : " + keyPressLength/1000 + " sec ");
                systemLogTA_ID.appendText(keyPressLength%1000 + " millisec");
                timeTravelled += keyPressLength;

                backTrackingLog.append("Left       : ").append(keyPressLength / 1000).append(" sec ");
                backTrackingLog.append(keyPressLength % 1000).append(" millisec");

                direction = "Right";
                distance = keyPressLength/1000;

            }else{
                systemLogTA_ID.appendText("Right     : " + keyPressLength%1000 + " millisec");
                backTrackingLog.append("Left       : ").append(keyPressLength % 1000).append(" millisec");
            }

        }
        else if(currKey == KeyCode.SPACE){
            systemLogTA_ID.appendText("Brake");
            systemLogTA_ID.appendText("\n");

            backTrackingLog.append("Brake");
            backTrackingLog.append("\n");
        }

        /** Resets and pushes each log on next line in systemLogs textfield*/
        systemLogTA_ID.appendText("\n");
        backTrackingLog.append("\n");
        keyPressLength = 0;

        /** Call method to calculate total distance covered w.r.t. seconds key was pressed*/
        totalDistanceTravelled();

        /** Sending data to heatMapGenerator class after each key release*/
        assert direction != null;
        HeatMapGenerator.heatChartGenerator(direction, distance);
        HeatMapGenerator.heatMapGeneration();

        /** Responsible for creating blended heatChart with maps*/
        new ImageBlender().run();

        /** Periodically check for battery status after each key release*/
        batteryStatusChecker();

    }

    /** Total distance travelled */
    float timeTravelled = 0;
    float coveredDistance = 0;
    double vehicleSpeed = 5.0; // 50 miles per hour
    public void totalDistanceTravelled(){
        System.out.println(timeTravelled);
        coveredDistance = (float) ((vehicleSpeed / 360.0) * timeTravelled);
        distanceCovered_ID.setText(String.valueOf(coveredDistance));
        System.out.println( String.valueOf(coveredDistance ));
    }

    /** Backtrack logs */
    public void backtrackBtnClicked() {
        systemLogTA_ID.setText(String.valueOf(backTrackingLog));
    }

    /** Battery of laptop/vehicle displayed here */
    public void batteryStatusChecker(){
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        batteryLife_ID.setText(batteryStatus.toString());
    }

    /** Capture Images from default camera and sent to ImageProcessor */
    boolean isCaptureClicked = false;
    public void captureImageBtnClicked() {

        isCaptureClicked = true;
        ImageProcessor.stopCapture();

        Webcam webCamObj = Webcam.getDefault();
        webCamObj.open();

        BufferedImage capturedImage = webCamObj.getImage();

        byte[] pixels = ((DataBufferByte) capturedImage.getRaster().getDataBuffer()).getData();
        Mat capturedMat = new Mat(capturedImage.getHeight(), capturedImage.getWidth(), CvType.CV_8UC3);
        capturedMat.put(0, 0, pixels);
        ImageProcessor.detectFaceFromImages(capturedMat, isCaptureClicked);
        isCaptureClicked = false;
        webCamObj.close();
        turnOnVideoCam();
    }

    /** Capture video from video cam **/
    static VideoCapture capture;
    public void turnOnVideoCam() {
        capture = new VideoCapture(0);

        new AnimationTimer() {
            @Override public void handle(long l) {
                /** Calling Image processor class for face detection */
                imageView_ID.setImage(ImageProcessor.getCapture());
            }
        }.start();

    }
}

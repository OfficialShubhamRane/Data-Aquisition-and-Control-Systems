
/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

import com.github.sarxos.webcam.Webcam;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;


public class NavigationPanelController {

    public TextArea systemLogTA_ID;
    public TextField opName_ID;
    public Button captureImageBtn_ID;
    public TextField batteryLife_ID;
    public TextField distanceCovered_ID;
    public TextField latitude_ID;
    public TextField longitude_ID;
    public ImageView imageView_ID;
    public TextField weatherRtf_ID;


    public void initialize() throws IOException {

        /** Sets operator name fetching from user_ID field from login */
        opName_ID.setText(LoginController.operatorName);

        /** Battery of laptop showing here can be replaced with vehicles battery */
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        batteryLife_ID.setText(batteryStatus.toString());

        /** Turn on Video Cam */
        try{

            turnOnVideoCam();

        }catch (Exception e){
            System.out.println("Camera Failed to Start");
            e.printStackTrace();
        }

        /** Sets Latitude and Longitude */
        try{

            String latitude = LocalMapGenerator.latitudeGetter( LocalMapGenerator.publicIP_Finder() );
            String longitude = LocalMapGenerator.longitudeGetter( LocalMapGenerator.publicIP_Finder() );

            latitude_ID.setText( latitude );
            longitude_ID.setText( longitude );

        }catch (Exception e){
            System.out.println("Couldn't get Lat and Longitude data");
            e.printStackTrace();
        }

        /** Get weather data */
        try{

            /** Gets Latitude and Longitude */
            String latitude = LocalMapGenerator.latitudeGetter( LocalMapGenerator.publicIP_Finder() );
            String longitude = LocalMapGenerator.longitudeGetter( LocalMapGenerator.publicIP_Finder() );

            /** Call Weather api for weather data */
            String currentWeather = LocalMapGenerator.getWeatherData(latitude, longitude);
            weatherRtf_ID.setText( currentWeather );

        }catch(Exception e){
            System.out.println("Couldn't get weather data");
            e.printStackTrace();
        }

    }

    /**
     * Calculating for How long key was pressed
     * */
    KeyCode currKey;
    KeyCode lastKey = null;
    long keyPressedSystemTime = 0;
    long keyHeldDuration = 0;

    /** Time when key was pressed */
    public void arrowKeyStrokesHandler(KeyEvent keyEvent) {
        currKey = keyEvent.getCode();
        if(currKey != lastKey){
            lastKey = currKey;
            if(currKey == KeyCode.W){ //UP
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.D){ //RIGHT
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.A){ //LEFT
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.S){ //DOWN/BACK
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.SPACE){ // Stop/Space
                keyPressedSystemTime = System.currentTimeMillis();
            }
        }
    }

    /** System time - time when key was released */
    StringBuilder backTrackingLog = new StringBuilder();
    public void arrowKeyReleaseHandler(KeyEvent keyEvent) throws IOException {

        /** Values initializers for heatmap */
        String direction = null;
        long distance = 0;

        /** Checks which key was released to map its released system time*/
        KeyCode releasedKey = keyEvent.getCode();
        if (currKey == releasedKey) {
            keyHeldDuration = System.currentTimeMillis() - keyPressedSystemTime;
            keyPressedSystemTime = 0;
            lastKey = null;
        }

        /** Controls and SystemLogging on TextArea */
        if (currKey == KeyCode.W) {

            if(keyHeldDuration / 1000 > 0){
                systemLogTA_ID.appendText("Forward : " + keyHeldDuration /1000 + " sec ");
                systemLogTA_ID.appendText(keyHeldDuration % 1000 + " millisec");
                timeTravelled += keyHeldDuration;

                backTrackingLog.append("Reverse : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");

                direction = "Forward";
                distance = keyHeldDuration /1000;

            }else{
                systemLogTA_ID.appendText("Forward : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Reverse : ").append(keyHeldDuration % 1000).append(" millisec");
            }

        }
        else if (currKey == KeyCode.A){
            if(keyHeldDuration /1000 > 0){
                systemLogTA_ID.appendText("Left        : " + keyHeldDuration /1000 + " sec ");
                systemLogTA_ID.appendText(keyHeldDuration %1000 + " millisec");
                timeTravelled += keyHeldDuration;

                backTrackingLog.append("Right      : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");

                direction = "Left";
                distance = keyHeldDuration /1000;

            }else{
                systemLogTA_ID.appendText("Left        : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Right      : ").append(keyHeldDuration % 1000).append(" millisec");
            }

        }
        else if(currKey == KeyCode.S){
            if(keyHeldDuration /1000 > 0){
                systemLogTA_ID.appendText("Reverse : " + keyHeldDuration /1000 + " sec ");
                systemLogTA_ID.appendText(keyHeldDuration %1000 + " millisec");
                timeTravelled += keyHeldDuration;

                backTrackingLog.append("Forward : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");


                direction = "Reverse";
                distance = keyHeldDuration /1000;

            }else{
                systemLogTA_ID.appendText("Reverse : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Forward : ").append(keyHeldDuration % 1000).append(" millisec");
            }

        }
        else if(currKey == KeyCode.D){
            if(keyHeldDuration /1000 > 0){
                systemLogTA_ID.appendText("Right     : " + keyHeldDuration /1000 + " sec ");
                systemLogTA_ID.appendText(keyHeldDuration %1000 + " millisec");
                timeTravelled += keyHeldDuration;

                backTrackingLog.append("Left       : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");

                direction = "Right";
                distance = keyHeldDuration /1000;

            }else{
                systemLogTA_ID.appendText("Right     : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Left       : ").append(keyHeldDuration % 1000).append(" millisec");
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
        keyHeldDuration = 0;

        /** Call method to calculate total distance covered w.r.t. seconds key was pressed*/
        totalDistanceTravelled();

        /** Sending data to heatMapGenerator class after each key release*/
        assert direction != null;
        HeatMapGenerator.heatChartGenerator(direction, distance);
        HeatMapGenerator.heatMapGeneration();

        /** Responsible for creating blended heatChart with maps*/
        new mapBlender().combineHeatmapWithGoogleMap();

        /** Periodically check for battery status after each key release*/
        batteryStatusChecker();

    }

    /** Total distance travelled */
    float timeTravelled = 0;
    float coveredDistance = 0;
    double vehicleSpeed = 5.0; // 50 miles per hour
    public void totalDistanceTravelled(){
        System.out.println(timeTravelled);

        coveredDistance = (float) ( (vehicleSpeed / 360.0) * timeTravelled);
        distanceCovered_ID.setText(String.valueOf(coveredDistance));
        System.out.println( coveredDistance );
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

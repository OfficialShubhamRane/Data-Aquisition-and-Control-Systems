/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

import com.github.sarxos.webcam.Webcam;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
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
import java.io.File;
import java.io.IOException;


public class NavigationPanelController extends Thread {

    public TextArea systemLogTA_ID;
    public TextField opName_ID;
    public Button captureImageBtn_ID;
    public TextField batteryLife_ID;
    public TextField distanceCovered_ID;
    public ImageView imageView_ID;
    public TextField weatherRtf_ID;
    public Button backTrack_btn;
    public static String public_IP;

    /**
     * Initialize Value
     * */
    public void initialize() {

        System.out.println( "Setting up Controls and Data Acquisition Window...");

        /** Sets operator name fetching from user_ID field from login */
        opName_ID.setText( LoginController.operatorName );

        /** Creating dedicated file for current session */
        SessionDocGenerator.directoryGenerator();


        /** Turn on Video Cam */
        try{
            turnOnVideoCam();
            System.out.println("Camera: Successfully Started Camera");

        }catch (Exception e){
            System.out.println("Camera: Failed to Start Camera");
            e.printStackTrace();
        }

        /** fetch and set public IP of the system */
        LocalMapGenerator.publicIP_Finder();
//        System.out.println( "System: Public IP: " + public_IP );


        /** Calling thread to periodically check on weather in every 1 minutes */
        start();


    }

    @Override
    public void run(){

        while( true ){

            /** Get System data */
            try{

                System.out.println("System: Checking battery Percentage");

                /** Battery of laptop showing here can be replaced with vehicles battery */
                Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
                Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
                batteryLife_ID.setText(batteryStatus.toString());

                System.out.println("Location: Attempting to get weather data");

<<<<<<< HEAD
                    /** Call Weather api for weather data */
                    String currentWeather = LocalMapGenerator.getWeatherData(latitude, longitude);
                    weatherRtf_ID.setText( currentWeather );
=======
                /** Gets Latitude and Longitude */
                String latitude = LocalMapGenerator.latitudeGetter( NavigationPanelController.public_IP );
                String longitude = LocalMapGenerator.longitudeGetter( NavigationPanelController.public_IP );
//                System.out.println("Latitude: " + latitude + "Longitude: " + longitude);
>>>>>>> dev_1

                /** Call Weather api for weather data */
                String currentWeather = LocalMapGenerator.getWeatherData(latitude, longitude);
                System.out.println( "Current Weather: " + currentWeather );
                weatherRtf_ID.setText( currentWeather );
                System.out.println("Location: Completed fetching weather data");

                sleep(60000);

            }catch(Exception e){
                weatherRtf_ID.setText("N/A");
                System.out.println("Location: Couldn't fetch weather data");
                e.printStackTrace();
            }
        }
    }

    /**
     * Calculating for How long key was pressed
     * */
    private KeyCode currKey;
    private KeyCode lastKey = null;
    private long keyPressedSystemTime = 0;
    private long keyHeldDuration = 0;

    /**
     * System time when key was pressed
     * */
    public void arrowKeyStrokesHandler(KeyEvent keyEvent) throws IOException {
        currKey = keyEvent.getCode();
        if(currKey != lastKey){
            lastKey = currKey;
            if(currKey == KeyCode.W){ // UP
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.D){ // RIGHT
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.A){ // LEFT
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.S){ // DOWN/BACK
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.SPACE){ // Stop/Space
                keyPressedSystemTime = System.currentTimeMillis();
            }
            else if(currKey == KeyCode.E){ // Capture Photo
                captureImageBtnClicked();
            }
            else if(currKey == KeyCode.TAB){ // Capture Photo
                backtrackBtnClicked();
            }
<<<<<<< HEAD
            else if(currKey == KeyCode.ESCAPE){ // Exits the system
=======
            else if(currKey == KeyCode.ESCAPE){ // Exits the program
                System.out.println("System: Closing the application");
>>>>>>> dev_1
                System.exit(0);
            }else if(currKey == KeyCode.T){ // Starts auto traceback
                traceBackBtnClicked();
            }
        }
    }

    /**
     * System time when key was released
     * */
    StringBuilder backTrackingLog = new StringBuilder();
    StringBuilder forwardTrackingLog = new StringBuilder();
    public void arrowKeyReleaseHandler(KeyEvent keyEvent) throws IOException {

        /** Values initializers for heatmap */
        String direction = "Forward";
        long distance = 0;

        /** Checks which key was released to map its released system time*/
        KeyCode releasedKey = keyEvent.getCode();
        if (currKey == releasedKey) {
            keyHeldDuration = System.currentTimeMillis() - keyPressedSystemTime;
            keyPressedSystemTime = 0;
            lastKey = null;
        }

        /** Controls and SystemLogging on TextArea */
        if (currKey == KeyCode.W ) {

            if(keyHeldDuration / 1000 > 0){
                forwardTrackingLog.append("Forward : " + keyHeldDuration /1000 + " sec ");
                forwardTrackingLog.append(keyHeldDuration % 1000 + " millisec");
                secondsTravelled += keyHeldDuration;

                backTrackingLog.append("Reverse : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");

                RTH_Controller.traceBackStack.push("B" + keyHeldDuration / 1000);

                direction = "Forward";
                distance = keyHeldDuration / 1000;

            }else{
                forwardTrackingLog.append("Forward : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Reverse : ").append(keyHeldDuration % 1000).append(" millisec");
            }

            forwardTrackingLog.append("\n");
            backTrackingLog.append("\n");

        }
        else if (currKey == KeyCode.A){
            if(keyHeldDuration /1000 > 0){
                forwardTrackingLog.append("Left        : " + keyHeldDuration /1000 + " sec ");
                forwardTrackingLog.append(keyHeldDuration %1000 + " millisec");
                secondsTravelled += keyHeldDuration;

                backTrackingLog.append("Right      : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");

                RTH_Controller.traceBackStack.push("R" + keyHeldDuration / 1000);

                direction = "Left";
                distance = keyHeldDuration / 1000;

            }else{
                forwardTrackingLog.append("Left        : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Right      : ").append(keyHeldDuration % 1000).append(" millisec");
            }

            forwardTrackingLog.append("\n");
            backTrackingLog.append("\n");

        }
        else if(currKey == KeyCode.S){
            if(keyHeldDuration /1000 > 0){
                forwardTrackingLog.append("Reverse : " + keyHeldDuration /1000 + " sec ");
                forwardTrackingLog.append(keyHeldDuration %1000 + " millisec");
                secondsTravelled += keyHeldDuration;

                backTrackingLog.append("Forward : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");

                RTH_Controller.traceBackStack.push("F" + keyHeldDuration / 1000);

                direction = "Reverse";
                distance = keyHeldDuration / 1000;

            }else{
                forwardTrackingLog.append("Reverse : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Forward : ").append(keyHeldDuration % 1000).append(" millisec");
            }

            forwardTrackingLog.append("\n");
            backTrackingLog.append("\n");

        }
        else if(currKey == KeyCode.D){
            if(keyHeldDuration /1000 > 0){
                forwardTrackingLog.append("Right     : " + keyHeldDuration /1000 + " sec ");
                forwardTrackingLog.append(keyHeldDuration %1000 + " millisec");
                secondsTravelled += keyHeldDuration;

                backTrackingLog.append("Left       : ").append(keyHeldDuration / 1000).append(" sec ");
                backTrackingLog.append(keyHeldDuration % 1000).append(" millisec");

                RTH_Controller.traceBackStack.push("L" + keyHeldDuration / 1000);

                direction = "Right";
                distance = keyHeldDuration / 1000;

            }else{
                forwardTrackingLog.append("Right     : " + keyHeldDuration %1000 + " millisec");
                backTrackingLog.append("Left       : ").append(keyHeldDuration % 1000).append(" millisec");
            }

            forwardTrackingLog.append("\n");
            backTrackingLog.append("\n");

        }
        else if(currKey == KeyCode.SPACE){
            forwardTrackingLog.append("Brake");
            backTrackingLog.append("Brake");

            direction = "Forward";
            distance = 0;

            forwardTrackingLog.append("\n");
            backTrackingLog.append("\n");
        }

        /** Depending upon status of track-backtrack toggle show system log contect*/
        if( !isBackTrackOn ){
            systemLogTA_ID.setText(String.valueOf(forwardTrackingLog));
        }else{
            systemLogTA_ID.setText(String.valueOf(backTrackingLog));
        }

        /** Call method to calculate total distance covered w.r.t. amount of time key was held*/
        totalDistanceTravelled();

        /** Sending data to heatMapGenerator class after each key release*/
        HeatMapGenerator.heatChartGenerator(direction, distance);
        HeatMapGenerator.heatMapGeneration();

        /** Responsible for creating blended heatChart with maps*/
        new MapBlender().combineHeatmapWithGoogleMap();

    }

    /**
     * Total distance travelled
     * */
    float secondsTravelled = 0;
    float movedDistance = 0;
    double vehicleSpeed = 5.0; // 50 miles per hour
    private void totalDistanceTravelled(){
        movedDistance = (float) ( (vehicleSpeed / 360.0) * secondsTravelled);
        distanceCovered_ID.setText(String.valueOf(movedDistance));
    }

    /**
     * Toggle between Track & Backtrack logs
     * */
    boolean isBackTrackOn = false;
    public void backtrackBtnClicked() {
        if(!isBackTrackOn){
            isBackTrackOn = true;
            backTrack_btn.setText("Track (TAB)");
            systemLogTA_ID.setText(String.valueOf(backTrackingLog));
        }else{
            isBackTrackOn = false;
            backTrack_btn.setText("Backtrack (TAB)");
            systemLogTA_ID.setText(String.valueOf(forwardTrackingLog));
        }

    }


    /**
     * Capture Images from default camera and sent to ImageProcessor
     * */
    boolean isCaptureClicked = false;
    public void captureImageBtnClicked(){

        System.out.println("Camera: Attempting to capture Image");

        isCaptureClicked = true;
        ImageProcessor.stopCapture();

        System.out.println("Camera: Acquiring camera resource to capture Image");
        Webcam webCamObj = Webcam.getDefault();
        webCamObj.open();

        BufferedImage capturedImage = webCamObj.getImage();

        byte[] pixels = ((DataBufferByte) capturedImage.getRaster().getDataBuffer()).getData();
        Mat capturedMat = new Mat(capturedImage.getHeight(), capturedImage.getWidth(), CvType.CV_8UC3);
        capturedMat.put(0, 0, pixels);

        System.out.println("Camera: Performing face Detection");
        ImageProcessor.detectFaceFromImages(capturedMat, isCaptureClicked);

        isCaptureClicked = false;
        System.out.println("Camera: Releasing Camera resource after capture");
        webCamObj.close();
        System.out.println("Camera: Attempting to acquire camera resource for Video streaming");
        turnOnVideoCam();
    }

    /**
     * Capture video from video cam
     * */
    public static VideoCapture capture;
    public void turnOnVideoCam() {
        capture = new VideoCapture(0);

        new AnimationTimer() {
            @Override public void handle(long l) {
                /** Calling Image processor class for face detection */
                imageView_ID.setImage(ImageProcessor.getCapture());
            }
        }.start();

        System.out.println("Camera: Successfully acquired camera resource for video streaming");
    }

    public void traceBackBtnClicked() throws IOException {
        RTH_Controller.traceBackController();
    }
}

//class Weather_battey_Thread extends Thread {
//
//    NavigationPanelController navigationPanelController = new NavigationPanelController();
//
//    public String batteryData;
//
//    @Override
//    public void run(){
//
//        System.out.println("System: Checking battery Percentage");
//        /** Battery of laptop showing here can be replaced with vehicles battery */
//        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
//        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
//        batteryData = batteryStatus.toString();
//
////        navigationPanelController.batteryLife_ID.setText( batteryStatus.toString() );
//
////        while( true ){
////
////            /** Get System data */
////            try{
////
////                System.out.println("Location: Attempting to get weather data");
////
////                /** Gets Latitude and Longitude */
////                String latitude = LocalMapGenerator.latitudeGetter( NavigationPanelController.public_IP );
////                String longitude = LocalMapGenerator.longitudeGetter( NavigationPanelController.public_IP );
////
////                /** Call Weather api for weather data */
////                String currentWeather = LocalMapGenerator.getWeatherData(latitude, longitude);
////                navigationPanelController.weatherRtf_ID.setText( currentWeather );
////
////                sleep(60000);
////
////            }catch(Exception e){
//////                navigationPanelController.weatherRtf_ID.setText("N/A");
////                System.out.println("Location: Couldn't fetch weather data");
////                e.printStackTrace();
////            }
////        }
//
//    }
//}
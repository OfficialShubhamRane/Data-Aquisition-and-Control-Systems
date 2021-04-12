
/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        Parent LoginView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("LoginView.fxml")));
//        Parent LoginView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("NavigationPanelView.fxml")));
        Scene LoginViewScene = new Scene(LoginView);
        primaryStage.setResizable(true);
        primaryStage.setScene(LoginViewScene);
        primaryStage.setTitle("Navigation&Surveillance Controller");
        primaryStage.show();


    }
}

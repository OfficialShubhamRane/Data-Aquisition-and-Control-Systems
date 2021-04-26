
/** Author: Shubham-Rane www.linkedin.com/in/shubham-rane97 **/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        /** Backdoor for testing */
//        Parent LoginView = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("NavigationPanelView.fxml")));

        Image icon = new Image("icon.png");

        Scene LoginViewScene = new Scene(LoginView);
        primaryStage.setScene(LoginViewScene);
        primaryStage.setTitle("Login");
        primaryStage.getIcons().add(icon);
        primaryStage.setHeight(450);
        primaryStage.setWidth(845);
        primaryStage.setResizable(false);
        primaryStage.show();


    }
}

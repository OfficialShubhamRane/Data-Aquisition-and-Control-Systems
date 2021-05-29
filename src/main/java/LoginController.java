/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    public static String operatorName;
    private String operatorPass;

    public Label invalidUserLb_ID;

    @FXML
    private PasswordField passwordTf_ID;

    @FXML
    private TextField userIDTf_ID;
    public boolean isValidUser;

    LoginDAO loginDAOObj;
    NotificationsGenerator notificationObject;

    public void initialize(){

        System.out.println("Loaded Login Screen");
        invalidUserLb_ID.setVisible(false);
        loginDAOObj = new LoginDAO();
        notificationObject = new NotificationsGenerator();
    }

    @FXML
    void loginBtnClicked(ActionEvent event) throws SQLException, IOException {

        operatorName = userIDTf_ID.getText();
        operatorPass = passwordTf_ID.getText();

        System.out.println("Logging in with credentials: " + operatorName + ", " + operatorPass);

        /** Validates user from database **/
        isValidUser = loginDAOObj.authenticateUser(operatorName, operatorPass);

        /**  Notification  as a feedback for uesr authentification */
//        notificationObject.loginNotification(isValidUser);
//        notificationObject.alertBox();

        /** If user is valid then load the next window */
        if(isValidUser){
            System.out.println("Successfully Logged in");
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("NavigationPanelView.fxml"))));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(575);
            stage.setWidth(845);
            stage.setTitle("Data Acquisition and Controls");
            stage.setResizable(false);
            stage.show();

        }else{
            System.out.println("Unsuccessful Login attempt");
            invalidUserLb_ID.setVisible(true);
        }


    }

}

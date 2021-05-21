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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    public static String operatorName;
    public Label invalidUserLb_ID;

    @FXML
    private PasswordField passwordTf_ID;

    @FXML
    private TextField userIDTf_ID;
    public boolean isValidUser;

    NotificationsGenerator notificationObject;

    public void initialize(){
        invalidUserLb_ID.setVisible(false);
        notificationObject = new NotificationsGenerator();
    }

    @FXML
    void loginBtnClicked(ActionEvent event) throws SQLException, IOException, InterruptedException {
        operatorName = userIDTf_ID.getText();
        String operatorPass = passwordTf_ID.getText();

        LoginDAO loginDAOObj = new LoginDAO();

        /** Validates user from database **/
        isValidUser = loginDAOObj.authenticateUser(operatorName, operatorPass);

        /**  Notification  as a feedback for uesr authentification */
//        notificationObject.loginNotification(isValidUser);

        /** If user is valid then load the next window */
        if(isValidUser){
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
            invalidUserLb_ID.setVisible(true);
        }


    }

}

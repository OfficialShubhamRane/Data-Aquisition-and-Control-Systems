/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    public AnchorPane rootPane;
    public static String operatorName;
    public Label invalidUserLb_ID;

    @FXML
    private PasswordField passwordTf_ID;

    @FXML
    private TextField userIDTf_ID;

    public boolean isValidUser;

    @FXML
    void loginBtnClicked() throws SQLException, IOException {
        System.out.println("User_ID: " + userIDTf_ID.getText());
        System.out.println("Password: " + passwordTf_ID.getText());
        operatorName = userIDTf_ID.getText();

        /** Validates user from database **/
        isValidUser = LoginDAO.authenticateUser(userIDTf_ID.getText(),passwordTf_ID.getText());

        if(isValidUser){
            Pane navigationPanel = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("NavigationPanelView.fxml")));
            rootPane.getChildren().setAll(navigationPanel);
        }else{
            invalidUserLb_ID.setVisible(true);
        }


    }

}

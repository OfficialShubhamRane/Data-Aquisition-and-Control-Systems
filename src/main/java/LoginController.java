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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void loginBtnClicked(ActionEvent event) throws SQLException, IOException {
        System.out.println("User_ID: " + userIDTf_ID.getText());
        System.out.println("Password: " + passwordTf_ID.getText());
        operatorName = userIDTf_ID.getText();

        /** Validates user from database **/
        isValidUser = LoginDAO.authenticateUser(userIDTf_ID.getText(),passwordTf_ID.getText());

        if(isValidUser){

            root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("NavigationPanelView.fxml"))));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setHeight(450);
            stage.setWidth(845);
            stage.setTitle("Data Acquisition and Controls Panel");
            stage.setResizable(false);
            stage.show();

        }else{
            invalidUserLb_ID.setVisible(true);
        }


    }

}

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationsGenerator {

    private String title = null;
    private String text = null;

    @FXML
    public void loginNotification(boolean isValidUser) {

        System.out.println("Entered in notification");
        Image img;
        if (isValidUser){
            System.out.println("In success");
            title = "SuccessFul Login";
            text = "Operator has logged in successfully";
        }
        else{
            System.out.println("In Unsuccessful");
            title = "Unsuccessful Login";
            text = "Invalid Credentials";
        }

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(10));
        notificationBuilder.showInformation();

    }


}

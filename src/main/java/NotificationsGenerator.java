import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationsGenerator {

    private String title = null;
    private String text = null;
    public void loginNotification(boolean isValidUser) {

        /** Depending upon validity of user display below info */
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

        /** Generates a notification */
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(10));
        notificationBuilder.showInformation();

    }


}

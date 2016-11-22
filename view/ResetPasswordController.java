package HAI.view;

import HAI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Created by Khalin on 11/12/2016.
 */
public class ResetPasswordController {


    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button resetButton;

    private Stage resetWindow = new Stage();


    public void setResetWindow(Stage resetWindow) {
        this.resetWindow = resetWindow;
    }


    @FXML public String handleResetPassword() {
        String newPassword, confirmPassword;
        newPassword = newPasswordField.getText();
        confirmPassword = confirmPasswordField.getText();
     if (!newPassword.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Passwords don't match");
            alert.showAndWait();
        }else {
         resetWindow.close();
     }
     return newPassword;
    }
}

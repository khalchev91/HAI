package HAI.view;

import HAI.MainApp;
import HAI.model.Staff;
import HAI.model.Student;
import HAI.utilities.Address;
import HAI.utilities.Date;
import HAI.utilities.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Khalin on 11/3/2016.
 */
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button resetPasswordButton;

    private Stage loginWindow = new Stage();
    private String loginPassword;

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public void setLoginWindow(Stage loginWindow) {
        this.loginWindow = loginWindow;
    }

    @FXML
    public void handleLogin() {
        String password="";
        if (loginWindow.getTitle()=="Student Login") {
            int id;
            try {
                int loginId = Integer.parseInt(usernameField.getText());
                try {
                    try{
                    RandomAccessFile studentLogin = new RandomAccessFile(new File("studentLogin.hai"), "r");
                    studentLogin.seek((loginId - 1) * (4 + (25 * 2)));
                    id = studentLogin.readInt();
                    password = studentLogin.readUTF();
                    if (passwordField.getText().equals(password)) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Successful login");
                        alert.showAndWait();
                        setLoginPassword(password);
                        loginWindow.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failure");
                        alert.setContentText("Password failed");
                        alert.showAndWait();
                    }
                    studentLogin.close();
                    }catch (EOFException exc){
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("ID exceeded file limit");
                        alert.showAndWait();
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a number");
                alert.showAndWait();
            }

        } else if (loginWindow.getTitle()=="Staff Login") {
            int id;

            try {
                int loginId = Integer.parseInt(usernameField.getText());
                try {
                    try{
                    RandomAccessFile staffLogin = new RandomAccessFile(new File("staffLogin.hai"), "r");
                    staffLogin.seek((loginId - 1) * (4 + (25 * 2)));
                    id = staffLogin.readInt();
                    password = staffLogin.readUTF();
                    if (passwordField.getText().equals(password)) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Successful login");
                        alert.showAndWait();
                        setLoginPassword(password);
                        loginWindow.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failure");
                        alert.setContentText("Password failed");
                        alert.showAndWait();
                    }
                    staffLogin.close();
                    }catch (EOFException exc){
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("ID exceeded file limit");
                        alert.showAndWait();
                    };
                } catch (IOException exc) {
                    exc.printStackTrace();
                }

            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a number");
                alert.showAndWait();
            }
        } else {
            try {
                int loginId = Integer.parseInt(usernameField.getText());
                        if ((loginId == 123) && passwordField.getText().equals("123456")) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Successful login");
                        alert.showAndWait();
                        loginWindow.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failure");
                        alert.setContentText("Username or password incorrect");
                        alert.showAndWait();
                    }

            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a number");
                alert.showAndWait();
            }
        }
    }



    @FXML
    public void handleReset() {

        MainApp mainApp = new MainApp();

        if (loginWindow.getTitle().equals("Student Login")) {
            String password = mainApp.reset();
            RandomAccessFile studentLogin = null;
            try {
                int loginId = Integer.parseInt(usernameField.getText());
                if (loginId < 1600 || loginId > 1630) {
                    try {

                        studentLogin = new RandomAccessFile(new File("studentLogin.hai"), "rw");
                        studentLogin.seek((loginId - 1) * (4 + (25 * 2)));
                        studentLogin.writeInt(loginId);
                        studentLogin.writeUTF(password);
                    } catch (IOException exc) {
//
                    } finally {
                        try {
                            studentLogin.close();
                        } catch (IOException exc) {
                            //
                        }
                    }
                }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("ID exceeded file limit");
                        alert.showAndWait();
                    }


            } catch (NumberFormatException nfe) {
                //
            }


        }else if(loginWindow.getTitle().equals("Staff Login")){
            String password = mainApp.reset();
            RandomAccessFile staffLogin= null;
            try {
                int loginId= Integer.parseInt(usernameField.getText());
                try {
                    try{
                    staffLogin= new RandomAccessFile(new File("staffLogin.hai"),"rw");
                    staffLogin.seek((loginId-1)*(4+(25*2)));
                    staffLogin.writeInt(loginId);
                    staffLogin.writeUTF(password);
                    }catch (EOFException exc){
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("ID exceeded file limit");
                        alert.showAndWait();
                    }
                }catch (IOException exc){

                }finally {
                    try {
                        staffLogin.close();
                    }catch (IOException exc){

                    }
                }
            }catch (NumberFormatException nfe){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a number");
                alert.showAndWait();
            }
        }else{
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You cannot change your password as you are an administrator");
            alert.showAndWait();
        }

    }

}





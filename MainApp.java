package HAI;/**
 * Created by Khalin on 10/22/2016.
 */

import HAI.model.*;
import HAI.view.InitialController;
import HAI.view.LoginController;
import HAI.view.ResetPasswordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import ;

import java.io.IOException;

public class MainApp extends Application {
public Stage primaryStage= new Stage();
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("view/initial.fxml"));
            AnchorPane initial=loader.load();
            primaryStage.setTitle("HAI");
            primaryStage.setScene(new Scene(initial));

            InitialController controller= loader.getController();
            controller.setInitWindow(primaryStage);
        //initialize();
            primaryStage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }
    public void login(String loginType){
        Stage loginWindow= new Stage();

        try{
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("view/Login.fxml"));
            AnchorPane logon= loader.load();
            loginWindow.setTitle(loginType);
            loginWindow.initStyle(StageStyle.UNDECORATED);
            loginWindow.initModality(Modality.APPLICATION_MODAL);
            LoginController controller= loader.getController();
            controller.setLoginWindow(loginWindow);

            loginWindow.setScene(new Scene(logon));
            loginWindow.showAndWait();

        }catch(IOException exc){
            exc.printStackTrace();
        }

    }

    public String reset(){
        Stage resetWindow= new Stage();
        String password=null;
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("view/ResetPassword.fxml"));
            AnchorPane reset= loader.load();
            resetWindow.setTitle("");
            resetWindow.initStyle(StageStyle.UNDECORATED);
            resetWindow.initModality(Modality.APPLICATION_MODAL);

            ResetPasswordController controller= loader.getController();
            controller.setResetWindow(resetWindow);

            resetWindow.setScene(new Scene(reset));
            resetWindow.showAndWait();

            password= controller.handleResetPassword();
            return password;
        }catch (IOException exc){
            //
        }

        return password;
    }

public void initialize(){
    Programme programme= new Programme();
    Student student= new Student();
    Staff staff= new Staff();
    Course course= new Course();
    programme.initialize();
    student.initialize();
    //student.initializeLogin();
    //staff.initialize();
    //staff.initializeLogin();
        course.initialize();
    course.createFile();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

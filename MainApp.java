package HAI;/**
 * Created by Khalin on 10/22/2016.
 */
import HAI.utilities.Address;
import HAI.utilities.Date;
import HAI.utilities.PhoneNumber;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import HAI.model.*;
import HAI.view.InitialController;
import HAI.view.LoginController;
import HAI.view.ResetPasswordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import ;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp extends Application {
public Stage primaryStage= new Stage();
    @Override
    public void start(Stage primaryStage) {
        try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/initial.fxml"));
                        AnchorPane initial = loader.load();

                        primaryStage.setTitle("HAI");
                        primaryStage.setScene(new Scene(initial));


                        InitialController controller = loader.getController();
                        controller.setInitWindow(primaryStage);

            login();

        /*    PhoneNumber phoneNumber= new PhoneNumber("876","846","0837");
            Date date= new Date(1,2,2016);
            Date day= date.toDate(date.toString());
            PhoneNumber phone=phoneNumber.toPhoneNumber(phoneNumber.toString());
            Address address= new Address("12 Rocky Road","Kingston");
            Address address1=new Address();
            System.out.println(address);
            System.out.println(date);
            System.out.println(day.getYear());
            address1= address.toAddress(address.toString());
                System.out.println(address1.getStreet());
                System.out.println(phone.getLine());
                System.out.println(address.toAddress(address.toString()));*/
                //primaryStage.show();
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }

    }
    public void login(){
        Stage loginWindow= new Stage();

        try{
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("view/Login.fxml"));
            AnchorPane logon= loader.load();
            loginWindow.setTitle("Login");
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

public boolean initialize(){
    Programme programme= new Programme();
    programme.initialize();
    Student student= new Student();
    student.initialize();
    Course course= new Course();
    //course.initialize();
    //course.createFile();
   /* Programme programme= new Programme();
    Person student= new Student();
    Person staff= new Staff();
    Course course= new Course();
    programme.initialize();
    student.initialize();
    student.initializeLogin();
    staff.initialize();
    staff.initializeLogin();
    course.initialize();
    course.createFile();*/
   return true;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

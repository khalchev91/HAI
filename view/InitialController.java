package HAI.view;

import HAI.MainApp;
import HAI.model.Staff;
import HAI.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Created by Khalin on 10/22/2016.
 */
public class InitialController {
    @FXML
    private MenuItem addStaffAccount;
    @FXML
    private MenuItem staffLogin;
    @FXML
    private MenuItem studentLogin;

private MainApp mainApp= new MainApp();
Stage initWindow= new Stage();
    public void setInitWindow(Stage initWindow){
        this.initWindow=initWindow;
    }
    @FXML
    private void handleNewStaff(){
        Staff staff= new Staff();
        staff.newStaffForm();
    }
    @FXML
    private void handleStaffLogin(){
        Staff staff= new Staff();
        initWindow.close();
        staff.staffMenu();
    }

    @FXML
    private void handleStudentLogin(){
        Student student= new Student();
            initWindow.close();
          //  student.studentMenu();
        }
    }


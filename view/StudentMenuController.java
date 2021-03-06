package HAI.view;

import HAI.MainApp;
import HAI.model.Course;
import HAI.model.Programme;
import HAI.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Created by Khalin on 11/1/2016.
 */
public class StudentMenuController {
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem programmeDetails;
    @FXML
    private MenuItem addCourse;
    @FXML
    private MenuItem feeBreakdown;
    @FXML
    private MenuItem progressReport;

    private Stage studentMenu;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentMenu(Stage studentMenu) {
        this.studentMenu = studentMenu;
    }

    @FXML
    public void handleLogout() {
        MainApp mainApp = new MainApp();
        studentMenu.close();
        mainApp.start(studentMenu);
    }

    @FXML
    public void handleProgrammeDetails() {
        Programme programme = new Programme();
        programme.programmeDetails(id);
    }

    @FXML
    public void handleAddCourse() {
        Student student= new Student();
        student.studentAddCourse(id);
    }
    @FXML
    public void handleGenerateFees(){
        Student student= new Student();
        student.generateFees(id);
    }
    @FXML public void handleGenerateProgressReport(){
        Student student= new Student();
        student.generateProgressReport(id);
    }
}

package HAI.view;

import HAI.MainApp;
import HAI.model.Course;
import HAI.model.Programme;
import HAI.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;

/**
 * Created by Khalin on 10/27/2016.
 */
public class StaffMenuController {
    @FXML private MenuItem addStudent;
    @FXML private MenuItem createProgramme;
    @FXML private MenuItem createCourse;
    @FXML private MenuItem editProgramme;
    @FXML private MenuItem viewStudents;
    @FXML private MenuItem logout;
    @FXML private SeparatorMenuItem separatorMenuItem;


    Stage staffMenu;
    public void setStaffMenu(Stage staffMenu){
        this.staffMenu= staffMenu;
    }
    @FXML
    private void handleAddStudent(){
        Student student= new Student();
        student.newStudentForm();
    }
    @FXML
    private void handleLogout(){
        MainApp mainApp= new MainApp();
        staffMenu.close();
        mainApp.start(staffMenu);
    }
@FXML private void handleNewProgramme(){
        int code=0;
    Programme programme= new Programme();
    Course course= new Course();
    if(code==0) {
        code = programme.programmeForm();
        course.addCourse(code);
    }
}

@FXML private void handleEditProgramme() {
    int code = 0;
    Programme programme = new Programme();
    Course course = new Course();

        programme = programme.search();
        code = programme.editProgramme(programme);
    if (code != 0) {
        course.addCourse(code);
    }
}
@FXML private void handleGenerateStudentList(){
    Programme programme= new Programme();
    programme.generateStudentList();
}
@FXML private void handleCreateCourse(){
    Course course= new Course();
    course.courseForm();
}
}

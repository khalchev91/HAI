package HAI.view;

import HAI.model.Course;
import HAI.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Created by Khalin on 11/18/2016.
 */
public class GenerateFeesController {
    private ObservableList<Course>courseData= FXCollections.observableArrayList();

    @FXML private TextField studentIdField;
    @FXML private TextField totalCreditsField;
    @FXML private TextField totalCostsField;
    @FXML private TableView<Course>courseTableView;
    @FXML private TableColumn<Course,Integer>codeColumn;
    @FXML private TableColumn<Course,String>courseNameColumn;
    @FXML private TableColumn<Course,Integer>creditColumn;
    @FXML private TableColumn<Course,Float>costColumn;
    @FXML private Button produceFeesButton;

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public ObservableList<Course> getCourseData() {
        return courseData;
    }

    @FXML public void populateTable() {
        float totalCost = 0, costPerCredit, costForCourse;
        int totalCredits = 0, code, courseCode, studentId, credit, prerequisite;
        String courseName, courseDescription;
        Course course = new Course();
        Student student = new Student();
        Scanner studentCourseFile = null;
        RandomAccessFile courseFile = null;
        courseData.clear();
        courseTableView.setItems(getCourseData());

            try {
                studentCourseFile = new Scanner(new File("student-course.hai"));
                while (studentCourseFile.hasNext()) {
                    studentId = studentCourseFile.nextInt();
                    courseCode = studentCourseFile.nextInt();
                    if (studentId == id) {
                        try {
                            courseFile = new RandomAccessFile(new File("course.hai"), "r");
                            courseFile.seek((courseCode - 1) * course.getRecordSize());
                            code = courseFile.readInt();
                            courseName = courseFile.readUTF();
                            courseDescription = courseFile.readUTF();
                            credit = courseFile.readInt();
                            prerequisite = courseFile.readInt();
                            costPerCredit = courseFile.readFloat();
                            costForCourse = courseFile.readFloat();
                            totalCost += costForCourse;
                            totalCredits += credit;
                            course = new Course(code, courseName, courseDescription, credit, prerequisite, costPerCredit, costForCourse);
                            courseData.add(course);
                            courseTableView.setItems(getCourseData());
                            totalCostsField.setText(Float.toString(totalCost));
                            totalCreditsField.setText(Integer.toString(totalCredits));

                        } catch (FileNotFoundException exc) {
                            //
                        }
                    }
                }

            } catch (IOException exc) {
                //
            }
            if (courseData.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You didn't register for a course");
                alert.showAndWait();
            } else {
                updateStudentFile();
            }
    }
    private void updateStudentFile(){
        Student student= new Student();
        String firstName,lastName,enrollmentStatus;
        RandomAccessFile studentFile= null;
        try {
            studentFile= new RandomAccessFile(new File("student.hai"),"r");

        }catch (FileNotFoundException fne){
            //
        }
        try {
            studentFile.seek((id-1)*student.getRecordSize());
            student.setId(studentFile.readInt());
            student.setFirstName(studentFile.readUTF());
            student.setLastName(studentFile.readUTF());
            student.setEnrollmentStatus(studentFile.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                studentFile.close();
            }catch (IOException exc){

            }
        }
        student.setEnrollmentStatus("1");
        try {
            studentFile = new RandomAccessFile(new File("student.hai"), "rw");
            try {
                studentFile.seek((id-1)*student.getRecordSize());
                studentFile.writeInt(student.getId());
                studentFile.writeUTF(student.getFirstName());
                studentFile.writeUTF(student.getLastName());
                studentFile.writeUTF(student.getEnrollmentStatus());
            }catch (IOException exc) {
                //
            }
        }catch (FileNotFoundException fne){
            //
        }finally {
            try {
                studentFile.close();
            }catch (IOException exc){
                //
            }
        }
    }

}

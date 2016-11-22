package HAI.view;

import HAI.model.Course;
import HAI.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Khalin on 11/12/2016.
 */
public class StudentAddCourseController {
    ObservableList<Course>courseObservableList= FXCollections.observableArrayList();
    @FXML private TableView<Course>courseTableView;
    @FXML private TableColumn<Course, Integer>courseCodeColumn;
    @FXML private TableColumn<Course,String>courseNameColumn;
    @FXML private TextField studentIdField;
    @FXML private Button searchStudentButton;
    @FXML private Button registerForCourse;

    public ObservableList<Course> getCourseObservableList() {
        return courseObservableList;
    }

    @FXML private void populateTable() {
        int id,code,courseCode,programmeCode,courseCodeProgramme,prerequisite,credit;
        float costPerCredit,courseCost;
        Student student= new Student();
        Course course= new Course();
        String firstName,lastName,enrollmentStatus,courseName,description;
        RandomAccessFile studentFile= null;
        RandomAccessFile courseFile=null;
        Scanner seqStudentFile=null;
        courseObservableList.clear();
        courseTableView.setItems(getCourseObservableList());
        try {
            int studentID = Integer.parseInt(studentIdField.getText());
                try{
                    seqStudentFile= new Scanner(new File("course-programme.hai"));
                    studentFile= new RandomAccessFile(new File("student.hai"),"r");
                    courseFile= new RandomAccessFile(new File("course.hai"),"r");
                    studentFile.seek((studentID-1)*student.getRecordSize());
                    id=studentFile.readInt();
                    firstName=studentFile.readUTF();
                    lastName=studentFile.readUTF();
                    enrollmentStatus= studentFile.readUTF();
                    code=studentFile.readInt();
                    while (seqStudentFile.hasNext()){
                        courseCodeProgramme =seqStudentFile.nextInt();
                        programmeCode=seqStudentFile.nextInt();
                        if(code==programmeCode) {
                            try {
                                courseFile.seek((courseCodeProgramme - 1) * course.getRecordSize());
                                courseCode = courseFile.readInt();
                                courseName = courseFile.readUTF();
                                description = courseFile.readUTF();
                                credit = courseFile.readInt();
                                prerequisite = courseFile.readInt();
                                costPerCredit = courseFile.readFloat();
                                courseCost=courseFile.readFloat();
                                course = new Course(courseCode, courseName, description, credit, prerequisite, costPerCredit,courseCost);
                                courseObservableList.add(course);
                                courseTableView.setItems(getCourseObservableList());
                            }catch (EOFException eof){
                                //
                            }
                        }
                    }
                }catch (IOException exc){
                    //
                }finally {
                    try {
                        studentFile.close();
                        courseFile.close();
                    }catch (IOException exc){
                seqStudentFile.close();
                    }
                }
            }catch (NumberFormatException nfe){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a number");
            alert.showAndWait();
        }
    }

   @FXML private void handleAdd() {
    Course course= courseTableView.getSelectionModel().getSelectedItem();
       FileWriter courseFile= null;
       try {
           try {
               if(course!=null) {
                   int id = Integer.parseInt(studentIdField.getText());
                   courseFile = new FileWriter("student-course.hai", true);
                   courseFile.write(id + "\t" + course.getCourseCode() + "\n");
               }
           }catch (IOException exc){
               //
           }finally {
               try {
                   courseFile.close();
               }catch (IOException exc){
                   //
               }
           }
       }catch (NumberFormatException nfe){
           Alert alert= new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Please enter a number");
           alert.showAndWait();
       }
   }
    public boolean checkIfExist(int programmeCodeCheck,int courseCodeCheck){
        int courseCode,programmeCode;
        Scanner seqCourse= null;
        try {
            seqCourse= new Scanner(new File("course-programme.hai"));
            while (seqCourse.hasNext()){
                courseCode=seqCourse.nextInt();
                programmeCode= seqCourse.nextInt();
                if(courseCode==courseCodeCheck&&programmeCodeCheck==programmeCode){
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This course already exists in that programme");
                    alert.showAndWait();
                    return false;
                }
            }
        }catch (FileNotFoundException fnfex){
            //
        }finally {
            seqCourse.close();
        }
        return true;
    }
}

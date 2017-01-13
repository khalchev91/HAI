package HAI.view;

import HAI.model.Course;
import HAI.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @FXML private void populate() {

        Student student = new Student();
        Course course = new Course();
        Connection connection = null;
        PreparedStatement statement = null;
        courseObservableList.clear();
        courseTableView.setItems(getCourseObservableList());
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String host = "jdbc:mysql://localhost:3306"+ "user=root&password=khalin1549";
                connection = DriverManager.getConnection(host);
                String sqlCode="";
                courseObservableList.add(course);
                courseTableView.setItems(getCourseObservableList());
            } catch (SQLException sql) {
                System.out.println(sql.getErrorCode());
            }
        } catch (Exception e) {
            //
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sql) {
                        //
                }
            }if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    /**e.printStackTrace();
                **/
                     }
            }
        }
    }

   @FXML private void handleAdd() {
    Course course= courseTableView.getSelectionModel().getSelectedItem();
       FileWriter courseFile= null;
           if (course != null && checkIfExist(id, course.getCourseCode())) {
           try {
                   courseFile = new FileWriter("student-course.hai", true);
                   courseFile.write(id + "\t" + course.getCourseCode() + "\n");

               }catch(IOException exc){
                   //
               }finally{
                   try {
                       courseFile.close();
                   } catch (IOException exc) {
                       //
                   }
               }
           }
   }
    public boolean checkIfExist(int studentId,int courseCodeCheck){
        int id,courseCode;
        Scanner seqCourse= null;
        try {
            seqCourse= new Scanner(new File("student-course.hai"));
            while (seqCourse.hasNext()){
                id=seqCourse.nextInt();
                courseCode= seqCourse.nextInt();
                if(id==studentId&&courseCode==courseCodeCheck){
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

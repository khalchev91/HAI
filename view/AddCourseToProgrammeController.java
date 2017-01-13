package HAI.view;

import HAI.model.Course;
import HAI.model.Programme;
import HAI.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by Khalin on 11/8/2016.
 */
public class AddCourseToProgrammeController {
    private ObservableList<Course>addCourse= FXCollections.observableArrayList();
    private ObservableList<Course>addedCourses= FXCollections.observableArrayList();
    @FXML private TableView<Course> courseTableView;
    @FXML private TableView<Course>addedCourseTableView;
    @FXML private TableColumn<Course,Integer>courseCodeColumn;
    @FXML private TableColumn<Course, String>addedCourseNameColumn;
    @FXML private TableColumn<Course, Integer> courseColumn;
    @FXML private TableColumn<Course,String> courseNameColumn;
    @FXML private Button addToProgramme;
    @FXML private Button removeCourse;
    @FXML private Button populateButton;
    private int programmeCode;

    public void setProgrammeCode(int programmeCode) {
        this.programmeCode = programmeCode;
    }

    public ObservableList<Course> getAddCourse() {
        return addCourse;
    }

    public ObservableList<Course> getAddedCourses() {
        return addedCourses;
    }

    public static boolean contains(TableView<Course> table, Course course) {
        for (Course item : table.getItems())
            if (Integer.toString(item.getCourseCode()).equals(course.getCourseCode())&& item.getCourseName().equals(course.getCourseName())){
                return true;
    }
        return false;
    }

    @FXML private void populate() {
        Course[] course = new Course[2];

        Connection connection = null;
        int pcode, ccode;
        PreparedStatement statement = null;
        addCourse.clear();
        courseTableView.setItems(getAddCourse());
        addedCourses.clear();
        addedCourseTableView.setItems(getAddedCourses());
        try {
            try {
                String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(host);
                course[1]= new Course();
                statement = connection.prepareStatement("SELECT  *FROM  course"+" INNER join CoursesProgramme"+" on course.courseCode= CoursesProgramme.courseCode"+ " WHERE CoursesProgramme.programmeCode=?");
                statement.setInt(1,programmeCode);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    course[1].setCourseCode(resultSet.getInt("courseCode"));
                    course[1].setCourseName(resultSet.getString("courseName"));
                    course[1].setDescription(resultSet.getString("courseDescription"));
                    course[1].setCredit(resultSet.getInt("credits"));
                    course[1].setPrerequisite(resultSet.getInt("coursePreRequisite"));
                    course[1].setCourseCost(resultSet.getFloat("courseCost"));
                    course[1].setCostPerCredit(resultSet.getFloat("costPerCredit"));

                    addedCourses.add(new Course(course[1]));
                    addedCourseTableView.setItems(getAddedCourses());
                }
                System.out.println(addedCourseTableView.getItems());
            } catch (SQLException sql) {
                System.out.println(sql.getErrorCode());
                sql.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            try {
                String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(host);
                statement = connection.prepareStatement("SELECT  *FROM  course");
                course[0]= new Course();
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    course[0].setCourseCode(resultSet.getInt("courseCode"));
                    course[0].setCourseName(resultSet.getString("courseName"));
                    course[0].setDescription(resultSet.getString("courseDescription"));
                    course[0].setCredit(resultSet.getInt("credits"));
                    course[0].setPrerequisite(resultSet.getInt("coursePreRequisite"));
                    course[0].setCourseCost(resultSet.getFloat("courseCost"));
                    course[0].setCostPerCredit(resultSet.getFloat("costPerCredit"));
                    System.out.println(course[0]);
                    if (addedCourseTableView.getItems().contains(new Course(course[0]))){
                        System.out.println("No more courses to add");
                    } else {
                        addCourse.add(new Course(course[0]));
                        courseTableView.setItems(getAddCourse());
                    }
                }

            } catch (SQLException sql) {
                System.out.println(sql.getErrorCode());
                sql.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                }catch (SQLException sql){
                    //
                }
            }
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if(addedCourses.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("NO COURSES EXIST IN THIS PROGRAMME");
            alert.showAndWait();
        }
        if (addCourse.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("NO COURSES EXIST");
            alert.showAndWait();
        }
    }
    @FXML void handleAdd() {
        Course course = courseTableView.getSelectionModel().getSelectedItem();
        int courseCode=course.getCourseCode();
        int courseIndex= courseTableView.getSelectionModel().getSelectedIndex();
        Connection connection = null;
        PreparedStatement statement = null;
        if (courseExistsAlready(programmeCode,courseCode)) {
            try {
                try {
                    String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    connection = DriverManager.getConnection(host);
                    String courseToProgramme="INSERT  into CoursesProgramme" + "(courseCode, programmeCode)"+ " VALUES (?,?)";
                    statement= connection.prepareStatement(courseToProgramme);
                    statement.setInt(1,courseCode);
                    statement.setInt(2,programmeCode);
                    statement.executeUpdate();
                    addedCourses.add(new Course(course));
                    courseTableView.getItems().remove(courseIndex);
                    addedCourseTableView.setItems(getAddedCourses());
                } catch (SQLException sql) {
                    System.out.println(sql.getErrorCode());
                    sql.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException sql) {
                        //
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The course already exists in this programme");
            alert.showAndWait();
        }
    }
@FXML private void handleDelete() {
int selectedIndex= addedCourseTableView.getSelectionModel().getSelectedIndex();
Course course= addedCourseTableView.getSelectionModel().getSelectedItem();
int courseCode=course.getCourseCode();
Connection connection=null;
PreparedStatement preparedStatement=null;
if(selectedIndex>=0) {
    try {
        try {
            String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(host);
            String check = "DELETE FROM CoursesProgramme" + " where courseCode=? AND programmeCode=?";
            preparedStatement = connection.prepareStatement(check);
            preparedStatement.setInt(1,courseCode);
            preparedStatement.setInt(2,programmeCode);
            preparedStatement.executeUpdate();
            addedCourseTableView.getItems().remove(selectedIndex);
            addCourse.add(new Course(course));
            courseTableView.setItems(getAddCourse());
        } catch (SQLException sql) {
            System.out.println(sql.getErrorCode());
            sql.printStackTrace();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sql) {
                //
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}else {
    Alert alert= new Alert(Alert.AlertType.ERROR);
    alert.setContentText("Please select a course in that programme");
    alert.showAndWait();
}
}

public boolean courseExistsAlready(int inProgrammeCode,int inCourseCode) {
        int courseCode,checkProgrammeCode;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try{
            try{
                String host= "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection=DriverManager.getConnection(host);
                String check="if EXISTS(SELECT *FROM CoursesProgramme"+ " where courseCode=? AND programmeCode=?)"+ " SELECT *FROM CoursesProgramme"+ " where CourseCode=? AND ProgrammeCode=?";
                preparedStatement=connection.prepareStatement(check);
                preparedStatement.setInt(1,inCourseCode);
                preparedStatement.setInt(2,inProgrammeCode);
                preparedStatement.setInt(3,inCourseCode);
                preparedStatement.setInt(4,inProgrammeCode);
                if(!(((ResultSet)preparedStatement.executeQuery()).next())){
                    System.out.println("Nothing to be read");
                }else {
                    ResultSet resultSet=preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        System.out.println("...");
                        courseCode = resultSet.getInt("CourseCode");
                        checkProgrammeCode = resultSet.getInt("ProgrammeCode");
                        if (courseCode == inCourseCode && checkProgrammeCode == inProgrammeCode) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }catch (SQLException sql){
                System.out.println(sql.getErrorCode());
                sql.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sql) {
                    //
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
}

}

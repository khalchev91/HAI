package HAI.view;

import HAI.model.Person;
import HAI.model.Programme;
import HAI.model.Student;
import HAI.utilities.Address;
import HAI.utilities.Date;
import HAI.utilities.PhoneNumber;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.*;

/**
 * Created by Khalin on 11/4/2016.
 */
public class GenerateStudentListController {
    private ObservableList<Student>studentInfo= FXCollections.observableArrayList();
    @FXML private TextField programmeCodeField;
    @FXML private Button searchButton;
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, Integer>idColumn;
    @FXML private TableColumn<Student, String>firstNameColumn;
    @FXML private TableColumn<Student, String>lastNameColumn;
    @FXML Button cancelButton;


    private Programme programme= new Programme();
    private Stage generateStudentList;

    public void setGenerateStudentList(Stage generateStudentList) {
        this.generateStudentList = generateStudentList;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }




    public ObservableList<Student>getStudentInfo(){
        return studentInfo;
    }



    @FXML void handleOk(){
        Connection connection= null;
        PreparedStatement statement= null;
        Programme programme= new Programme();
        Student student= new Student();
        studentInfo.clear();
        studentTableView.setItems(getStudentInfo());
        try {
            int code= Integer.parseInt(programmeCodeField.getText());
            try {
                try {

                    String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    connection = DriverManager.getConnection(host);
                    String stement="SELECT *FROM uniStudent "+" WHERE programmeCode=?";
                    statement= connection.prepareStatement(stement);
                    statement.setInt(1,code);
                    ResultSet resultSet=statement.executeQuery();
                    while (resultSet.next()) {
                        student.setId(resultSet.getInt("studentId"));
                        student.setFirstName(resultSet.getString("firstName"));
                        student.setLastName(resultSet.getString("lastName"));
                        student.setProgrammeCode(resultSet.getInt("programmeCode"));
                        student.setContactNumber(new PhoneNumber().toPhoneNumber(resultSet.getString("contactNumber")));
                        student.setEnrollmentStatus(resultSet.getString("enrollmentStatus"));
                        student.setStudentAddress(new Address().toAddress(resultSet.getString("address")));
                        student.setDateEnrolled(new Date().toDate(resultSet.getString("dateEnrolled")));
                        student.setPassword(resultSet.getString("password"));
                        System.out.println(student);
                        studentInfo.add(new Student(student));
                        studentTableView.setItems(getStudentInfo());
                    }
                }catch (SQLException sql){
                    System.out.println(sql.getErrorCode());
                    sql.printStackTrace();
                }
            }catch (Exception e){
                //
            }finally {
                try {
                    if(statement!=null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try{
                    if(connection!=null){
                        connection.close();
                    }
                }catch (SQLException sql){
                    sql.printStackTrace();
                }
            }
        }catch (NumberFormatException nfe){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a number not a string!!!!");
            alert.showAndWait();
        }
        if(studentInfo.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No students are enrolled in this programme");
            alert.showAndWait();
            programmeCodeField.clear();
        }

    }
    @FXML void handleCancel(){
        generateStudentList.close();
    }


}

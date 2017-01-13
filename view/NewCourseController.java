package HAI.view;

import HAI.model.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Khalin on 11/1/2016.
 */
public class NewCourseController {
    @FXML
    TextField courseCodeField;
    @FXML
    TextField courseNameField;
    @FXML
    TextField descriptionField;
    @FXML
    TextField creditField;
    @FXML
    TextField prerequisiteField;
    @FXML
    TextField costPerCreditField;
    @FXML
    Button addCourseButton;


    private Stage courseForm;

    public void setCourseForm(Stage courseForm) {
        this.courseForm = courseForm;
    }

    @FXML
    void handleAddCourse() {
        Course course = new Course();
        Connection connection = null;
        PreparedStatement statement = null;
        if (isInputValid()) {
                try{
                    course.setCourseCode(Integer.parseInt(courseCodeField.getText()));
                    course.setCourseName(courseNameField.getText());
                    course.setDescription(descriptionField.getText());
                    course.setPrerequisite(Integer.parseInt(prerequisiteField.getText()));
                    course.setCredit(Integer.parseInt(creditField.getText()));
                    course.setCostPerCredit(Float.parseFloat(costPerCreditField.getText()));
                    course.setCourseCost(course.getCostPerCredit()*course.getCredit());
                }catch (NumberFormatException nfe){
                    //
                }

            try {
                try {
                    String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    connection = DriverManager.getConnection(host);
                    statement= connection.prepareStatement("INSERT INTO course"+ "(courseCode,courseName,courseDescription,credits,costPerCredit,coursePreRequisite,courseCost)"+"VALUES (?,?,?,?,?,?,?)");
                    statement.setInt(1,course.getCourseCode());
                    statement.setString(2,course.getCourseName());
                    statement.setString(3,course.getDescription());
                    statement.setInt(4,course.getCredit());
                    statement.setFloat(5,course.getCostPerCredit());
                    statement.setInt(6,course.getPrerequisite());
                    statement.setFloat(7,course.getCourseCost());
                    statement.executeUpdate();
                    courseForm.close();
                } catch (SQLException sql) {
                    if (sql.getErrorCode() == 2627) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Course already exists");
                        alert.showAndWait();
                    } else {
                        System.out.println(sql.getErrorCode());
                        sql.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private boolean isInputValid(){
        String errorMessage="";
        int code;
        Pattern textField=Pattern.compile("^[a-z A-Z 0-9]*$");
        Matcher[] matches=new Matcher[3];
        matches[0]=textField.matcher(courseNameField.getText());
        matches[1]=textField.matcher(descriptionField.getText());
        if(courseCodeField.getText()==""||courseCodeField.getText().length()==0){
            errorMessage+="Invalid course code entered";
        }try{
               code= Integer.parseInt(courseCodeField.getText());
            if(code<2500||code>2530){
                errorMessage+="Course Code should be between 2500 and 2530";
            }
            }catch(NumberFormatException nfe){
                errorMessage+="Invalid code entered. Please enter an integer";
                courseCodeField.clear();
            }

        if(courseNameField.getText()==""||courseNameField.getText().length()==0){
            errorMessage+="Invalid Name entered";
        }else if(!(matches[0].find()&&matches[0].group().equals(courseNameField.getText()))){
            errorMessage+="Please enter text";
        }
        if(descriptionField.getText()==""||descriptionField.getText().length()==0){
            errorMessage+="Invalid Description";
        }else if(!matches[1].matches()){
            errorMessage+="Please enter text";
        }
        if(creditField.getText()==""||creditField.getText().length()==0){
            errorMessage+="Invalid field";
        }else{
            try{
                Integer.parseInt(creditField.getText());
            }catch (NumberFormatException nfe){
                errorMessage+="Invalid integer entered. Please enter a number";
            }
        }
        if(prerequisiteField.getText()==""||prerequisiteField.getText().length()==0){
            errorMessage+="Invalid prerequisite entered";
        }else {
            try{
                Integer.parseInt(prerequisiteField.getText());
            }catch (NumberFormatException nfe){
                errorMessage+="Invalid prerequisite entered. Please enter a number";
            }
        }
        if(costPerCreditField.getText()==""||costPerCreditField.getText().length()==0){
            errorMessage+="Invalid cost entered";
        }else{
            try{
                Float.parseFloat(costPerCreditField.getText());
            }catch (NumberFormatException nfe){
                //nfe.printStackTrace();
                errorMessage+="Invalid number entered. Please enter a decimal value";
            }
        }
        if(errorMessage.length()==0){
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Record successfully added");
            alert.setContentText("Record successfully added to file");
            alert.initOwner(courseForm);

            alert.showAndWait();
            return true;
        }else {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fields entered");
            alert.setContentText(errorMessage);
            alert.initOwner(courseForm);
            alert.showAndWait();
            return false;
        }
    }
}

package HAI.view;

import HAI.model.Student;
import HAI.utilities.Address;
import HAI.utilities.Date;
import HAI.utilities.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Khalin on 10/27/2016.
 */
public class NewStudentController {
    @FXML
    private TextField studentIDField;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField parishField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField programmeCodeField;
    @FXML
    private TextField enrolmentStatusField;
    @FXML
    private TextField areaCodeField;
    @FXML
    private TextField exchangeField;
    @FXML
    private TextField lineField;
    @FXML
    private Button addStudentButton;

   private Stage newStudent = new Stage();

    public void setNewStudent(Stage newStudent){
        this.newStudent= newStudent;
    }


    @FXML
    private void handleNewStudent() {
        PreparedStatement statement=null;
        Statement stment=null;
        Connection connection=null;
        if (isInputValid()) {
                        Student student = new Student();
                        student.setId(Integer.parseInt(studentIDField.getText()));
                        student.setFirstName(firstNameField.getText());
                        student.setLastName(lastNameField.getText());
                        student.setEnrollmentStatus("0");
                        student.setProgrammeCode(Integer.parseInt(programmeCodeField.getText()));
                        student.setDateEnrolled(new Date(datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(), datePicker.getValue().getYear()));
                        student.setContactNumber(new PhoneNumber(areaCodeField.getText(), exchangeField.getText(), lineField.getText()));
                        student.setStudentAddress(new Address(streetField.getText(), parishField.getText()));

            try {
               try {
                   String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                   connection = DriverManager.getConnection(host);

                   statement = connection.prepareStatement("INSERT INTO uniStudent"+"(studentId,firstName,lastName,address,dateEnrolled,programmeCode,enrollmentStatus,contactNumber,password)"+"VALUES"+"(?,?,?,?,?,?,?,?,?)");
                   statement.setInt(1, student.getId());
                   statement.setString(2,student.getFirstName());
                   statement.setString(3,student.getLastName());
                   statement.setString(4,student.getStudentAddress().toString());
                    statement.setString(5,student.getDateEnrolled().toString());
                    statement.setInt(6,student.getProgrammeCode());
                    statement.setString(7,student.getEnrollmentStatus());
                    statement.setString(8,student.getContactNumber().toString());
                    statement.setString(9,student.getPassword());
                    statement.executeUpdate();
                    newStudent.close();
                } catch (SQLException sql) {
                   System.out.println(sql.getErrorCode());
                   if (sql.getErrorCode() == 2627) {
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setContentText("CANNOT INSERT DUPLICATE KEY");
                       alert.showAndWait();
                   } else if(sql.getErrorCode()==547){
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setContentText("THIS PROGRAMME DOES NOT EXIST: "+ student.getProgrammeCode());
                       alert.showAndWait();
                   }else {
                       sql.printStackTrace();
                   }
               }
           }catch (Exception exc){
               exc.printStackTrace();
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
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        Pattern textField=Pattern.compile("^[a-z A-Z .]*$");
        Matcher[] matches=new Matcher[4];
        matches[0]=textField.matcher(firstNameField.getText());
        matches[1]=textField.matcher(lastNameField.getText());
        matches[2]=textField.matcher(parishField.getText());
        matches[3]=textField.matcher(enrolmentStatusField.getText());
        Pattern street=Pattern.compile("^[a-z A-Z . 0-9]*$");
        Matcher streetName=street.matcher(streetField.getText());
        if(studentIDField.getText()==""||studentIDField.getText().length()==0){
            errorMessage+="Invalid ID entered\n";
        }else{
            try{
                Integer.parseInt(studentIDField.getText());
            }catch (NumberFormatException nfe){
                errorMessage+="Invalid ID entered. Please enter an integer\n";
            }
        }
        if (firstNameField.getText() == "" || firstNameField.getText().length() == 0) {
            errorMessage += "Invalid First Name entered\n";
        }else if(!matches[0].matches()){
            errorMessage+="Invalid first Name entered";
        }
        if (lastNameField.getText() == "" || lastNameField.getText().length() == 0) {
            errorMessage += "Invalid last Name\n";
        }else if(!matches[1].matches()){
            errorMessage+="Invalid last name entered";
        }

        if (streetField.getText() == "" || streetField.getText().length() == 0) {
            errorMessage += "Invalid Street Entered\n";
        }else if(!streetName.matches()){
            errorMessage+="Invalid street name";
        }
        if (parishField.getText() == "" || parishField.getText().length() == 0) {
            errorMessage += "Invalid Parish Entered\n";
        }else if(!matches[2].matches()){
            errorMessage+="Please enter a valid parish";
        }

       if(datePicker.getValue()==null){
           errorMessage+="Please pick a date\n";
       }
            if (programmeCodeField.getText() == "" || programmeCodeField.getText().length() == 0) {
                errorMessage += "Invalid Programme Code\n";
            }else{
                try{
                    Integer.parseInt(programmeCodeField.getText());
                }catch (NumberFormatException nfe){
                    errorMessage+="Invalid Programme Code. Please enter an integer\n";
                }
            }
            if (enrolmentStatusField.getText() == "" || enrolmentStatusField.getText().length() == 0) {
                errorMessage += "Invalid enrolment status\n";
            }else if(!matches[3].matches()){
                errorMessage+="Invalid Enrolment Status entered";
            }
            if (areaCodeField.getText() == "" || areaCodeField.getText().length() != 3) {
                errorMessage += "Invalid Area Code Entered\n";
            }else{
                try{
                    Integer.parseInt(areaCodeField.getText());
                }catch (NumberFormatException nfe){
                    errorMessage+="Invalid Area Code Entered. Please enter an integer\n";
                }
            }
            if (exchangeField.getText() == "" || exchangeField.getText().length() != 3) {
                errorMessage += "Invalid Exchange Number entered\n";
            }else{
                try{
                    Integer.parseInt(exchangeField.getText());
                }catch (NumberFormatException nfe){
                    errorMessage+="Invalid Exchange Number entered. Please enter an integer\n";
                }
            }
            if (lineField.getText() == "" || lineField.getText().length() != 4) {
                errorMessage += "Invalid line Number entered\n";
            }else{
                try{
                    Integer.parseInt(lineField.getText());
                }catch (NumberFormatException nfe){
                    errorMessage+="Invalid line Number entered. Please enter an integer\n";
                }
            }
            if (errorMessage.length() == 0) {
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Field");
                alert.initOwner(newStudent);
                alert.setContentText(errorMessage);
                alert.showAndWait();
                return false;
            }
        }
    }



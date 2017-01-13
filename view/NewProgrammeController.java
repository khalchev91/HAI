package HAI.view;

import HAI.model.Course;
import HAI.model.Programme;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

/**
 * Created by Khalin on 10/27/2016.
 */
public class NewProgrammeController {

    @FXML private TextField codeField;
    @FXML private TextField nameField;
    @FXML private TextField numberOfCoursesField;
    @FXML private TextField awardField;
    @FXML private TextField accreditationField;
    @FXML private Button addProgramme;

    private Stage programmeForm;
    private Course course= new Course();
    public void setProgrammeForm(Stage programmeForm) {
        this.programmeForm = programmeForm;
    }
    private int programmeCode;


    public int getProgrammeCode() {
        return programmeCode;
    }

    @FXML
    public void handleNewProgramme(){
        Connection connection=null;
        PreparedStatement statement=null;
        Programme programme= new Programme();

        RandomAccessFile programmeFile=null;
        if (isInputValid()) {
        try {
            programme.setProgrammeCode(Integer.parseInt(codeField.getText()));
            programme.setProgrammeName(nameField.getText());
            programme.setMaxNumberOfCourses(Integer.parseInt(numberOfCoursesField.getText()));
            programme.setAward(awardField.getText());
            programme.setAccreditation(accreditationField.getText());
        }catch (NumberFormatException nfe){
            //
        }
        try {
            try {
                String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(host);
                statement=connection.prepareStatement("INSERT INTO Programme"+ "(ProgrammeCode,programmeName,maximumNumberOfCourses,Award,Accreditation)"+ "VALUES(?,?,?,?,?)");
                statement.setInt(1,programme.getProgrammeCode());
                statement.setString(2,programme.getProgrammeName());
                statement.setInt(3,programme.getMaxNumberOfCourses());
                statement.setString(4,programme.getAward());
                statement.setString(5,programme.getAccreditation());
                statement.executeUpdate();
            } catch (SQLException sql) {
                if(sql.getErrorCode()==2627){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("could not add record as it already exists");
                    alert.showAndWait();
                }else{
                    sql.printStackTrace();
                }
            }
        }catch (Exception e){
            //
        }

}


}
private boolean isInputValid(){
    String errorMessage="";
    Pattern textField=Pattern.compile("^[a-z A-Z]*$");
    int code;
    Matcher[] matches=new Matcher[3];
    matches[0]=textField.matcher(nameField.getText());
    matches[1]=textField.matcher(awardField.getText());
    matches[2]=textField.matcher(accreditationField.getText());
    if(codeField.getText()==""||codeField.getText().length()==0){
        errorMessage+="Invalid Programme Code.\n";
    }try{
        code=Integer.parseInt(codeField.getText());
    if(code<1000 ||code>1020){
        errorMessage+="Programme code exceeds limit.Please enter between 1000 and 1020.\n";
    }
        } catch (NumberFormatException nfe) {
            errorMessage += "Invalid Programme Code.Please enter a number.\n";
            codeField.clear();
        }
    if(nameField.getText()==""||nameField.getText().length()==0){
        errorMessage+="Invalid Programme Name\n";
    }else if(!matches[0].matches()){
        errorMessage+="Invalid name entered\n";
    }
    if(numberOfCoursesField.getText()==""||numberOfCoursesField.getText().length()==0){
        errorMessage+="Invalid Number of courses.\n";
    }else{
        try{
            Integer.parseInt(numberOfCoursesField.getText());
        }catch (NumberFormatException nfe){
            errorMessage+="Invalid Number entered. Please enter an integer.\n";
        }
    }
    if(awardField.getText()==""||awardField.getText().length()==0){
        errorMessage+="Invalid Award entered\n";
    }else if(!matches[1].matches()){
        errorMessage+="Invalid award entered\n";
    }
    if(accreditationField.getText()==""||accreditationField.getText().length()==0){
        errorMessage+="Invalid accreditation entered\n";
    }else if(!matches[2].matches()){
        errorMessage+="Invalid accreditation entered\n";
    }
    if (errorMessage.length()==0){
        return true;
    }else{
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Field Entered");
        alert.initOwner(programmeForm);
        alert.setContentText(errorMessage);
        alert.showAndWait();
        return false;
    }
}
}

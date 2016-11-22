package HAI.view;

import HAI.model.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
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
        Course course= new Course();
        if (isInputValid()&&doesRecordExist()) {
            RandomAccessFile courseFile=null;
            try {
                courseFile = new RandomAccessFile(new File("course.hai"), "rw");
                try {
                    courseFile.seek((Integer.parseInt(courseCodeField.getText()) - 1) * course.getRecordSize());
                    courseFile.writeInt(Integer.parseInt(courseCodeField.getText()));
                    courseFile.writeUTF(courseNameField.getText());
                    courseFile.writeUTF(descriptionField.getText());
                    courseFile.writeInt(Integer.parseInt(creditField.getText()));
                    courseFile.writeInt(Integer.parseInt(prerequisiteField.getText()));
                    courseFile.writeFloat(Float.parseFloat(costPerCreditField.getText()));
                    courseFile.writeFloat((Integer.parseInt(creditField.getText())) * (Float.parseFloat(costPerCreditField.getText())));
                } catch (IOException exc) {
                    exc.printStackTrace();
                } finally {
                    try {
                        courseFile.close();
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        courseForm.close();
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
    private boolean doesRecordExist(){
        int code,courseCode;
        Course course= new Course();
        RandomAccessFile courseFile=null;
        code=Integer.parseInt(courseCodeField.getText());
        try{
            courseFile = new RandomAccessFile(new File("course.hai"),"r");
            courseFile.seek((code-1)*course.getRecordSize());
            courseCode= courseFile.readInt();
            if(courseCode!=1){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Record already exists");
                alert.setContentText("Record already exists.");
                alert.initOwner(courseForm);
                alert.showAndWait();
                courseFile.close();
                return false;
            }else {
                return true;
            }

        }catch (IOException exc){
            exc.printStackTrace();
        }finally {
            try{
                courseFile.close();
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
        return false;
    }
}

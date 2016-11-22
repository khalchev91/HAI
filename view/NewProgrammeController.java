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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @FXML
    public int handleNewProgramme(){
        Programme programme= new Programme();
        int code=0;
        RandomAccessFile programmeFile=null;
        if (isInputValid()&& doesRecordExist()) {
        try {
            programmeFile=new RandomAccessFile(new File("programme.hai"),"rw");
            programmeFile.seek((Integer.parseInt(codeField.getText())-1)*programme.sizeOfRecord());
            programmeFile.writeInt(Integer.parseInt(codeField.getText()));
            programmeFile.writeUTF(nameField.getText());
            programmeFile.writeInt(Integer.parseInt(numberOfCoursesField.getText()));
            programmeFile.writeUTF(awardField.getText());
            programmeFile.writeUTF(accreditationField.getText());
            programmeForm.close();
            return Integer.parseInt(codeField.getText());
    }catch(IOException exc){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File failure");
        alert.setContentText("File failed to open");
    }finally {
            try {
                programmeFile.close();
            }catch (IOException exc){
                //
            }
        }

}
return code;

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

    private boolean doesRecordExist(){
        int id,programmeCode;
        Programme programme= new Programme();
        RandomAccessFile programmeFile=null;
        id=Integer.parseInt(codeField.getText());
        try{
            programmeFile = new RandomAccessFile(new File("programme.hai"),"r");
            programmeFile.seek((id-1)*programme.sizeOfRecord());
            programmeCode= programmeFile.readInt();
            System.out.println(programmeCode);
            if(programmeCode!=1){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Record already exists");
                alert.setContentText("Record already exists.");
                alert.initOwner(programmeForm);
                alert.showAndWait();
                programmeFile.close();
                return false;
            }else {
                return true;
            }

        }catch (IOException exc){
            exc.printStackTrace();
        }finally {
            try{
                programmeFile.close();
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
        return false;
    }
}

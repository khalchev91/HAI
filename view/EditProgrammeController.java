package HAI.view;

import HAI.model.Programme;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.RadialGradient;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Khalin on 10/31/2016.
 */
public class EditProgrammeController {
    @FXML private TextField programmeCodeField;
    @FXML private TextField programmeNameField;
    @FXML private TextField numberOfCoursesField;
    @FXML private  TextField awardField;
    @FXML private TextField accreditationField;
    @FXML private Button saveChangesButton;
    @FXML private Button cancelButton;

       private Stage editForm= new Stage();
    public void setEditForm(Stage editForm) {
        this.editForm = editForm;
    }
private Programme programme;

    public void setProgramme(Programme programme){

    this.programme=programme;
    programmeCodeField.setText(Integer.toString(programme.getProgrammeCode()));
    programmeNameField.setText(programme.getProgrammeName());
    numberOfCoursesField.setText(Integer.toString(programme.getMaxNumberOfCourses()));
    awardField.setText(programme.getAward());
    accreditationField.setText(programme.getAccreditation());
}
@FXML public int handleSave() {
    if (isInputValid()) {
        try {
            RandomAccessFile programmeFile = new RandomAccessFile(new File("programme.hai"), "rw");
            programmeFile.seek((Integer.parseInt(programmeCodeField.getText()) - 1) * programme.sizeOfRecord());
            programmeFile.writeInt(Integer.parseInt(programmeCodeField.getText()));
            programmeFile.writeUTF(programmeNameField.getText());
            programmeFile.writeInt(Integer.parseInt(numberOfCoursesField.getText()));
            programmeFile.writeUTF(awardField.getText());
            programmeFile.writeUTF(accreditationField.getText());
            programmeFile.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        editForm.close();
    }
    return Integer.parseInt(programmeCodeField.getText());
}

    private boolean isInputValid(){
        String errorMessage="";
        if(programmeCodeField.getText()==""||programmeCodeField.getText().length()==0){
            errorMessage+="Invalid Programme Code.\n";
        }else if(Integer.parseInt(programmeCodeField.getText())<1000||Integer.parseInt(programmeCodeField.getText())>1020){
            errorMessage+="Programme code exceeds limit";
        }else {
            try {
                Integer.parseInt(programmeCodeField.getText());

            } catch (NumberFormatException nfe) {
                errorMessage += "Invalid Programme Code. Please enter a number.\n";
                programmeCodeField.clear();
            }
        }
        if(programmeNameField.getText()==""||programmeNameField.getText().length()==0){
            errorMessage+="Invalid Programme Name\n";
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
        }
        if(accreditationField.getText()==""||accreditationField.getText().length()==0){
            errorMessage+="Invalid accreditation entered\n";
        }
        if (errorMessage.length()==0){
            return true;
        }else{
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Field Entered");
            alert.initOwner(editForm);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    @FXML private void handleCancel(){
        editForm.close();
    }
}



package HAI.view;

import HAI.model.Programme;
import HAI.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Khalin on 11/7/2016.
 */
public class ViewProgrammeDetailsController {
    private ObservableList<Programme> programmeInfo= FXCollections.observableArrayList();
    @FXML private TableView<Programme> programmeTableView;
    @FXML private TableColumn<Programme,Integer>programmeCodeColumn;
    @FXML private  TableColumn<Programme, String>programmeNameColumn;
    @FXML private Label programmeCodeLabel;
    @FXML private Label programmeNameLabel;
    @FXML private Label maxCoursesLabel;
    @FXML private Label accreditationLabel;
    @FXML private Label awardLabel;
    @FXML private Button searchButton;
    @FXML private TextField studentIDField;

    private  Programme programme= new Programme();

   public void setProgramme(Programme programme) {
       this.programme = programme;
       if (programme != null) {
           programmeCodeLabel.setText(Integer.toString(programme.getProgrammeCode()));
           programmeNameLabel.setText(programme.getProgrammeName());
           maxCoursesLabel.setText(Integer.toString(programme.getMaxNumberOfCourses()));
           awardLabel.setText(programme.getAward());
           accreditationLabel.setText(programme.getAccreditation());
       } else {
           programmeCodeLabel.setText("");
           programmeNameLabel.setText("");
           maxCoursesLabel.setText("");
           awardLabel.setText("");
           accreditationLabel.setText("");
       }
   }

    public ObservableList<Programme>getProgrammeInfo(){
    return programmeInfo;
}
@FXML private void initialize(){
    setProgramme(null);
}

@FXML private void handleSearch(){

    Programme programme= new Programme();
    int code, maxNumberofCourses,studentId,programmeCode;
    Student student= new Student();

    String  accreditation, award,firstName, lastName;
    String enrollmentStatus;
    String programmeName;
    RandomAccessFile studentFile= null;
    RandomAccessFile programmeFile= null;
    programmeInfo.clear();

    programmeTableView.setItems(programmeInfo);
    try{
        try{
        studentFile= new RandomAccessFile(new File("student.hai"),"r");
                studentFile.seek((Integer.parseInt(studentIDField.getText()) - 1) * student.getRecordSize());
                studentId= studentFile.readInt();
                firstName = studentFile.readUTF();
                lastName= studentFile.readUTF();
                enrollmentStatus= studentFile.readUTF();
                programmeCode= studentFile.readInt();
            try {
                try{
                programmeFile= new RandomAccessFile(new File("programme.hai"),"r");
                programmeFile.seek((programmeCode-1)*programme.sizeOfRecord());
                code=programmeFile.readInt();
                programmeName=programmeFile.readUTF();
                maxNumberofCourses = programmeFile.readInt();
                award = programmeFile.readUTF();
                accreditation = programmeFile.readUTF();
                if (code != 1) {
                    programme = new Programme(code, programmeName, maxNumberofCourses, award, accreditation);
                    programmeInfo.add(new Programme(programme));
                    programmeTableView.setItems(getProgrammeInfo());

                    programmeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setProgramme(newValue));
            }
                }catch (EOFException exc){
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Programme code exceeded file limit");
                    alert.showAndWait();
                }
                }catch (IOException exc){
                exc.printStackTrace();
            }finally {
                try {
                    programmeFile.close();
                }catch (IOException exc){
                    exc.printStackTrace();
                }
            }
        }catch (EOFException exc){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ID number exceeded file limit");
            alert.showAndWait();
        }
    }catch (IOException exc){
        exc.printStackTrace();
    }finally {
        try {
            studentFile.close();
        }catch (IOException exc){
            //
        }

    }

}

}

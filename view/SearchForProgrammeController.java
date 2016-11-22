package HAI.view;

import HAI.model.Programme;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Created by Khalin on 11/1/2016.
 */
public class SearchForProgrammeController {
    @FXML
    private Button searchButton;
    @FXML
    private TextField programmeCodeField;
    private Stage searchProgramme = new Stage();

    public void setSearchProgramme(Stage searchProgramme) {
        this.searchProgramme = searchProgramme;
    }

    @FXML
    public Programme handleSearch() {
        int code;
        Programme programme = new Programme();
        int programmeCode, numberOfCourses;
        String programmeName, award, accreditation;

        try {
            code = Integer.parseInt(programmeCodeField.getText());
            RandomAccessFile search=null;
            try {
                    try{
                search = new RandomAccessFile(new File("programme.hai"), "r");
                search.seek((code - 1) * programme.sizeOfRecord());
                programmeCode = search.readInt();
                if (programmeCode == 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Record doesn't exist");
                    alert.showAndWait();
                } else {
                    programmeName = search.readUTF();
                    numberOfCourses = search.readInt();
                    award = search.readUTF();
                    accreditation = search.readUTF();

                    programme = new Programme(programmeCode, programmeName, numberOfCourses, award, accreditation);
                    searchProgramme.close();
                    search.close();
                    return programme;
                }
                    }catch (EOFException exc){
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Programme code exceeded file limit");
                        alert.showAndWait();
                    }
            } catch (IOException exc) {
                exc.printStackTrace();
            }finally {
                try {
                    search.close();
                }catch (IOException exc){

                }

            }
        }catch (NumberFormatException exc){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please input programme Code");
            alert.showAndWait();
        }

            return programme;


    }
}


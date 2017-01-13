package HAI.view;

import HAI.model.Staff;
import HAI.utilities.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;


/**
 * Created by Khalin on 10/22/2016.
 */
public class NewStaffController {
    @FXML
    private TextField staffId;
    @FXML
    private TextField staffFirstName;
    @FXML
    private TextField staffLastName;
    @FXML
    private TextField faculty;
    @FXML
    private TextField department;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button addStaff;



    private Stage newStaff= new Stage();
    public void setNewStaff(Stage newStaff){
        this.newStaff= newStaff;
    }

    @FXML
    private void handleAddStaff() {
    Staff staff = new Staff();
        RandomAccessFile staffFile=null;
    if (isInputValid() && doesRecordExist()) {
        staff.setId(Integer.parseInt(staffId.getText()));
        staff.setFirstName(staffFirstName.getText());
        staff.setLastName(staffLastName.getText());
        staff.setFaculty(faculty.getText());
        staff.setDepartment(department.getText());
        staff.setDateofEmployment(new Date(datePicker.getValue().getDayOfMonth(),datePicker.getValue().getMonthValue(),datePicker.getValue().getYear()));
        try {
            try {
            staffFile = new RandomAccessFile(new File("staff.hai"), "rw");
            staffFile.seek((Integer.parseInt(staffId.getText()) - 1) * staff.getRecordSize());
            staffFile.writeInt(Integer.parseInt(staffId.getText()));
            staffFile.writeUTF(staffFirstName.getText());
            staffFile.writeUTF(staffLastName.getText());
            staffFile.writeUTF(faculty.getText());
            staffFile.writeUTF(department.getText());
            staffFile.writeInt(staff.getDateofEmployment().getDay());
            staffFile.writeInt(staff.getDateofEmployment().getMonth());
            staffFile.writeInt(staff.getDateofEmployment().getYear());
            }catch (EOFException exc){
Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ID exceeded file limit");
                alert.showAndWait();
            }
        }catch (IOException exc){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File failed to open");
            alert.setContentText("File Failure");
           alert.showAndWait();
        }finally {
            try{
                staffFile.close();
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
        newStaff.close();
    }
}
private boolean doesRecordExist(){
    int id,idStaff;
    Staff staff= new Staff();
    RandomAccessFile staffFile=null;
        id=Integer.parseInt(staffId.getText());
    try{
        staffFile = new RandomAccessFile(new File("staff.hai"),"r");
        try {
            staffFile.seek((id - 1) * staff.getRecordSize());
            idStaff= staffFile.readInt();

        if(idStaff!=1){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Record already exists");
            alert.setContentText("Record already exists.");
            alert.initOwner(newStaff);
            alert.showAndWait();
            staffFile.close();
            return false;
        }else {
            return true;
        }
        }catch (EOFException exc){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ID exceeded file limit");
            alert.showAndWait();
        }
    }catch (IOException exc){
        exc.printStackTrace();
    }finally {
        try{
            staffFile.close();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }
    return false;
}
private boolean isInputValid(){
    String errorMessage="";

    if(staffId.getText()==""||staffId.getText().length()==0){
        errorMessage="Invalid ID entered";
    }else {
        try{
            Integer.parseInt(staffId.getText());

        }catch(NumberFormatException inputValid){
            errorMessage+="Invalid number entered. Please Enter an integer value";
            staffId.clear();
        }
    }
    if(staffFirstName.getText()==""||staffFirstName.getText().length()==0){
        errorMessage+="Invalid first Name\n";
    }
    if(staffLastName.getText()==""||staffLastName.getText().length()==0){
        errorMessage+="Invalid last Name\n";
    }
    if(faculty.getText()==""||faculty.getText().length()==0){
        errorMessage+="Invalid Faculty\n";
    }
    if(department.getText()==""||department.getText().length()==0){
        errorMessage+="Invalid department\n";
    }
    if(datePicker.getValue()==null){
        errorMessage+="Please pick a date\n";
    }
    if(errorMessage.length()==0){
    return true;
    }else {
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid field");
        alert.initOwner(newStaff);
        alert.setContentText(errorMessage);
        alert.showAndWait();
        return false;
    }

}
}

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

    private Student student;



    public ObservableList<Student>getStudentInfo(){
        return studentInfo;
    }



    @FXML void handleOk(){
        int id,day,month,year,code;
        int programmeCode= Integer.parseInt(programmeCodeField.getText());
        String firstName,lastName,areaCode,exchange,line,street,parish,password,status;
        Student student= new Student(0,"","","",0,new Address(),new PhoneNumber(),new Date());
        studentInfo.clear();
        studentTableView.setItems(getStudentInfo());
        try {
            RandomAccessFile studentFile = new RandomAccessFile(new File("student.hai"), "rw");
            try {
                for (int count = 1600; count < 1630; count++) {
                    studentFile.seek((count - 1) * student.getRecordSize());
                    id = studentFile.readInt();
                    firstName = studentFile.readUTF();
                    lastName = studentFile.readUTF();
                    status = studentFile.readUTF();
                    code = studentFile.readInt();
                    day = studentFile.readInt();
                    month = studentFile.readInt();
                    year = studentFile.readInt();
                    areaCode = studentFile.readUTF();
                    exchange = studentFile.readUTF();
                    line = studentFile.readUTF();
                    street = studentFile.readUTF();
                    parish = studentFile.readUTF();

                    if (programmeCode == code&&id!=1) {
                        student = new Student(id, firstName, lastName, status, code, new Address(street, parish), new PhoneNumber(areaCode, exchange, line), new Date(day, month, year));
                         studentInfo.add(new Student(student));
                        studentTableView.setItems(getStudentInfo());
                    }
                }

            } catch (EOFException eofc) {

            } finally {
                studentFile.close();
            }
        }catch (IOException exc){
            exc.printStackTrace();
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

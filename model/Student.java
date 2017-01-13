package HAI.model;

import HAI.utilities.Address;
import HAI.utilities.Date;
import HAI.utilities.PhoneNumber;
import HAI.view.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by Khalin on 10/22/2016.
 */
public class Student extends Person {
    private Address studentAddress;
    private Date dateEnrolled;
    private int programmeCode;
    private String enrollmentStatus;
    private PhoneNumber contactNumber;
    private String password;

    public Student() {
        super();
        setStudentAddress(new Address());
        setContactNumber(new PhoneNumber());
        setDateEnrolled(new Date());
        setEnrollmentStatus("0");
        setProgrammeCode(1000);
        setPassword("123456");
    }

    public Student(int inId, String inFirstName, String inLastName, String inEnrollmentStatus, int inProgrammeCode, Address inStudentAddress, PhoneNumber inContactNumber, Date inDateEnrolled) {
        super(inId, inFirstName, inLastName);
        setProgrammeCode(inProgrammeCode);
        setEnrollmentStatus(inEnrollmentStatus);
        setDateEnrolled(inDateEnrolled);
        setContactNumber(inContactNumber);
        setStudentAddress(inStudentAddress);
    }

    public Student(Student student) {
        super(student);
        setProgrammeCode(student.getProgrammeCode());
        setDateEnrolled(student.getDateEnrolled());
        setEnrollmentStatus(student.getEnrollmentStatus());
        setStudentAddress(student.getStudentAddress());
        setContactNumber(student.getContactNumber());
        setPassword(student.getPassword());
    }

    @Override
    public String toString() {
        String student = "";
        student += getId() + "\n";
        student += getFirstName() + "\n";
        student += getLastName() + "\n";
        student += getProgrammeCode() + "\n";
        student += getEnrollmentStatus() + "\n";

        student += getStudentAddress() + "\n";

        student += getContactNumber() + "\n";
        student += getDateEnrolled() + "\n";
        return student;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(Address studentAddress) {
        this.studentAddress = studentAddress;
    }

    public Date getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(Date dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public int getProgrammeCode() {
        return programmeCode;
    }

    public void setProgrammeCode(int programmeCode) {
        this.programmeCode = programmeCode;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public PhoneNumber getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(PhoneNumber contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public void initialize() {
        Connection connection = null;
        Statement statement = null;
        try {
            try {
                String studentTable = "CREATE TABLE uniStudent" +
                        "(studentId INTEGER PRIMARY KEY," +
                        "firstName VARCHAR(255)," +
                        "lastName VARCHAR(255)," +
                        "address VARCHAR(255), " +
                        "dateEnrolled VARCHAR(30) , " +
                        "programmeCode  INTEGER, " +
                        "enrollmentStatus  VARCHAR(15)," +
                        "contactNumber  VARCHAR(25)," +
                        "password VARCHAR(30))";

                String host = "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(host);
                statement = connection.createStatement();

                statement.execute(studentTable);
                statement.execute("ALTER TABLE uniStudent"+
                " ADD CONSTRAINT fk_programmeCode"+
                      " FOREIGN KEY(programmeCode)"+" REFERENCES Programme(programmeCode)");
               statement.execute("ALTER TABLE uniStudent"+ " Drop COLUMN password");

            }catch (SQLException sql){
                System.out.println(sql.getErrorCode());
                sql.printStackTrace();
            }
            } catch (Exception e) {
            System.out.println(e.getCause());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something happened...");
            alert.showAndWait();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException exc) {
                //
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exc) {
                //
            }
        }
    }



    public void newStudentForm() {
        Stage newStudent = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/NewStudent.fxml"));
            AnchorPane form = loader.load();
            newStudent.setTitle("Student Information");
            newStudent.setScene(new Scene(form));
            newStudent.initModality(Modality.APPLICATION_MODAL);

            NewStudentController controller = loader.getController();
            controller.setNewStudent(newStudent);

            newStudent.showAndWait();
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }

    public void studentMenu(int id) {
        Stage menu = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/StudentMenu.fxml"));
            AnchorPane studentMenu = loader.load();
            menu.setTitle("Student Menu");
            menu.setScene(new Scene(studentMenu));

            StudentMenuController controller = loader.getController();
            controller.setStudentMenu(menu);
            controller.setId(id);
            menu.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void generateProgressReport(int id) {
        Stage progressReport = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GenerateProgressReport.fxml"));
            AnchorPane report = loader.load();

            progressReport.setTitle("Progress Report");
            progressReport.setScene(new Scene(report));
            ProgressReportController controller= loader.getController();
            progressReport.initStyle(StageStyle.UTILITY);
            controller.setId(id);
            progressReport.showAndWait();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
@Override
    public void initializeLogin() {
        RandomAccessFile loginFile = null;
        try {
            loginFile = new RandomAccessFile(new File("studentLogin.hai"), "rw");
            for (int count = 1600; count < 1620; count++) {
                loginFile.seek((count - 1) * (4 + (25 * 2)));
                loginFile.writeInt(count);
                loginFile.writeUTF(getPassword());
            }
        } catch (IOException exc) {
            //
        } finally {
            try {
                loginFile.close();
            } catch (IOException exc) {
                //
            }
        }
    }
    public void studentAddCourse(int id){
        Stage studentAdd=new Stage();
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/StudentAddCourse.fxml"));
            AnchorPane addCourse= loader.load();
            studentAdd.setTitle("Add a Course");
            studentAdd.setScene(new Scene(addCourse));
            StudentAddCourseController controller= loader.getController();
            controller.setId(id);
            studentAdd.showAndWait();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }
    public void generateFees(int id){
        Stage generateFees=new Stage();
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/GenerateFees.fxml"));
            AnchorPane fees=loader.load();
            generateFees.setTitle("Generated Fees for semester");
            generateFees.setScene(new Scene(fees));
            generateFees.initModality(Modality.APPLICATION_MODAL);
            GenerateFeesController controller= loader.getController();
            controller.setId(id);
            generateFees.showAndWait();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }


/**
 * try {
 RandomAccessFile studentFile = new RandomAccessFile(new File("student.hai"), "rw");
 for (int count = 1600; count <= 1620; count++) {
 studentFile.seek((count - 1) * getRecordSize());
 studentFile.writeInt(getId());
 studentFile.writeUTF(getFirstName());
 studentFile.writeUTF(getLastName());
 studentFile.writeUTF(getEnrollmentStatus());
 studentFile.writeInt(getProgrammeCode());
 studentFile.writeInt(getDateEnrolled().getDay());
 studentFile.writeInt(getDateEnrolled().getDay());
 studentFile.writeInt(getDateEnrolled().getDay());
 studentFile.writeUTF(getContactNumber().getAreaCode());
 studentFile.writeUTF(getContactNumber().getExchange());
 studentFile.writeUTF(getContactNumber().getLine());
 studentFile.writeUTF(getStudentAddress().getStreet());
 studentFile.writeUTF(getStudentAddress().getParish());

 }
 studentFile.close();
 } catch (IOException exc) {
 exc.printStackTrace();
 }
 }
*/
 public long getRecordSize() {
 return (long) (4 + 4 + 4 + 4 + 4 + ((25 + 25 + 25 + 25 + 25 + 25 + 25 + 25) * 2));
 }
}




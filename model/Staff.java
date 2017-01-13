package HAI.model;

import HAI.MainApp;
import HAI.utilities.Date;
import HAI.view.InitialController;
import HAI.view.NewStaffController;
import HAI.view.StaffMenuController;
import com.sun.xml.internal.ws.developer.Serialization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;


/**
 * Created by Khalin on 10/22/2016.
 */
public class Staff extends Person {

	private String department;
    private String faculty;
    private Date dateofEmployment;
    private String staffPassword;

    public Staff(){
        super();
        setDepartment("Some department");
        setFaculty("Some faculty");
        setDateofEmployment(new Date());
        setStaffPassword("some password");
    }
    public Staff(int inId,String inFirstName, String inLastName,String inDepartment, String inFaculty,String inStaffPassword, Date inDateofEmployment){
        super(inId,inFirstName,inLastName);
        setDepartment(inDepartment);
        setFaculty(inFaculty);
        setStaffPassword(inStaffPassword);
        setDateofEmployment(inDateofEmployment);
    }
    public Staff(Staff staff){
        super(staff);
        setFaculty(staff.getFaculty());
        setDepartment(staff.getDepartment());
      setDateofEmployment(staff.getDateofEmployment());
        setStaffPassword(staff.getStaffPassword());
    }
@Override
public String toString(){
    String string="";
    string+="Staff ID: "+getId()+"\n ";
    string+="Staff First Name: "+getFirstName()+"\n";
    string+="Staff Last Name: "+getLastName()+"\n";
    string+="Staff Faculty: "+getFaculty()+"\n";
    string+="Staff Department: "+getDepartment()+"\n";
    string+="Employment Date: "+getDateofEmployment()+"\n";
    return string;
}


    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Date getDateofEmployment() {
        return dateofEmployment;
    }

    public void setDateofEmployment(Date dateofEmployment) {
        this.dateofEmployment = dateofEmployment;
    }
   @Override
    public void initialize(){
        RandomAccessFile staffFile= null;
        try {
            staffFile = new RandomAccessFile(new File("staff.hai"), "rw");
            for(int count=2000;count<2030;count++){
                staffFile.seek((count-1)*getRecordSize());
                staffFile.writeInt(getId());
                staffFile.writeUTF(getFirstName());
                staffFile.writeUTF(getLastName());
                staffFile.writeUTF(getFaculty());
                staffFile.writeUTF(getDepartment());
                staffFile.writeInt(getDateofEmployment().getDay());
                staffFile.writeInt(getDateofEmployment().getMonth());
                staffFile.writeInt(getDateofEmployment().getYear());

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

    }

    public long getRecordSize(){
        return (long) (4+4+4+4+((25+25+25+25)*2));
    }

    public void newStaffForm(){
        Stage staffForm= new Stage();
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/NewStaff.fxml"));
            AnchorPane form= loader.load();
            staffForm.setTitle("New Staff Member");
            staffForm.setScene(new Scene(form));
            staffForm.initModality(Modality.APPLICATION_MODAL);

            NewStaffController controller= loader.getController();
            controller.setNewStaff(staffForm);
            staffForm.showAndWait();
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }
public void staffMenu(){
    Stage menu= new Stage();
    try{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/StaffMenu.fxml"));
        AnchorPane staffMenu= loader.load();
        menu.setTitle("Staff Menu");
        menu.setScene(new Scene(staffMenu));
        StaffMenuController controller= loader.getController();
        controller.setStaffMenu(menu);

        menu.show();
    }catch(IOException exc){
        exc.printStackTrace();
    }
}
@Override
    public void initializeLogin(){
        RandomAccessFile loginFile= null;
        try {
            loginFile= new RandomAccessFile(new File("staffLogin.hai"),"rw");
            for(int count=2000;count<2030;count++){
                loginFile.seek((count-1)*(4+(25*2)));
                loginFile.writeInt(count);
                loginFile.writeUTF(getStaffPassword());
            }
        }catch (IOException exc){
            //
        }finally {
            try {
                loginFile.close();
            }catch (IOException exc){
                //
            }
        }
        }
}

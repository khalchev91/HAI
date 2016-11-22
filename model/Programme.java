package HAI.model;

import HAI.view.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Khalin on 10/22/2016.
 */
public class Programme {
    private int programmeCode;
    private String programmeName;
    private int maxNumberOfCourses;
    private String award;
    private String accreditation;

    public Programme() {
        setProgrammeCode(1);
        setProgrammeName("Some programme");
        setMaxNumberOfCourses(1);
        setAward("Some Award");
        setAccreditation("Some accreditation");
    }

    public Programme(int inProgrammeCode, String inProgrammeName, int inMaxNumberOfCourses, String inAward, String inAccreditation) {
        setProgrammeCode(inProgrammeCode);
        setProgrammeName(inProgrammeName);
        setMaxNumberOfCourses(inMaxNumberOfCourses);
        setAward(inAward);
        setAccreditation(inAccreditation);
    }

    public Programme(Programme programme) {
        setProgrammeCode(programme.getProgrammeCode());
        setProgrammeName(programme.getProgrammeName());
        setMaxNumberOfCourses(programme.getMaxNumberOfCourses());
        setAward(programme.getAward());
        setAccreditation(programme.getAccreditation());
    }

    public String toString() {
        String string = "";
        string += "Programme Code: " + getProgrammeCode() + "\n";
        string += "Programme Name: " + getProgrammeName() + "\n";
        string += "Maximum Number of Courses: " + getMaxNumberOfCourses() + "\n";
        string += "Programme Award: " + getAward() + "\n";
        string += "Programme Accreditation: " + getAccreditation() + "\n";
        return string;
    }

    public int getProgrammeCode() {
        return programmeCode;
    }

    public void setProgrammeCode(int programmeCode) {
        this.programmeCode = programmeCode;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public int getMaxNumberOfCourses() {
        return maxNumberOfCourses;
    }

    public void setMaxNumberOfCourses(int maxNumberOfCourses) {
        this.maxNumberOfCourses = maxNumberOfCourses;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    public void initialize(){
        try {
            RandomAccessFile programmeFile = new RandomAccessFile(new File("programme.hai"), "rw");
            for(int count=1000;count<1020;count++){
                programmeFile.seek((count-1)*sizeOfRecord());
                programmeFile.writeInt(getProgrammeCode());
                programmeFile.writeUTF(getProgrammeName());
                programmeFile.writeInt(getMaxNumberOfCourses());
                programmeFile.writeUTF(getAward());
                programmeFile.writeUTF(getAccreditation());
            }
            programmeFile.close();
        }catch(IOException exc){


        }

    }
    public long sizeOfRecord(){
        return (long)(4+4+((25+25+25)*2));
    }

public int programmeForm(){
    Stage newProgramme= new Stage();
    int code=0;
    try{

        FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/NewProgramme.fxml"));
        AnchorPane form= loader.load();
        newProgramme.setTitle("New Programme");
        newProgramme.setScene(new Scene(form));
        newProgramme.initModality(Modality.APPLICATION_MODAL);

        NewProgrammeController controller= loader.getController();
        controller.setProgrammeForm(newProgramme);

        newProgramme.showAndWait();
        code= controller.handleNewProgramme();
    }catch(IOException exc){
        exc.printStackTrace();
    }
    return code;
}
public int editProgramme(Programme programme){
    Stage edit= new Stage();
    int code=0;
    try{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/EditProgrammeDetails.fxml"));
        AnchorPane form= loader.load();
        edit.setTitle("Edit Programme Details");
        edit.setScene(new Scene(form));
        edit.initModality(Modality.APPLICATION_MODAL);
        EditProgrammeController controller= loader.getController();
        controller.setEditForm(edit);
        controller.setProgramme(programme);


        edit.showAndWait();
        code= controller.handleSave();
    }catch (IOException exc){
        exc.printStackTrace();
    }
    return code;
}
public Programme search(){
    Programme programme= null;
    Stage searchProgramme=new Stage();
    try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SearchforProgramme.fxml"));
        AnchorPane search= loader.load();
        searchProgramme.setTitle("Search for Programme");
        searchProgramme.setScene(new Scene(search));
        searchProgramme.initModality(Modality.APPLICATION_MODAL);
        searchProgramme.initStyle(StageStyle.UNDECORATED);

        SearchForProgrammeController controller=loader.getController();
        controller.setSearchProgramme(searchProgramme);

        searchProgramme.showAndWait();
        programme= new Programme(controller.handleSearch());

    }catch (IOException exc){
        exc.printStackTrace();
    }
    return programme;
}
public void generateStudentList(){
    Stage studentList= new Stage();
    try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GenerateStudentList.fxml"));
        AnchorPane list= loader.load();
        studentList.setTitle("Student List");
        studentList.setScene(new Scene(list));
        GenerateStudentListController controller= loader.getController();
        controller.setGenerateStudentList(studentList);

        studentList.show();

    }catch (IOException exc){
        exc.printStackTrace();
    }
}
public void programmeDetails(){
    Stage viewProgrammeDetails= new Stage();
    try{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/ProgrammeDetails.fxml"));
            AnchorPane details= loader.load();

        viewProgrammeDetails.setTitle("Programme Details");
        ViewProgrammeDetailsController controller= loader.getController();
        controller.getProgrammeInfo();
        viewProgrammeDetails.setScene(new Scene(details));
        viewProgrammeDetails.showAndWait();
    }catch (IOException exc){
        exc.printStackTrace();
    }
}
}


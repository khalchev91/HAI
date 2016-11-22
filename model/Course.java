package HAI.model;

import HAI.view.AddCourseToProgrammeController;
import HAI.view.NewCourseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Khalin on 10/22/2016.
 */
public class Course {
    private int courseCode;
    private String courseName;
    private String description;
    private int credit;
    private int prerequisite;
    private float costPerCredit;
    private float courseCost;

    public Course(){
        setCourseCode(1);
        setCourseName("Some course");
        setDescription("description");
        setCredit(1);
        setPrerequisite(1);
        setCostPerCredit(0);
        setCourseCost(0);
    }
    public Course(int inCourseCode, String inCourseName,String inDescription,int inCredit,int inPrerequisite,float inCostPerCredit,float inCourseCost){
        setCourseCode(inCourseCode);
        setCourseName(inCourseName);
        setDescription(inDescription);
        setCredit(inCredit);
        setPrerequisite(inPrerequisite);
        setCostPerCredit(inCostPerCredit);
        setCourseCost(inCourseCost);
    }
public Course(Course course){
    setCourseCode(course.getCourseCode());
    setCourseName(course.getCourseName());
    setDescription(course.getDescription());
    setCredit(course.getCredit());
    setPrerequisite(course.getPrerequisite());
    setCostPerCredit(course.getCostPerCredit());
    setCourseCost(course.getCourseCost());
    }

public String toString(){
    String string="";
    string+="Course Code: "+getCourseCode()+"\n";
    string+="Course Name: "+getCourseName()+"\n";
    string+="Description: "+getDescription()+"\n";
    string+="Credit: "+getCredit()+"\n";
    string+="Prerequisite: "+getPrerequisite()+"\n";
    string+="Cost Per Credit: "+getCostPerCredit()+"\n";
    string+="Course Cost:"+getCourseCost()+"\n";
    return string;
}

    public float getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(float courseCost) {
        this.courseCost = courseCost;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(int prerequisite) {
        this.prerequisite = prerequisite;
    }

    public float getCostPerCredit() {
        return costPerCredit;
    }

    public void setCostPerCredit(float costPerCredit) {
        this.costPerCredit = costPerCredit;
    }


    public void courseForm(){
    Stage newCourse= new Stage();
    try{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/NewCourse.fxml"));
        AnchorPane form= loader.load();
        newCourse.setTitle("New Course");
        newCourse.initModality(Modality.APPLICATION_MODAL);
        newCourse.setScene(new Scene(form));

        NewCourseController controller= loader.getController();
        controller.setCourseForm(newCourse);
        newCourse.showAndWait();
    }catch (IOException exc){
        exc.printStackTrace();
    }
}
public void initialize(){
    RandomAccessFile courseFile= null;
    try{
courseFile= new RandomAccessFile(new File("course.hai"),"rw");
        for(int count=2500; count<2530; count++){
            courseFile.seek((count-1)*getRecordSize());
            courseFile.writeInt(getCourseCode());
            courseFile.writeUTF(getCourseName());
            courseFile.writeUTF(getDescription());
            courseFile.writeInt(getCredit());
            courseFile.writeInt(getPrerequisite());
            courseFile.writeFloat(getCostPerCredit());
            courseFile.writeFloat(getCourseCost());
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
}
    public long getRecordSize(){
        return (long)(4+4+4+4+4+((25+25)*2));
    }
public void addCourse(int code){
    Stage addCourse= new Stage();
    try{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../view/AddCoursetoProgramme.fxml"));
        AnchorPane course= loader.load();
        addCourse.setTitle("Add Course");
        addCourse.setScene(new Scene(course));
        addCourse.initStyle(StageStyle.UTILITY);
        AddCourseToProgrammeController controller=loader.getController();
        addCourse.showAndWait();
        controller.setProgrammeCode(code);
    }catch (IOException exc){
        exc.printStackTrace();
    }
}
public void createFile(){
    FileWriter courseProgramme= null;
    FileWriter courseStudent=null;
    try {
        courseProgramme= new FileWriter("course-programme.hai",true);
    }catch (IOException exc){
        //
    }finally {
        try {
            courseProgramme.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    try {
        courseStudent= new FileWriter("student-course.hai",true);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            courseStudent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

}

package HAI.view;

import HAI.model.Course;
import HAI.model.Programme;
import HAI.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Khalin on 11/8/2016.
 */
public class AddCourseToProgrammeController {
    private ObservableList<Course>addCourse= FXCollections.observableArrayList();
    @FXML private TableView<Course> courseTableView;
    @FXML private TableColumn<Course, Integer> courseColumn;
    @FXML private TableColumn<Course,String> courseNameColumn;

    @FXML private Button addToProgramme;
private int programmeCode;

    public void setProgrammeCode(int programmeCode) {
        this.programmeCode = programmeCode;
    }

    public int getProgrammeCode() {
        return programmeCode;
    }

    public ObservableList<Course> getAddCourse() {
        return addCourse;
    }

    @FXML private void initialize() {
        RandomAccessFile courseFile=null;
        Course[] course= new Course[30];
        int courseCode,credit,preRequisite,studentId;
        int recordNum=0;
        String courseName,description;
        float costPerCredit,courseCost;
        try{
            courseFile= new RandomAccessFile(new File("course.hai"),"r");
            try {
                for (int count = 2500; count < 2530; count++) {
                    course[recordNum]=new Course();
                    courseFile.seek((count - 1) * course[recordNum].getRecordSize());
                    courseCode = courseFile.readInt();
                    courseName = courseFile.readUTF();
                    description = courseFile.readUTF();
                    credit = courseFile.readInt();
                    preRequisite = courseFile.readInt();
                    costPerCredit = courseFile.readFloat();
                    courseCost=courseFile.readFloat();
                    if(courseCode!=1){
                        course[recordNum]= new Course(courseCode,courseName,description,credit,preRequisite,costPerCredit,courseCost);
                        addCourse.add(course[recordNum]);
                        courseTableView.setItems(getAddCourse());
                    }
                    recordNum++;
                    if(recordNum>=30){
                        break;
                    }
                }
            }catch (EOFException eof){
                /**/
            }
        }catch (IOException exc){
            exc.printStackTrace();
        }


    }
    @FXML void handleAdd(){
        Course course=courseTableView.getSelectionModel().getSelectedItem();
        FileWriter courseFile= null;

        if(course!=null && checkIfExist(programmeCode,course.getCourseCode())){
            try{
                courseFile= new FileWriter("course-programme.hai",true);
                courseFile.write(course.getCourseCode()+"\t"+programmeCode+"\n");
            }catch (IOException exc){
                //
            }finally {
                try{
                    courseFile.close();
                }catch (IOException exc){
                    //
                }
            }
        }
    }

    public boolean checkIfExist(int programmeCodeCheck,int courseCodeCheck){
        int courseCode,programmeCode;
        Scanner seqCourse= null;
        try {
            seqCourse= new Scanner(new File("course-programme.hai"));
            while (seqCourse.hasNext()){
                courseCode=seqCourse.nextInt();
                programmeCode= seqCourse.nextInt();
                if(courseCode==courseCodeCheck&&programmeCodeCheck==programmeCode){
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This course already exists in that programme");
                    alert.showAndWait();
                    return false;
                }
            }
        }catch (FileNotFoundException fnfex){
            //
        }finally {
            seqCourse.close();
        }
        return true;
    }


}

package HAI.view;

import HAI.model.Course;
import HAI.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Khalin on 11/9/2016.
 */
public class ProgressReportController {
    ObservableList<Course>courseData= FXCollections.observableArrayList();
    @FXML TableView<Course> courseTableView;
    @FXML TableColumn<Course,Integer>courseIdColumn;
    @FXML TableColumn<Course,String>courseNameColumn;
    @FXML TableColumn<Course,Integer>courseCreditColumn;
    @FXML Button searchStudentButton;
    @FXML TextField studentIdField;

    public ObservableList<Course> getCourseData() {
        return courseData;
    }
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @FXML private void progressReport() {
        String courseName, description;
        int courseCode, programmeCode, credit, preRequisite;
        float costPerCredit, courseCost;
        int seqId, seqCode;
        int studentId;
        RandomAccessFile courseFile = null;
        Scanner searchSeq = null;
        Course course = new Course();
        Student student = new Student();
        courseData.clear();
        courseTableView.setItems(getCourseData());
            try {
                searchSeq = new Scanner(new File("student-course.hai"));
                while (searchSeq.hasNext()) {
                    seqId = searchSeq.nextInt();
                    seqCode = searchSeq.nextInt();
                    if (seqId == getId()) {
                        try {
                            courseFile = new RandomAccessFile(new File("course.hai"), "r");
                            try {
                                courseFile.seek((seqCode - 1) * course.getRecordSize());
                                courseCode = courseFile.readInt();
                                courseName = courseFile.readUTF();
                                description = courseFile.readUTF();
                                credit = courseFile.readInt();
                                preRequisite = courseFile.readInt();
                                costPerCredit = courseFile.readFloat();
                                courseCost = courseFile.readFloat();
                                course = new Course(courseCode, courseName, description, credit, preRequisite, costPerCredit, courseCost);
                                courseData.add(course);
                                courseTableView.setItems(getCourseData());
                            } catch (IOException exc) {
                                //
                            } finally {
                                try {
                                    courseFile.close();
                                } catch (IOException exc) {
                                    //
                                }
                            }
                        } catch (FileNotFoundException e) {
                            //
                        }
                    }
                }
                if (!(searchSeq.hasNext()) && courseData.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Record not found");
                    alert.showAndWait();
                    courseData.clear();
                }
            } catch (IOException exc) {
                //
            } finally {
                searchSeq.close();
            }
    }


}

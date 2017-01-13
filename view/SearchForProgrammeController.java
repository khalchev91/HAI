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
import java.sql.*;
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
        PreparedStatement statement= null;
        Connection connection= null;

        Programme programme = new Programme();
        try{
            code= Integer.parseInt(programmeCodeField.getText());
            try{
                try{
                    String host= "jdbc:sqlserver://KHALCHEV97;databaseName=HAI;integratedSecurity=true";
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    String search="IF EXISTS(SELECT *FROM programme"+" WHERE programmeCode = ?)"+"SELECT *FROM programme"+" WHERE programmeCode = ?"+" ELSE"+" SELECT NULL";
                    connection= DriverManager.getConnection(host);
                    statement= connection.prepareStatement(search);
                    statement.setInt(1,code);
                    statement.setInt(2,code);
                    ResultSet resultSet= statement.executeQuery();
                    while (resultSet.next()){
                        programme.setProgrammeCode(resultSet.getInt("ProgrammeCode"));
                        programme.setProgrammeName(resultSet.getString("programmeName"));
                        programme.setMaxNumberOfCourses(resultSet.getInt("maximumNumberOfCourses"));
                        programme.setAward(resultSet.getString("Award"));
                        programme.setAccreditation(resultSet.getString("Accreditation"));
                    }

                    if(resultSet==null){
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("THIS PROGRAMME DOESN'T EXIST");
                        alert.showAndWait();
                    }
                }catch (SQLException sql){
                    System.out.println(sql.getErrorCode());
                    sql.printStackTrace();
                }
            }catch (Exception e){

            }

        }catch (NumberFormatException exc){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please input programme Code");
            alert.showAndWait();
        }
            searchProgramme.close();
            return programme;


    }
}


package HAI.model;

import HAI.view.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Khalin on 10/22/2016.
 */
public  class Person {

    protected int id;
    protected String firstName;
    protected String lastName;

    public Person(){
        setId(1);
        setFirstName("Jack");
        setLastName("Brown");
    }
public Person(int inId,String inFirstName, String inLastName){
    setId(inId);
    setFirstName(inFirstName);
    setLastName(inLastName);
}
public Person(Person person){
    setId(person.getId());
    setFirstName(person.getFirstName());
    setLastName(person.getLastName());
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }





}

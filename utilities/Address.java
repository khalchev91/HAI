package HAI.utilities;

import java.io.Serializable;

/**
 * Created by Khalin on 10/22/2016.
 */
public class Address {
    private String street;
    private String parish;

public Address(){
    setStreet("1 Jack's hill road");
    setParish("St.Andrew");
}
public Address(String inStreet,String inParish){
    setStreet(inStreet);
    setParish(inParish);
}
public Address(Address address){
    setStreet(address.getStreet());
    setParish(address.getParish());
}
    public String toString(){
        String string="";
        string+=getStreet()+"\n";
        string+=getParish()+"\n";
        return string;
    }
    public Address toAddress(String address){
        Address address1= new Address();
        String[] newLine=address.split("\n");
        address1.setStreet(newLine[0]);
        address1.setParish(newLine[1]);
        return address1;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street= street;
    }


    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }
}

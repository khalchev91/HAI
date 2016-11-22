package HAI.utilities;

import java.io.Serializable;

/**
 * Created by Khalin on 10/22/2016.
 */
public class PhoneNumber {
    private String areaCode;
    private String exchange;
    private String line;

    public PhoneNumber(){
        setAreaCode("000");
        setExchange("000");
        setLine("0000");
    }
public PhoneNumber(String inAreaCode,String inExchange, String inLine){
  setAreaCode(inAreaCode);
    setExchange(inExchange);
    setLine(inLine);
}
public PhoneNumber(PhoneNumber phoneNumber){
    setAreaCode(phoneNumber.getAreaCode());
    setExchange(phoneNumber.getExchange());
    setLine(phoneNumber.getLine());
}
public String toString(){
    String string="";
    string+="("+getAreaCode()+")";
    string+=getExchange()+"-";
    string+=getLine();
    return string;
}
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}

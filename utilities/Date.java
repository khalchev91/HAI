package HAI.utilities;

import java.io.Serializable;

/**
 * Created by Khalin on 10/26/2016.
 */
public class Date {
    private int day;
    private int month;
    private int year;

   public Date(){
       setDay(31);
       setMonth(12);
       setYear(2000);
   }
   public Date(int inDay, int inMonth, int inYear){
       setDay(inDay);
       setMonth(inMonth);
       setYear(inYear);
   }
   public Date(Date date){
       setDay(date.getDay());
       setMonth(date.getMonth());
       setYear(date.getYear());
   }
   @Override
   public String toString(){
       String record="";
       record+=getDay()+"/";
       record+=getMonth()+"/";
       record+=getYear();
       return record;
   }
   public Date toDate(String day){
        Date date= new Date();
        String[]days=day.split("/");
        try {
            date.setDay(Integer.parseInt(days[0]));
            date.setMonth(Integer.parseInt(days[1]));
            date.setYear(Integer.parseInt(days[2]));
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }
        return date;
   }
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

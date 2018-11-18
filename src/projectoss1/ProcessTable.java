/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoss1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author lenovo Y700 ISK
 */
public class ProcessTable {
    
    
    private final StringProperty pid;
    private final StringProperty arrivalTime;
    private final StringProperty burstTime;
    private final StringProperty finishTime;
    private final StringProperty TA;
    private final StringProperty waitTime;
    private final StringProperty WTA;
    private final StringProperty priority;

    

    public ProcessTable (String pid , String arrivalTime , String burstTime , String finishTime , String TA , String waitTime , String WTA ,String priority ){
         this.pid = new SimpleStringProperty(pid);
         this.arrivalTime = new SimpleStringProperty(arrivalTime);
         this.burstTime = new SimpleStringProperty(burstTime);
         this.finishTime = new SimpleStringProperty(finishTime);
         this.TA= new SimpleStringProperty(TA);
         this.waitTime = new SimpleStringProperty(waitTime);
         this.WTA = new SimpleStringProperty(WTA);
         this.priority = new SimpleStringProperty(priority);
         
    }   
    
    public String getPid() {
        return pid.get();
    }
    
    public void setPid(String value){
        pid.set(value);
    }
    
     public String getPriority() {
        return priority.get();
    }
    
    public void setPriority(String value){
        priority.set(value);
    }


    public String getArrivalTime() {
        return arrivalTime.get();
    }

    public void setArrivalTime(String value){
        arrivalTime.set(value);
    }
    
    public String getBurstTime() {
        return burstTime.get();
    }
    
    public void setBurstTime(String value){
        burstTime.set(value);
    }

    public String getFinishTime() {
        return finishTime.get();
    }

    public void setFinishTime(String value){
        finishTime.set(value);
    }
    
    public String getTA() {
        return TA.get();
    }
    
    public void setTA(String value){
        TA.set(value);
    }

    public String getWaitTime() {
        return waitTime.get();
    }
    
     public void setWaitTime(String value){
        waitTime.set(value);
    }

    public String getWTA() {
        return WTA.get();
    }
    
     public void setWTA(String value){
        WTA.set(value);
    }
     public StringProperty pidProperty(){return pid;}
   public StringProperty arrivalTimeProperty(){return arrivalTime;}
   public StringProperty burstTimeProperty(){return burstTime;}
   public StringProperty finishTimeProperty(){return finishTime;}
   public StringProperty TAProperty(){return TA;}
   public StringProperty waitTimeProperty(){return waitTime;}
   public StringProperty WTAProperty(){return WTA;}
   
}
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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author lenovo Y700 ISK
 */
public class ProcessTable {
    
    
    private final SimpleIntegerProperty pid;
    private final SimpleIntegerProperty arrivalTime;
    private final SimpleIntegerProperty burstTime;
    private final SimpleIntegerProperty finishTime;
    private final SimpleIntegerProperty TA;
    private final SimpleIntegerProperty waitTime;
    private final SimpleIntegerProperty WTA;
    

    public ProcessTable (int pid , int arrivalTime , int burstTime , int finishTime , int TA , int waitTime , int WTA ){
         this.pid = new SimpleIntegerProperty(pid);
         this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
         this.burstTime = new SimpleIntegerProperty(burstTime);
         this.finishTime = new SimpleIntegerProperty(finishTime);
         this.TA= new SimpleIntegerProperty(TA);
         this.waitTime = new SimpleIntegerProperty(waitTime);
         this.WTA = new SimpleIntegerProperty(WTA);
         
    }   
    
    public SimpleIntegerProperty getPid() {
        return pid;
    }
    
    public void setPid(int value){
        pid.set(value);
    }

    public SimpleIntegerProperty getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int value){
        arrivalTime.set(value);
    }
    
    public SimpleIntegerProperty getBurstTime() {
        return burstTime;
    }
    
    public void setBurstTime(int value){
        burstTime.set(value);
    }

    public SimpleIntegerProperty getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int value){
        finishTime.set(value);
    }
    
    public SimpleIntegerProperty getTA() {
        return TA;
    }
    
    public void setTA(int value){
        TA.set(value);
    }

    public SimpleIntegerProperty getWaitTime() {
        return waitTime;
    }
    
     public void setWaitTime(int value){
        waitTime.set(value);
    }

    public SimpleIntegerProperty getWTA() {
        return WTA;
    }
    
     public void setWTA(int value){
        WTA.set(value);
    }
}
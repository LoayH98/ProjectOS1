/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoss1;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */


public class FXMLDocumentController implements Initializable {
    
      ArrayList<Process> processes;
    static ArrayList<Process> readyQueue;
    @FXML
    private Label label;
    
        @FXML
    private Button readFileBtn;

    @FXML
    private TextField fileNameTxt;
        
    @FXML
    private void readFileAction(ActionEvent event) {
        String FileName = fileNameTxt.getText() ;
       if(readFile(FileName))
           System.out.println("Done!");
       System.out.println(processes.get(0).pid);
    }
    
     public boolean readFile(String fileName){      // function to read processes data from the given input file, and save it in the arrayList processes
                                                   // returns true if data has read successfully, otherwise it returns false
        processes = new ArrayList<Process>();   
        try{
            File file = new File(fileName);
            Scanner input = new Scanner(file); 
            input.nextLine();                   // skipping first 2 lines in the input file
          
            
            while(input.hasNextLine()){         // read file line by line until its end
                
                String line = input.nextLine();         // get a line
                line = line.replaceAll("[ \t]", "");    // remove spaces and tabs
                String tokens[] = line.split("[,]");    // split it using the de-limitor "|"
                
                /* convert data from String to int and save it */
                int pid = Integer.parseInt(tokens[0]);          
                int arrivalTime = Integer.parseInt(tokens[1]);  
                int burstTime = Integer.parseInt(tokens[2]);
               // int repeat = Integer.parseInt(tokens[3]);
             //   int interval = Integer.parseInt(tokens[4]);
                int deadline = Integer.parseInt(tokens[3]);
                
                /* create a new process from the read data and add it immediately to the processes arrayList */
                processes.add(new Process(pid, arrivalTime, burstTime, deadline));
              }
            //, repeat, interval
        }
        catch(Exception ex){                // if any problem occured while oppening or reading the file
            JOptionPane.showMessageDialog(null, "Error openning file: " + ex.getMessage()); // show an error message
            return false;
        }
        
        return true;
    }
     
      public ArrayList<Integer> SJF(){
        int time = 0;                               // current time
        Process currentlyRunning = null;            // the process that is running currently, initially null
        readyQueue = new ArrayList<Process>();      // ready queue to add arrived processes that are ready to run
        ArrayList<Integer> ganttChart = new ArrayList<Integer>();   // Gantt Chart to show processes run-time
        
        while(!allProcessesFinished()){     // while there are still unfinished processes
            
            checkProcessesArrival(time);    // check if any process has arriced at current moment
            
            if(currentlyRunning == null){               // if no process is running currently 
                Process leastBurstTimeProcess = findLeastBurstTimeProcess();            // find the process with minimum burt time
                int leastBursTimetProcessIdx = readyQueue.indexOf(leastBurstTimeProcess);   // find its index in the readyQueue
                if (leastBursTimetProcessIdx != -1)                                     // and if it is in the queue
                    currentlyRunning = readyQueue.remove(leastBursTimetProcessIdx);     // remove it and run it
                
                if(currentlyRunning != null && currentlyRunning.startTime == -1)    // if there is a process has just started to run
                    currentlyRunning.startTime = time;                              // set its start time to current time
            }
            
            if(currentlyRunning != null && time == currentlyRunning.burstTime + currentlyRunning.startTime){    // if current time = burst time + start time for a currently running process,
                                                                                                                // then this means that the process has finished
                currentlyRunning.finishTime = time;         // set finish time to current time
                currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;   // compute turnaround time
                currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.burstTime;    // compute waiting time
                currentlyRunning = null;
                
                /* starting another process, just at the same time the previous process has finished */
                Process leastBurstTimeProcess = findLeastBurstTimeProcess();            // find the process with minimum burst time
                int leastBursTimetProcessIdx = readyQueue.indexOf(leastBurstTimeProcess);   // find its index in the readyQueue
                if (leastBursTimetProcessIdx != -1)                                     // and if it is in the queue
                    currentlyRunning = readyQueue.remove(leastBursTimetProcessIdx);     // remove it and run it
                
                if(currentlyRunning != null && currentlyRunning.startTime == -1)    // if there is a process has just started to run,
                    currentlyRunning.startTime = time;                              // then set its start time to current time
            }
            
            addToGanttChart(ganttChart, currentlyRunning);          // add currently running process at the current moment to Gantt Chart
            time++;         // increment time
        }
        
        return ganttChart;
    }
         
    public void addToGanttChart(ArrayList<Integer> ganttChart, Process currentlyRunning){   // function to add a process's pid to Gantt Chart
        if (currentlyRunning != null)               // if there is a process running at the time the function has called
            ganttChart.add(currentlyRunning.pid);   // add this process's pid to Gantt Chart
        else                                        
            ganttChart.add(-1);                     // add -1, indicating no process was running at this time
    }
    
    
          public boolean allProcessesFinished(){  // function to check if all process has finished or not
        for (Process p : processes) 
            if(p.finishTime == -1)          // if any process has finish time = -1, then it hasn't finished yet
                return false;
        return true;
    }
            public void checkProcessesArrival(int time){    // check if any process has arrived at the given time
        for (Process p : processes) 
            if(p.arrivalTime == time)   // if a process arrived at the given time
                readyQueue.add(p);      // then add it to the readyQueue
    }
                public static Process findLeastBurstTimeProcess(){   // function to find the process that has minimum burst time
        int minBurstTime = Integer.MAX_VALUE;            // initilize minBurstTime to a very large number
        Process leastBurstTimeProcess = null;
        for (Process p : readyQueue) {
            if(p.burstTime < minBurstTime){         // if process p has less burst time than leastBurstTimeProcess
                leastBurstTimeProcess = p;          // make p the leastBurstTimeProcess           
                minBurstTime = p.burstTime;
            }
        }
        return leastBurstTimeProcess;       // return the leastBurstTimeProcess
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

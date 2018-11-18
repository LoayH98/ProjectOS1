/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoss1;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class FXMLDocumentController implements Initializable {
    Random rand = new Random(); 
    
    public  DecimalFormat numberFormat = new DecimalFormat("#.00"); 
     static ArrayList<Process> processes;
    static ArrayList<Process> readyQueue;
    static ArrayList<Process> processes1;
    @FXML
    private Label label;
    
    @FXML
    private Button readFileBtn;

    @FXML
    private TextField fileNameTxt;
    
    @FXML
    private Button runBtn;

    @FXML
    private RadioButton PradioButton;

    @FXML
    private ToggleGroup Algo;

    @FXML
    private RadioButton SJFradioButton;

    @FXML
    private RadioButton RRradioBtn;

    @FXML
    private RadioButton FCFSradioBtn;
     
    @FXML
    private TextField WTtextArea;

    @FXML
    private TextField TAtextArea;

    @FXML
    private TextField WTAtextArea;
    @FXML 
    private TextField TQtextArea ;
    @FXML
    private Label TQlabel ;
 
    @FXML
    private TableColumn aa ;
    
    
    
    
    @FXML
    private TableView<ProcessTable> Table;
    @FXML
    private TableColumn<ProcessTable, String > cpid;
    @FXML
    private TableColumn<ProcessTable, String > carrivalTime;
    @FXML
    private TableColumn<ProcessTable, String > cburstTime;
    @FXML
    private TableColumn<ProcessTable, String> cfinishTime;
    @FXML
    private TableColumn<ProcessTable, String> cTA;
    @FXML
    private TableColumn<ProcessTable, String> cwaitTime;
    @FXML
    private TableColumn<ProcessTable, String> cWTA;
    @FXML
    private TableColumn<ProcessTable, String> cpriority;
    
    private ObservableList<ProcessTable> List;
     
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        algorithms.getItems().addAll("RR","SJF","Priority","FCFS","SRTF","LCFS");
    }   
    
    
    @FXML
    private void readFileAction(ActionEvent event) {
        
         FileChooser fileChooser = new FileChooser();
         File selectedFile = fileChooser.showOpenDialog(null);
         if (selectedFile != null) {
            if(readFile(selectedFile.getPath()))
           System.out.println("Done!"); 
        }
              
    }
    
     public boolean readFile(String fileName){      // function to read processes data from the given input file, and save it in the arrayList processes
         // returns true if data has read successfully, otherwise it returns false
         
         if(processes == null)
        processes = new ArrayList<Process>();   
        try{
          
            File file = new File(fileName);
            Scanner input = new Scanner(file); 
            input.nextLine();         
            List = FXCollections.observableArrayList();                      // skipping first 2 lines in the input file
            while(input.hasNextLine()){         // read file line by line until its end
                
                String line = input.nextLine();         // get a line
                line = line.replaceAll("[ \t]", "");    // remove spaces and tabs
                String tokens[] = line.split("[,]");    // split it using the de-limitor "|"
                
                /* convert data from String to int and save it */
               /// int pid = Integer.parseInt(tokens[0]);          
                int arrivalTime = Integer.parseInt(tokens[0]);  
                int burstTime = Integer.parseInt(tokens[1]);
               // int repeat = Integer.parseInt(tokens[3]);
             //   int interval = Integer.parseInt(tokens[4]);
                int deadline = Integer.parseInt(tokens[2]);
                
                System.out.print("aaaa");
                /* create a new process from the read data and add it immediately to the processes arrayList */
                Process p1 = new Process(arrivalTime, burstTime, deadline);
                p1.pid = processes.size();
                p1.priority = rand.nextInt(5)+1 ; 
                processes.add(p1);
              
        
              }
             
        
         for(int i =0 ; i<processes.size(); i++){
             ProcessTable t = new ProcessTable(String.valueOf(processes.get(i).pid) , String.valueOf(processes.get(i).arrivalTime) ,String.valueOf(processes.get(i).burstTime ), String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(rand.nextInt(5)+1));
              List.add(t);
              }
            
        cpid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        carrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        cburstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        cfinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        cTA.setCellValueFactory(new PropertyValueFactory<>("TA"));
       cwaitTime.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        cWTA.setCellValueFactory(new PropertyValueFactory<>("WTA"));
        cpriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
       //Table.setItems(null);
       Table.setItems(List);
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
      
       public ArrayList<Integer> ShortestRemainingJobFirst(){ 
       int time = 0;                               // current time
       Process currentlyRunning = null;            // the process that is running currently, initially null
       readyQueue = new ArrayList<Process>();      // ready queue to add arrived processes that are ready to run
       ArrayList<Integer> ganttChart = new ArrayList<Integer>();   // Gantt Chart to show processes run-time
       
       while(!allProcessesFinished()){
            checkProcessesArrival(time);    // check if any process has arriced at current moment
            
            
            
            if(currentlyRunning != null  && currentlyRunning.remainingTime != 0 )
                currentlyRunning.remainingTime-- ;
            
             if(currentlyRunning != null && currentlyRunning.remainingTime == 0){    // if remaining running time for a currently running process is 0,
                                                                                    // then this means that the process has finished  
                currentlyRunning.finishTime = time;         // set finish time to current time
                currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;   // compute turnaround time
                currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.burstTime;    // compute waiting time

                int currentlyRunningIdx = readyQueue.indexOf(currentlyRunning);  // get the index of currently running process
                readyQueue.remove(currentlyRunningIdx);                         // remove this process from the readyQueue
                currentlyRunning = null;

                if (readyQueue.size() != 0){                                // if there still some processes in readyQueue to run
                    currentlyRunning = findShortestRemaining(null) ;   /* get the process that is just after the last process finished (FCFS).
                                                                                                       note that, this process's index = currentlyRunningIdx, because we 
                                                                                                       removed an element from the arrayList, and indices have been shifted left */

                    if(currentlyRunning != null && currentlyRunning.startTime == -1) // if the process has just started to run,
                        currentlyRunning.startTime = time;                           // then set its start time to current time  
                }  
            }
              if((currentlyRunning == null || findShortestRemaining(currentlyRunning).pid != currentlyRunning.pid ) && readyQueue.size() != 0){          // if there is no process running currently or a process has finished its timeQuantum
                currentlyRunning = findShortestRemaining(currentlyRunning); // get the next process in readyQueue
                /*
                note: this line instead of the previous two made a very big mistake
                    currentlyRunning = readyQueue.get( (i++) % readyQueue.size() );
                */
                                     // reset remainingTimeQuantum
                
                if(currentlyRunning != null && currentlyRunning.startTime == -1)// if the process has just started to run,
                    currentlyRunning.startTime = time;                          // then set its start time to current time
            }
              
              for(Process p : readyQueue)
                if(p!= currentlyRunning && (time %3 == 0))
                    p.priority++ ;
              
            addToGanttChart(ganttChart, currentlyRunning);          // add currently running process at the current moment to Gantt Chart
            time++;         // increment time
            
       }
       
       return ganttChart ;
    }
      
        public Process findShortestRemaining(Process ShortestRemainingProcess){
           if(ShortestRemainingProcess == null )
               ShortestRemainingProcess = readyQueue.get(0);
           
           int time = ShortestRemainingProcess.remainingTime ;
           for(Process p : readyQueue){
               if (p.remainingTime < time ){
                   time = p.remainingTime ;
                   ShortestRemainingProcess = p ;
               }
           }
           
           return ShortestRemainingProcess ;
        }
      
      
      public ArrayList<Integer> PrirorityWithP_and_Aging(){ 
       int time = 0;                               // current time
       Process currentlyRunning = null;            // the process that is running currently, initially null
       readyQueue = new ArrayList<Process>();      // ready queue to add arrived processes that are ready to run
       ArrayList<Integer> ganttChart = new ArrayList<Integer>();   // Gantt Chart to show processes run-time
       
       while(!allProcessesFinished()){
            checkProcessesArrival(time);    // check if any process has arriced at current moment
            
            
            
            if(currentlyRunning != null  && currentlyRunning.remainingTime != 0 )
                currentlyRunning.remainingTime-- ;
            
             if(currentlyRunning != null && currentlyRunning.remainingTime == 0){    // if remaining running time for a currently running process is 0,
                                                                                    // then this means that the process has finished  
                currentlyRunning.finishTime = time;         // set finish time to current time
                currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;   // compute turnaround time
                currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.burstTime;    // compute waiting time

                int currentlyRunningIdx = readyQueue.indexOf(currentlyRunning);  // get the index of currently running process
                readyQueue.remove(currentlyRunningIdx);                         // remove this process from the readyQueue
                currentlyRunning = null;

                if (readyQueue.size() != 0){                                // if there still some processes in readyQueue to run
                    currentlyRunning = findMostpriorityProcess(null) ;   /* get the process that is just after the last process finished (FCFS).
                                                                                                       note that, this process's index = currentlyRunningIdx, because we 
                                                                                                       removed an element from the arrayList, and indices have been shifted left */

                    if(currentlyRunning != null && currentlyRunning.startTime == -1) // if the process has just started to run,
                        currentlyRunning.startTime = time;                           // then set its start time to current time  
                }  
            }
              if((currentlyRunning == null || findMostpriorityProcess(currentlyRunning).pid != currentlyRunning.pid ) && readyQueue.size() != 0){          // if there is no process running currently or a process has finished its timeQuantum
                currentlyRunning = findMostpriorityProcess(currentlyRunning); // get the next process in readyQueue
                /*
                note: this line instead of the previous two made a very big mistake
                    currentlyRunning = readyQueue.get( (i++) % readyQueue.size() );
                */
                                     // reset remainingTimeQuantum
                
                if(currentlyRunning != null && currentlyRunning.startTime == -1)// if the process has just started to run,
                    currentlyRunning.startTime = time;                          // then set its start time to current time
            }
              
              for(Process p : readyQueue)
                if(p!= currentlyRunning && (time %3 == 0))
                    p.priority++ ;
              
            addToGanttChart(ganttChart, currentlyRunning);          // add currently running process at the current moment to Gantt Chart
            time++;         // increment time
            
       }
       
       return ganttChart ;
    }
       
       public Process findMostpriorityProcess(Process MostPriorityPorcess){
           if(MostPriorityPorcess == null )
               MostPriorityPorcess = readyQueue.get(0);
           
           int maxPriority = MostPriorityPorcess.priority ;
           for(Process p : readyQueue){
               if (p.priority > maxPriority ||( p.priority == maxPriority && p.pid > MostPriorityPorcess.pid )){
                   maxPriority = p.priority ;
                   MostPriorityPorcess = p ;
               }
           }
          
           return MostPriorityPorcess ;
       }
    
    public ArrayList<Integer> RR(){
        int time = 0;                          // current time
        Process currentlyRunning = null;       // the process that is running currently, initially null
        int timeQuantum, remainingTimeQuantum; 
        int i = 0;                             // index for readyQueue
        
        readyQueue = new ArrayList<Process>();                      // ready queue to add arrived processes that are ready to run
        ArrayList<Integer> ganttChart = new ArrayList<Integer>();   // Gantt Chart to show processes run-time

        timeQuantum = Integer.valueOf(TQtextArea.getText()) ; // read timeQuantum from user
        
        if (timeQuantum < 1){                          // if invalid input was entered
           JOptionPane.showMessageDialog(null, "Time Quantum Cannot be less than 1!");
            return null;
        }
        else{
            remainingTimeQuantum = timeQuantum;     // initialize remainingTimeQuantum
     
        while(!allProcessesFinished()){     // while there are still unfinished processes
            checkProcessesArrival(time);    // check if any process has arriced at current moment
            
            if (currentlyRunning != null){
                
                remainingTimeQuantum--;             // decrement remainingTimeQuantum every second
                currentlyRunning.remainingTime--;   // decrement remainingTime for currently running process every second
            }
                       
            if(currentlyRunning != null && currentlyRunning.remainingTime == 0){    // if remaining running time for a currently running process is 0,
                                                                                    // then this means that the process has finished  
                currentlyRunning.finishTime = time;         // set finish time to current time
                currentlyRunning.turnaround = currentlyRunning.finishTime - currentlyRunning.arrivalTime;   // compute turnaround time
                currentlyRunning.waitingTime = currentlyRunning.turnaround - currentlyRunning.burstTime;    // compute waiting time

                int currentlyRunningIdx = readyQueue.indexOf(currentlyRunning);  // get the index of currently running process
                readyQueue.remove(currentlyRunningIdx);                         // remove this process from the readyQueue
                currentlyRunning = null;

                if (readyQueue.size() != 0){                                // if there still some processes in readyQueue to run
                    currentlyRunning = readyQueue.get( currentlyRunningIdx % readyQueue.size() );   /* get the process that is just after the last process finished (FCFS).
                                                                                                       note that, this process's index = currentlyRunningIdx, because we 
                                                                                                       removed an element from the arrayList, and indices have been shifted left */
                    remainingTimeQuantum = timeQuantum;         // reset remainingTimeQuantum  

                    if(currentlyRunning != null && currentlyRunning.startTime == -1) // if the process has just started to run,
                        currentlyRunning.startTime = time;                           // then set its start time to current time  
                }  
            }
            
            if((currentlyRunning == null || remainingTimeQuantum == 0) && readyQueue.size() != 0){          // if there is no process running currently or a process has finished its timeQuantum
                i = (i + 1) % readyQueue.size(); 
                currentlyRunning = readyQueue.get(i); // get the next process in readyQueue
                /*
                note: this line instead of the previous two made a very big mistake
                    currentlyRunning = readyQueue.get( (i++) % readyQueue.size() );
                */
                remainingTimeQuantum = timeQuantum;                             // reset remainingTimeQuantum
                
                if(currentlyRunning != null && currentlyRunning.startTime == -1)// if the process has just started to run,
                    currentlyRunning.startTime = time;                          // then set its start time to current time
            }
            
            addToGanttChart(ganttChart, currentlyRunning);          // add currently running process at the current moment to Gantt Chart
            time++;         // increment time
        }
        
        return ganttChart;
        }
    }
    /* public int readTimeQuantum(){               // function to read timeQuantum from user and return it
        String timeQuantumString = JOptionPane.showInputDialog(null,"Enter Time Quantum:");
        int timeQuantum;
        
        if (timeQuantumString == null)          // if user pressed cancel button
            return -1; // null string 
        else{
            try {
                timeQuantum = Integer.parseInt(timeQuantumString); // getting timeQuantum from user
                if (timeQuantum < 1)
                    return -2;        //negative time quantum
               
            } catch (Exception e) {
                return -3;       // Invalid Input
            }   
        }
        
        return timeQuantum;
    }
*/
         
    
    public ArrayList<Integer> FirstComeFirstSevice(){
        
        int time = 0;                               // current time
        Process currentlyRunning = null;            // the process that is running currently, initially null
        readyQueue = new ArrayList<Process>();      // ready queue to add arrived processes that are ready to run
        ArrayList<Integer> ganttChart = new ArrayList<Integer>();   // Gantt Chart to show processes run-time
        
        while(!allProcessesFinished()){     // while there are still unfinished processes
            
            checkProcessesArrival(time);    // check if any process has arriced at current moment
            
            if(currentlyRunning == null && readyQueue.size() !=0){               // if no process is running currently 
                                   // and if it is in the queue
                    currentlyRunning = readyQueue.remove(0);     // remove it and run it
                
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
               
                if(readyQueue.size() != 0)
                    currentlyRunning = readyQueue.remove(0);     // remove it and run it
                
                if(currentlyRunning != null && currentlyRunning.startTime == -1)    // if there is a process has just started to run,
                    currentlyRunning.startTime = time;                              // then set its start time to current time
            }
            
            addToGanttChart(ganttChart, currentlyRunning);          // add currently running process at the current moment to Gantt Chart
            time++;         // increment time
        }
        
        return ganttChart;
        
    }
    
    
     public ArrayList<Integer> LastComeFirstSevice(){
        
        int time = 0;                               // current time
        Process currentlyRunning = null;            // the process that is running currently, initially null
        readyQueue = new ArrayList<Process>();      // ready queue to add arrived processes that are ready to run
        ArrayList<Integer> ganttChart = new ArrayList<Integer>();   // Gantt Chart to show processes run-time
        
        while(!allProcessesFinished()){     // while there are still unfinished processes
            
            checkProcessesArrival(time);    // check if any process has arriced at current moment
            
            if(currentlyRunning == null && readyQueue.size() !=0){               // if no process is running currently 
                                   // and if it is in the queue
                    currentlyRunning = readyQueue.remove(readyQueue.size()-1);     // remove it and run it
                
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
               
                if(readyQueue.size() != 0)
                    currentlyRunning = readyQueue.remove(readyQueue.size()-1);     // remove it and run it
                
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
    
     
    
    
    @FXML
    ComboBox algorithms ;
    
    @FXML
    private void Algorithm (ActionEvent event ){
        if(algorithms.getValue()== "RR"){
          TQlabel.setVisible(true);
          TQtextArea.setVisible(true);
          TQtextArea.setText("1");
           cpriority.setVisible(false);
        }
        else if(algorithms.getValue()== "Priority"){
            TQlabel.setVisible(false);
          TQtextArea.setVisible(false);
          TQtextArea.setText("1");
           cpriority.setVisible(true);
        }
        else {
            TQlabel.setVisible(false);
          TQtextArea.setVisible(false);
          TQtextArea.setText("1");
           cpriority.setVisible(false);
        }
            
    }
    
    @FXML
    private void RRButton (ActionEvent event ){
          TQlabel.setVisible(true);
          TQtextArea.setVisible(true);
          TQtextArea.setText("1");
           cpriority.setVisible(false);
    }
    
     @FXML
    private void SJRButton (ActionEvent event ){
          TQlabel.setVisible(false);
          TQtextArea.setVisible(false);
           cpriority.setVisible(false);
    }
    
    
     @FXML
    private void PButton (ActionEvent event ){
          TQlabel.setVisible(false);
          TQtextArea.setVisible(false);
           cpriority.setVisible(true);
    }
    
     @FXML
    private void FCFSButton (ActionEvent event ){
          TQlabel.setVisible(false);
          TQtextArea.setVisible(false);
          TQtextArea.setText("1");
           cpriority.setVisible(false);
    }
   /////////////////////////////////////////////////////////////////////////////////////Run 

    public FXMLDocumentController() {
    }
    @FXML
    private void runAlgoAction (ActionEvent event){
        if(processes == null)
            JOptionPane.showMessageDialog(null, "Error!! There No Processes"); // show an error message
    

       ArrayList<Process> copy = new ArrayList<>();
      
      for (Process p : processes){
          Process p1 = new Process(p.arrivalTime,p.burstTime, p.deadline); ////////// COPY ORGIN PROCESSES
          p1.pid = p.pid ;
          copy.add(p1);
      }
      
      ArrayList <Integer> chart  = new ArrayList<>();

         ////////////////////////////////////////////////////////////////////////SJF  
        if(algorithms.getValue() == "SJF"){
         chart = SJF();
        }
        
        else if(algorithms.getValue() == "FCFS"){
        chart = FirstComeFirstSevice();
        }
        
        ////////////////////////////////////////////////////////////////ROUND ROBEN
        else if(algorithms.getValue() == "RR"){
          chart = RR();
        }
        
        else if(algorithms.getValue() == "Priority"){
             chart = PrirorityWithP_and_Aging();
        }
        
        else if(algorithms.getValue() == "LCFS"){
             chart = LastComeFirstSevice();
        }
        
        else if(algorithms.getValue() == "SRTF"){
             chart = ShortestRemainingJobFirst();
        }
        
        else {
             JOptionPane.showMessageDialog(null, "Error!! Select an Algorithm Please !"); // show an error message
               return ; 
        }
         
         
          for(int i =0 ; i<processes.size() ;i++){
                processes.get(i).RL = new ArrayList<>() ;
                processes.get(i).WL =  new ArrayList<>() ;
            }
          
           for(int i = 0 ; i<chart.size()-1 ; i++){
               if(chart.get(i)!= -1 ){
                   for(int j = 0 ; j<processes.size() ; j++){
                      
                       if(chart.get(i) == j){
                           processes.get(j).RL.add(new RuninngTime(i, i+1));
                       }
                       else if(processes.get(j).arrivalTime < i && processes.get(j).finishTime > i ) {
                           processes.get(j).WL.add(new WaitingTime(i, i+1));
                       }
                   }
               }
           }
           
           
           int j = 0 ; 
              System.out.println("-----------------------------");
          for(int i=0;i<processes.size();i++){
              //if(processes.get(i).WL != null && processes.get(i).RL != null )
                  j =0 ; 
                 while( j  < processes.get(i).RL.size()  ){
                        System.out.println("Run procces #" + i + "-->" + processes.get(i).RL.get(j).startR + ",,"  +processes.get(i).RL.get(j).endR );
                         j++ ; 
                 }
                 j=0 ; 
                   while( j  < processes.get(i).WL.size()  ){
                       System.out.println("Wait procces #" + i + "-->" + processes.get(i).WL.get(j).startW + ",,"  +processes.get(i).WL.get(j).endW );
                        j++ ; 
                   }
              
          }
         
              for(int i =0 ;i <chart.size(); i++)
                System.out.println(chart.get(i));
          
          
          

          
             List = FXCollections.observableArrayList();         
             double TAavg=0.0 ,WTavg=0.0 ,WTAavg=0.0;int i = 0 ;
          
             for(i =0 ;i<processes.size() ; i++){
          ProcessTable p;
           processes.get(i).weightTime = processes.get(i).turnaround / (double) processes.get(i).burstTime ;
           processes.get(i).weightTime = Double.valueOf(numberFormat.format(processes.get(i).weightTime));
          p = new ProcessTable(String.valueOf(processes.get(i).pid) , String.valueOf(processes.get(i).arrivalTime) ,String.valueOf(processes.get(i).burstTime ), String.valueOf(processes.get(i).finishTime),String.valueOf(processes.get(i).turnaround),String.valueOf(processes.get(i).waitingTime),String.valueOf(processes.get(i).weightTime),String.valueOf(processes.get(i).priority));
              List.add(p);
              TAavg+=processes.get(i).turnaround;
              WTavg+=processes.get(i).waitingTime;
              WTAavg+=processes.get(i).weightTime;
             }
        cpid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        carrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        cburstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        cfinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        cTA.setCellValueFactory(new PropertyValueFactory<>("TA"));
       cwaitTime.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        cWTA.setCellValueFactory(new PropertyValueFactory<>("WTA"));
         cpriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
       //Table.setItems(null);
       Table.setItems(List);
       
          TAavg/=(i);
          WTavg/=(i);
          WTAavg/=(i);
           
          
      
          
          TAavg = Double.valueOf(numberFormat.format(TAavg));
          WTavg = Double.valueOf(numberFormat.format(WTavg));
          WTAavg = Double.valueOf(numberFormat.format(WTAavg));
          TAtextArea.setText(String.valueOf(TAavg));
          WTtextArea.setText(String.valueOf(WTavg));
          WTAtextArea.setText(String.valueOf(WTAavg));
       
          
          processes1 = new ArrayList<>();
        for (Process p : processes){
          Process p1 = new Process(p.arrivalTime,p.burstTime, p.deadline); ////////// COPY ORGIN PROCESSES
          p1.pid = p.pid ;
          p1.RL = p.RL ;
          p1.WL = p.WL;
          processes1.add(p1);
      }
         
        
        processes.clear();
         for (Process p : copy){
             Process p2 = new Process(p.arrivalTime, p.burstTime, p.deadline);
             p2.pid = p.pid ;
          processes.add(p2);
      }
      
    }
    
    
    @FXML
    Button generate ;
    
    @FXML
    private void GenerateAction(ActionEvent event){
        if(processes == null)
               processes = new ArrayList<Process>();   

       

      
        Process p1 = new Process(rand.nextInt(20) , rand.nextInt(20)+1 , rand.nextInt(50));
        p1.pid = processes.size();
        p1.priority = rand.nextInt(5)+1 ;
        
        processes.add(p1);
        
         List = FXCollections.observableArrayList();  
        
         for(int i =0 ; i<processes.size(); i++){
             ProcessTable t = new ProcessTable(String.valueOf(processes.get(i).pid) , String.valueOf(processes.get(i).arrivalTime) ,String.valueOf(processes.get(i).burstTime ), String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(processes.get(i).priority));
              List.add(t);
              }
            
        cpid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        carrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        cburstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        cfinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        cTA.setCellValueFactory(new PropertyValueFactory<>("TA"));
       cwaitTime.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        cWTA.setCellValueFactory(new PropertyValueFactory<>("WTA"));
         cpriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
       //Table.setItems(null);
       Table.setItems(List);
            //, repeat, interval
     
    }
    
    
    @FXML
    Button exit ; 
    
    @FXML
    public void exitAction(ActionEvent event){
        Platform.exit();
    }
    
    @FXML
    Button refresh ; 
    
    @FXML
     public void RefreshAction(ActionEvent event){
         if(processes != null){
        List = FXCollections.observableArrayList();  
        
         for(int i =0 ; i<processes.size(); i++){
             ProcessTable t = new ProcessTable(String.valueOf(processes.get(i).pid) , String.valueOf(processes.get(i).arrivalTime) ,String.valueOf(processes.get(i).burstTime ), String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(processes.get(i).priority));
              List.add(t);
              }
            
        cpid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        carrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        cburstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        cfinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        cTA.setCellValueFactory(new PropertyValueFactory<>("TA"));
       cwaitTime.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        cWTA.setCellValueFactory(new PropertyValueFactory<>("WTA"));
         cpriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
       //Table.setItems(null);
       Table.setItems(List);
            //, repeat, interval
            
             TAtextArea.setText("");
          WTtextArea.setText("");
          WTAtextArea.setText("");
            
         }
    }

    public static ArrayList<Process> getProcesses() {
        return processes1;
    }
     
    @FXML
    public void GantChartAction(ActionEvent event){
     
     GanttChartSample gantt = new GanttChartSample();
         gantt.start(new Stage());
    }
     
    @FXML
    Button delete ; 
    
     @FXML
     public void deleteAction(ActionEvent event){
         if(processes != null){
             processes.clear();
        List = FXCollections.observableArrayList();  
        
         for(int i =0 ; i<processes.size(); i++){
             ProcessTable t = new ProcessTable(String.valueOf(processes.get(i).pid) , String.valueOf(processes.get(i).arrivalTime) ,String.valueOf(processes.get(i).burstTime ), String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(processes.get(i).priority));
              List.add(t);
              }
            
        cpid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        carrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        cburstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        cfinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        cTA.setCellValueFactory(new PropertyValueFactory<>("TA"));
       cwaitTime.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        cWTA.setCellValueFactory(new PropertyValueFactory<>("WTA"));
         cpriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
       //Table.setItems(null);
       Table.setItems(List);
            //, repeat, interval
            
             TAtextArea.setText("");
          WTtextArea.setText("");
          WTAtextArea.setText("");
            
             processes = null ;
         }
     }
     
     @FXML
     Button GantChart ;
     
     
    
   
       
}
/// ready
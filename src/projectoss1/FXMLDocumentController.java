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
    int loaySharmoot ;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoss1;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author user
 */
public class ProjectOss1 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
     
        

        Parent root = FXMLLoader.load(getClass().getResource("FXMLFace.fxml"));
     //   System.out.println("Start");
        Scene scene = new Scene(root);       
        stage.setScene(scene);
        stage.setTitle("CPU Scheduling");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

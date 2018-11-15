package projectoss1;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static projectoss1.FXMLDocumentController.processes;
import projectoss1.GanttChart.ExtraData;

// TODO: use date for x-axis
public class GanttChartSample extends Application {

    @Override public void start(Stage stage) {

        stage.setTitle("Gantt Chart Sample");
      

        ArrayList <Process> processes = FXMLDocumentController.getProcesses() ;
         String[] process = new String[processes.size()];
        for(int i =0 ; i<processes.size() ;i++){
           process[i] = "Process #"+ i;
        }
        
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
       // yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(process)));

        chart.setTitle("Process Montoring");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);
        String p;
        
       /* for(int i=0;i<processes.size();i++){
              if(processes.get(i).WL != null && processes.get(i).RL != null ){
               System.out.println("Run procces #" + i + "-->" + processes.get(i).RL.get(0).startR + ",,"  +processes.get(i).RL.get(0).endR );
               System.out.println("Wait procces #" + i + "-->" + processes.get(i).WL.get(0).startW + ",,"  +processes.get(i).WL.get(0).endW );
              }
          }*/
        
        for(int i =0 ; i< processes.size() ; i++){
            p = process[i]; 
                   
        XYChart.Series series = new XYChart.Series();
        for(int j = 0 ; j < processes.get(i).WL.size() ; j++)
            series.getData().add(new XYChart.Data(processes.get(i).WL.get(j).startW, p, new ExtraData( processes.get(i).WL.get(j).endW - processes.get(i).WL.get(j).startW, "status-red")));
        
         for(int j = 0 ; j < processes.get(i).RL.size() ; j++)
             series.getData().add(new XYChart.Data(processes.get(i).RL.get(j).startR, p, new ExtraData(processes.get(i).RL.get(j).endR- processes.get(i).RL.get(j).startR, "status-green")));   
        chart.getData().add(series);
        }
        
        //series.getData().add(new XYChart.Data(processes.get(i).WL.get(0).startW, p, new ExtraData( processes.get(i).WL.get(0).endW - processes.get(i).WL.get(0).startW, "status-red")));
        //series.getData().add(new XYChart.Data(processes.get(i).RL.get(0).startR, p, new ExtraData(processes.get(i).RL.get(0).endR- processes.get(i).RL.get(0).startR, "status-green")));   
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        Scene scene  = new Scene(chart,620,350);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}

package projectoss1;

import java.util.ArrayList;

public class Process {

    int pid;
    int arrivalTime;
    int burstTime;
    int startTime;
    int finishTime;
    int turnaround;
    int waitingTime;
    double weightTime;
    int priority ; 
   // int repeat;
    //int interval;
    int deadline;
    int remainingTime;
    ArrayList <WaitingTime> WL ;
    ArrayList <RuninngTime> RL ;

 //   int remainingPeriods;

//////////////////////////////////////////////////////////////////////////////////////////////////////////
// , int repeat , int interval
    public Process( int arrivalTime, int burstTime, int deadline) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.startTime = -1;            // -1 indicating that the process hasn't started yet
        this.finishTime = -1;           // -1 indicating that the process hasn't finished yet
       // this.repeat = repeat;
       // this.interval = interval;
        this.deadline = deadline;
        this.remainingTime = this.burstTime;    // at the beginning, remaining running time = burst time
      //  this.remainingPeriods = this.repeat;    // at the beginning, remaining periods for a process = repeat
    }

    public void resetProcess(){         // function to reset a process (re-initialize it)
        this.startTime = -1;
        this.finishTime = -1;
        this.remainingTime = this.burstTime;
  //      this.remainingPeriods = this.repeat;
    }

    @Override
    public String toString() {
        return "Process{" + "pid=" + pid + ", arrivalTime=" + arrivalTime + ", burstTime=" + burstTime + ", startTime=" + startTime + ", finishTime=" + finishTime + ", turnaround=" + turnaround + ", waitingTime=" + waitingTime + ", deadline=" + deadline + '}';
    }
    //+ ", repeat=" + repeat + ", interval=" + interval 
}



///////// new copy
// amjad is fucken

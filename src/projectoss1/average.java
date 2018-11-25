/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectoss1;

public class average {
    private double avgWT ;
    private double avgTA ;
    private double avgWTA  ;
    
    

    public average(double avgTA, double avgWT, double avgWTA) {
        this.avgWT = avgWT;
        this.avgTA = avgTA;
        this.avgWTA = avgWTA;
    }

    public double getAvgWT() {
        return avgWT;
    }

    public void setAvgWT(double avgWT) {
        this.avgWT = avgWT;
    }

    public double getAvgTA() {
        return avgTA;
    }

    public void setAvgTA(double avgTA) {
        this.avgTA = avgTA;
    }

    public double getAvgWTA() {
        return avgWTA;
    }

    public void setAvgWTA(double avgWTA) {
        this.avgWTA = avgWTA;
    }
    
    
    
}

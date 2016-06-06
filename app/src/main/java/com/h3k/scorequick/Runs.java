package com.h3k.scorequick;

/**
 * Created by Karan on 5/21/2016.
 */
public class Runs {

    public enum RunsAvalible{
        DOT_BALL(0), SINGLE_RUN(1), DOUBLE_RUN(2), TRIPLE_RUN(3), FOUR_RUN(4), SIX_RUN(6);
        public int value;

        RunsAvalible(int value){
            this.value = value;
        }
    }

    private int runValue;
    private int runTotal;
    private RunsAvalible runAvailable;

    public Runs(){
        this.runValue = 0;
        this.runTotal = 0;
    }

    public void setNumberOfRuns(RunsAvalible runsAvalible){
        this.runAvailable = runsAvalible;
        runValue = runsAvalible.value;
        setRunTotal(runValue);
    }

    public void setExtraRuns (int extraRuns){
        runValue = extraRuns;
        setRunTotal(runValue);
    }

    private void setRunTotal(int runValue){
        runTotal = runTotal + runValue ;
    }

    public int getRun(){
        return runValue;
    }

    public int getTotalRuns(){
        return runTotal;
    }

    public void removeLastRun(){
        runTotal = runTotal - runValue;
    }
}


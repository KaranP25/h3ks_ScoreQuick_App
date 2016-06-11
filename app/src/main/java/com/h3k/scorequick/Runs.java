package com.h3k.scorequick;

/**
 * This class sets the type of runs per ball bowled.
 */
public class Runs {

    /**
     * States the type of runs and its values.
     */
    public enum RunsAvailable {
        DOT_BALL(0), SINGLE_RUN(1), DOUBLE_RUN(2), TRIPLE_RUN(3), FOUR_RUN(4), SIX_RUN(6);
        public int value;

        RunsAvailable(int value){
            this.value = value;
        }
    }

    private int runValue;
    private int runTotal;
    private RunsAvailable runAvailable;

    /**
     * Constructor Runs
     */
    public Runs(){
        this.runValue = 0;
        this.runTotal = 0;
    }

    /**
     * This method sets the number of runs.
     * @param runsAvailable
     */
    public void setNumberOfRuns(RunsAvailable runsAvailable){
        this.runAvailable = runsAvailable;
        runValue = runsAvailable.value;
        setRunTotal(runValue);
    }

    /**
     * This method sets number of extra runs.
     * @param extraRuns
     */
    public void setExtraRuns (int extraRuns){
        runValue = extraRuns;
        setRunTotal(runValue);
    }

    /**
     * This method sets the total runs scored.
     * @param runValue
     */
    private void setRunTotal(int runValue){
        runTotal = runTotal + runValue ;
    }

    /**
     * This method gets the value of the run.
     * @return
     */
    public int getRun(){
        return runValue;
    }

    /**
     * This method gets the total runs scored.
     * @return
     */
    public int getTotalRuns(){
        return runTotal;
    }

    /**
     * This method removes the last run.
     */
    public void removeLastRun(){
        runTotal = runTotal - runValue;
    }
}


package com.h3k.scorequick;

import java.util.ArrayList;

/**
 * This class saves all the balls played in a particular inning.
 */
public class Innings {
    private int mRunScored;
    private Runs mRunType;
    private String mInningOf, mWicketFalenType;
    private int mTotalOvers, mCurrentOver = 0, mNumOfBallsPlayed = 0, mWicketNeeded, mCurrentNumOfWickets,
            mExtraRunOfBall;
    private ArrayList<String>[] mOvers;
    private boolean isOverDone, inningDone, teamChasing;
    private int[] runsOfThatOver, runsAfterOver;
    private int mRunsNeededToWin;

    /**
     * Constructor of Innings
     * @param inningOf
     * @param numOfOvers
     * @param numOfPlayers
     * @param teamChasing
     */
    public Innings(String inningOf, int numOfOvers, int numOfPlayers, boolean teamChasing){
        this.mInningOf = inningOf;
        this.mTotalOvers = numOfOvers;
        this.mWicketNeeded = numOfPlayers - 1;
        this.teamChasing = teamChasing;

        mRunType = new Runs();
        mOvers = new ArrayList[numOfOvers];
        runsOfThatOver = new int[numOfOvers];
        runsAfterOver = new int[numOfOvers];
        for(int i = 0; i < mOvers.length; i++) {
            runsOfThatOver[i] = 0;
            runsAfterOver[i] = 0;
        }
        inningDone = false;
        for(int i = 0; i < mOvers.length; i++) mOvers[i] = new ArrayList<String>();
    }

    /**
     * This method sets the number of runs needed for a team to win.
     * @param runsMade
     */
    public void setRunsNeededToWin(int runsMade){
        if(teamChasing) {
            this.mRunsNeededToWin = runsMade + 1;
        }
    }

    /**
     * This method adds the type of a ball bowled in the arraylist.
     * @param overNum
     * @param ballBowled
     */
    public void setThisOverOverview(int overNum, BallBowled ballBowled){
        switch (ballBowled.getBallPlayed()) {
            case "b":
            case "lb":
                switch (mExtraRunOfBall){
                    case 2:
                    case 3:
                    case 4:
                        mOvers[overNum].add(String.valueOf(getExtraRunOfBall()) + ballBowled.getBallPlayed());
                        break;
                    default:
                        mOvers[overNum].add(ballBowled.getBallPlayed());
                }
                break;
            case "nb":
            case "wd":
                switch (mExtraRunOfBall){
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        mOvers[overNum].add(ballBowled.getBallPlayed() + "+" + String.valueOf(getExtraRunOfBall()));
                        break;
                    default:
                        mOvers[overNum].add(ballBowled.getBallPlayed());
                }
                break;
            case "W":
                mOvers[overNum].add(ballBowled.getBallPlayed() + "(" + getFallenWicketType() + ")");
                break;
            default:
                mOvers[overNum].add(ballBowled.getBallPlayed());
        }

    }

    /**
     * This method gets values from the elements of the arraylist.
     * @param over
     * @param totalVersion
     * @return
     */
    public String getOverOverview(int over, boolean totalVersion){
        StringBuilder thisOver = new StringBuilder();

        if (totalVersion) {
            thisOver.append("");
            String spliter = "";

            for (String i : mOvers[over]) {
                thisOver.append(spliter).append(i);
                spliter = " + ";
            }
            thisOver.append("");
        }else{
            thisOver.append("[ ");
            String spliter = "";

            //try {
                for (String i : mOvers[over]) {
                    thisOver.append(spliter).append(i);
                    spliter = ", ";
                }
                thisOver.append(" ]");
            //}catch(Exception e){ }
        }

        return thisOver.toString();
    }

    /**
     * This method sets the extra runs scored of a ball.
     * @param runOfBall
     */
    public void setExtraRunOfBall(Runs.RunsAvailable runOfBall){
        this.mExtraRunOfBall = runOfBall.value;
    }

    /**
     * This method gets the extra runs scored.
     * @return
     */
    private int getExtraRunOfBall(){
        return mExtraRunOfBall;
    }

    /**
     * This method sets wickets.
     * @param wicket
     */
    public void setFallenWicket(WicketsType wicket){
        mWicketFalenType = wicket.value;
        mCurrentNumOfWickets++;
    }

    /**
     * This method gets the type of wicket.
     * @return
     */
    private String getFallenWicketType(){
        return mWicketFalenType;
    }

    /**
     * This method sets amount of runs scored of a legal ball.
     * @param getRun
     */
    public void setRunScored(Runs.RunsAvailable getRun){
        mRunType.setNumberOfRuns(getRun);
        runsOfThatOver[mCurrentOver] = runsOfThatOver[mCurrentOver] + mRunType.getRun();
    }

    /**
     * This method sets extra runs scored.
     * @param extra
     */
    public void setExtraRuns(int extra){
        mRunType.setExtraRuns(extra);
    }

    /**
     * This method gets total runs scored.
     * @return
     */
    public int getTotalRunScored(){
        return mRunType.getTotalRuns();
    }

    /**
     * This method if an over is complete (over = 6 legal balls).
     */
    public void legalBallsPlayed(){
        if(mNumOfBallsPlayed < 6) {
            mNumOfBallsPlayed++;
        }
        if(mNumOfBallsPlayed == 6){
            isOverDone = true;
        } else{
            isOverDone = false;
        }
    }

    /**
     * This method gets the amount of balls played.
     * @return
     */
    public int getNumOfBallsPlayed(){
        return mNumOfBallsPlayed;
    }

    /**
     * This method gets number of the over.
     * @return
     */
    public int getCurrentOver(){
        return mCurrentOver;
    }

    /**
     * This method gets number of wickets.
     * @return
     */
    public int getCurrentNumOfWickets(){
        return mCurrentNumOfWickets;
    }

    /**
     * This method sets if an over is finished.
     * @param done
     */
    public void setOverDone(boolean done){
        if(done) {
            mNumOfBallsPlayed = 0;
            runsAfterOver[mCurrentOver] = getTotalRunScored();
            mCurrentOver++;
            isOverDone = false;
        }else if(!done){
            mNumOfBallsPlayed = 5;
            isOverDone = false;
        }
    }

    /**
     * This method checks if an over is complete.
     * @return
     */
    public boolean isOverComplete(){
        if(isOverDone){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method gets a particular ball from an over.
     * @param overNum
     * @param index
     * @return
     */
    public String getCertainBallOfOver(int overNum, int index){
        return mOvers[overNum].get(index);
    }

    /**
     * This method gets number of ball bowled.
     * @param overNum
     * @return
     */
    public int getHowManyBallsBowled(int overNum){
        return mOvers[overNum].size();
    }

    /**
     * This method gets number of runs scored in an over.
     * @param overNum
     * @return
     */
    public int getRunsOfThatOver(int overNum){
        return runsOfThatOver[overNum];
    }

    /**
     * This method gets amount of runs after the over is done.
     * @param overNum
     * @return
     */
    public int getRunsAfterOver(int overNum){
        if(!isOverComplete() && !isInningDone()){
            runsAfterOver[mCurrentOver] = getTotalRunScored();
        }
        return runsAfterOver[overNum];
    }

    /**
     * This method gets method gets total number of balls bowled.
     * @return
     */
    public int getTotalBallsBowled(){
        return mCurrentOver * 6 + mNumOfBallsPlayed;
    }

    /**
     * This method allows the user undo the last ball bowled.
     * @param overNum
     * @param ballLegal
     * @param wasLastBallWicket
     */
    public void undo(int overNum, boolean ballLegal, boolean wasLastBallWicket){
        if(ballLegal){
            if (mNumOfBallsPlayed != 0) {
                mNumOfBallsPlayed--;
            }
            if(mOvers[overNum].size() == 0){
                overNum--;
            }
        }
        if(wasLastBallWicket){
            if(mCurrentNumOfWickets != 0) {
                mCurrentNumOfWickets--;
            }
        }else{
            if(mRunType.getTotalRuns() != 0) {
                mRunType.removeLastRun();
                runsOfThatOver[mCurrentOver] = runsOfThatOver[mCurrentOver] - mRunType.getRun();
            }
        }
        if(!mOvers[overNum].isEmpty()) {
            mOvers[overNum].remove(mOvers[overNum].size() - 1);
        }
    }

    /**
     * This method returns the state of the inning.
     * @return
     */
    public boolean isInningDone(){
        checkInningDone();
        if(inningDone){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method checks if the inning is done.
     */
    private void checkInningDone(){
        if(mCurrentOver == mTotalOvers){
            inningDone = true;
        }else if(mCurrentNumOfWickets == mWicketNeeded){
            inningDone = true;
        }else if((mRunsNeededToWin <=  mRunType.getTotalRuns()) && teamChasing){
            inningDone = true;
        }else{
            inningDone = false;
        }

    }
}

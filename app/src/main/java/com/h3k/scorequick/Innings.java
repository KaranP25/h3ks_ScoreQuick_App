package com.h3k.scorequick;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Karan on 5/21/2016.
 */
public class Innings {
    private int mRunScored;
    private Runs mRunType;
    private String mInningOf, mWicketFalenType;
    private int mTotalOvers, mCurrentOver = 0, mNumOfBallsPlayed = 0, mRuns, mExtraRuns, mWicketNeeded, mCurrentNumOfWickets,
            mExtraRunOfBall;
    private ArrayList<String>[] mOvers;
    private boolean isOverDone, inningDone, teamChasing;
    private int[] runsOfThatOver, runsAfterOver;

    private int mRunsNeededToWin;


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

    public void setRunsNeededToWin(int runs){
        if(teamChasing) {
            this.mRunsNeededToWin = runs;
        }
    }

    public String getInningOf(){
        return mInningOf;
    }

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

    public void setExtraRunOfBall(Runs.RunsAvalible runOfBall){
        this.mExtraRunOfBall = runOfBall.value;
    }

    private int getExtraRunOfBall(){
        return mExtraRunOfBall;
    }

    public void setFallenWicket(WicketsType wicket){
        mWicketFalenType = wicket.value;
        mCurrentNumOfWickets++;
    }

    private String getFallenWicketType(){
        return mWicketFalenType;
    }

    public void setRunScored(Runs.RunsAvalible getRun){
        mRunType.setNumberOfRuns(getRun);
        runsOfThatOver[mCurrentOver] = runsOfThatOver[mCurrentOver] + mRunType.getRun();
    }

    public void setExtraRuns(int extra){
        mRunType.setExtraRuns(extra);
    }

    public int getTotalRunScored(){
        return mRunType.getTotalRuns();
    }

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

    public int getNumOfBallsPlayed(){
        return mNumOfBallsPlayed;
    }

    public int getCurrentOver(){
        return mCurrentOver;
    }

    public int getCurrentNumOfWickets(){
        return mCurrentNumOfWickets;
    }

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

    public boolean isOverComplete(){
        if(isOverDone){
            return true;
        }else {
            return false;
        }
    }

    public String getCertainBallOfOver(int overNum, int index){
        return mOvers[overNum].get(index);
    }

    public int getHowManyBallsBowled(int overNum){
        return mOvers[overNum].size();
    }

    public int getRunsOfThatOver(int overNum){
        return runsOfThatOver[overNum];
    }

    public int getRunsAfterOver(int overNum){
        if(!isOverComplete()){
            runsAfterOver[mCurrentOver] = getTotalRunScored();
        }
        return runsAfterOver[overNum];
    }

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

    public boolean isInningDone(){
        if(inningDone){
            return true;
        }else {
            return false;
        }
    }

    public void checkInningDone(){
        if(mCurrentOver == mTotalOvers){
            inningDone = true;
        }else if(mCurrentNumOfWickets == mWicketNeeded){
            inningDone = true;
        }else if((mRunType.getTotalRuns() == mRunsNeededToWin) && teamChasing){
            inningDone = true;
        }else{
            inningDone = false;
        }

    }
}

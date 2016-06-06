package com.h3k.scorequick;

import java.util.ArrayList;

/**
 * Created by Karan on 5/21/2016.
 */
public class Innings {
    private int mRunScored;
    private int mWicketTaken;
    private Runs mRunType;
    private String mInningOf, mWicketFalenType;
    private int mTotalOvers, mCurrentOver = 0, mNumOfBallsPlayed = 0, mRuns, mExtraRuns, mWicketNeeded, mCurrentNumOfWickets,
            mExtraRunOfBall;
    private ArrayList<String>[] mOvers;
    private boolean isOverDone;


    public Innings(String inningOf, int numOfOvers, int numOfPlayers){
        this.mInningOf = inningOf;
        this.mTotalOvers = numOfOvers;
        this.mWicketNeeded = numOfPlayers - 1;

        mRunType = new Runs();
        mOvers = new ArrayList[numOfOvers];
        for(int i = 0; i < mOvers.length; i++) mOvers[i] = new ArrayList<String>();
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

    public String getOverOverview(int over){
        StringBuilder thisOver = new StringBuilder();
        thisOver.append("[ ");
        String spliter = "";

        for(String i : mOvers[over]){
            thisOver.append(spliter).append(i);
            spliter = ", ";
        }
        thisOver.append(" ]");

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
    }

    public void setExtraRuns(int extra){
        mRunType.setExtraRuns(extra);
    }
    public int getTotalRunScored(){
        return mRunType.getTotalRuns();
    }

    public void leagalBallsPlayed(){
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

    public void setOverDone(boolean dun){
       // isOverDone = dun;
        if(dun) {
            mNumOfBallsPlayed = 0;
            mCurrentOver++;
            isOverDone = false;
        }else if(!dun){
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

    public void undo(int overNum, boolean ballLegal, boolean wasLastBallWicket){
        if(ballLegal){
            mNumOfBallsPlayed--;
        }
        if(wasLastBallWicket){
            mCurrentNumOfWickets--;
        }else{
            mRunType.removeLastRun();
        }
        mOvers[overNum].remove(mOvers[overNum].size()-1);
    }



}

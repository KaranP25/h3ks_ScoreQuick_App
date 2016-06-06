package com.h3k.scorequick;

/**
 * Created by Karan on 5/23/2016.
 */

public class BallBowled {

    public enum TypeOfBalls {
        NOTPALYED("nP"), DOT_BALL("•"), WIDEBALL("wd"), BYES("b"), LEGBYES("lb"), NO_BALL("nb"), WICKET("W"),
        ONE("1"), TWO("2"), THREE("3"), FOUR("4"), SIX("6");

        public String value;
        TypeOfBalls(String value) {
            this.value = value;
        }
    }

    private TypeOfBalls mTypeOfBall;
    private String mBallValue;

    public BallBowled(TypeOfBalls typeOfBall){
        this.mTypeOfBall = typeOfBall;
        mBallValue = mTypeOfBall.value;
    }

    public void setBallPlayed(TypeOfBalls ballPlayed){
        this.mTypeOfBall = ballPlayed;
        mBallValue = mTypeOfBall.value;
    }

    public void setWicketType (WicketsType wickets){

    }


    public void setBallPlayedRunScored(int runs){
        this.mBallValue = String.valueOf(runs);
    }

    public String getBallPlayed(){
        return mBallValue;
    }

    public boolean isEqual(TypeOfBalls ballPlayed){
        if(ballPlayed == mTypeOfBall){
            return true;
        }else{
            return false;
        }
    }
}


package com.h3k.scorequick;

/**
 * This class is used to set the type of ball bowled by a team.
 * @author Karan P., Karan J., Kalpit, Harsh
 * @version Final Build
 */
public class BallBowled {

    private TypeOfBalls mTypeOfBall;
    private String mBallValue;

    /**
     * Constructor of BallBowled
     * @param typeOfBall
     */
    public BallBowled(TypeOfBalls typeOfBall){
        this.mTypeOfBall = typeOfBall;
        mBallValue = mTypeOfBall.value;
    }

    /**
     * This method sets the ball.
     * @param ballPlayed
     */
    public void setBallPlayed(TypeOfBalls ballPlayed){
        this.mTypeOfBall = ballPlayed;
        mBallValue = mTypeOfBall.value;
    }

    /**
     * This method sets the run scored on a ball.
     * @param runs
     */
    public void setBallPlayedRunScored(int runs){
        this.mBallValue = String.valueOf(runs);
    }

    /**
     * This method gets the ball played.
     * @return
     */
    public String getBallPlayed(){
        return mBallValue;
    }
}


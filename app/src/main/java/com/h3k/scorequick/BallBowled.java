package com.h3k.scorequick;

/**
 * This class is used to set the type of ball bowled by a team.
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


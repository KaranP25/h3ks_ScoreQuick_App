package com.h3k.scorequick;

/**
 * Created by Karan on 6/10/2016.
 */
/**
 * States the type of runs and its values.
 * @author Karan P., Karan J., Kalpit, Harsh
 */
public enum RunsAvailable {
    DOT_BALL(0), SINGLE_RUN(1), DOUBLE_RUN(2), TRIPLE_RUN(3), FOUR_RUN(4), SIX_RUN(6);
    public int value;

    RunsAvailable(int value){
        this.value = value;
    }
}
package com.h3k.scorequick;

/**
 * States the type of Balls and its values.
 * @author Karan P., Karan J., Kalpit
 *
 */
public enum TypeOfBalls {
    NOTPALYED("nP"), DOT_BALL("•"), WIDEBALL("wd"), BYES("b"), LEGBYES("lb"), NO_BALL("nb"), WICKET("W"),
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), SIX("6");

    public String value;
    TypeOfBalls(String value) {
        this.value = value;
    }
}
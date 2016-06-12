package com.h3k.scorequick;

/**
 * This enum sets the type of a wicket.
 * @author Karan P., Karan J., Kalpit, Harsh
 * @version Final Build
 */
public enum WicketsType {
    CAUGHT("Caught"), BOWLED("Bowled"), LBW("LBW"), RUN_OUT("Run OUT"), STUMPED("Stumped"), HIT_WICKET("Hit Wicket"), RETIRED_HURT("Retired Hurt");

    public String value;

    /**
     * Constructor of Wicketstype
     * @param value
     */
    WicketsType(String value) {
        this.value = value;
    }
}





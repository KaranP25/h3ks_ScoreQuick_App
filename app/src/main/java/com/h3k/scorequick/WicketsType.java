package com.h3k.scorequick;

/**
 * Created by Karan on 5/23/2016.
 */

public enum WicketsType {
    CAUGHT("Caught"), BOWLED("Bowled"), LBW("LBW"), RUN_OUT("Run OUT"), STUMPED("Stumped"), HIT_WICKET("Hit Wicket"), RETIRED_HURT("Retired Hurt");

    public String value;
    WicketsType(String value) {
        this.value = value;
    }
}





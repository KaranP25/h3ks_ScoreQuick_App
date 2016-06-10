package com.h3k.scorequick;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Karan on 6/9/2016.
 */
public class Winner {
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private SharedPreferences mPrefs;

    private int teamARuns, teamBRuns, teamAWickets, teamBWickets;
    private String send ;

    public Winner (Context context){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final int MAX_OVER = mPrefs.getInt(GET_MAX_OVER, 0);
        final int MAX_PLAYER = mPrefs.getInt(GET_MAX_PLAYER, 0);
    }
    public void setTeamAName(int runs){
        this.teamARuns = runs;
    }

    public void setTeamBName(String name){
        this.teamBRuns = runs;
    }

    public void setTeamARuns(int runs){
        this.teamARuns = runs;
    }

    public void setTeamBRuns(int runs){
        this.teamBRuns = runs;
    }

    public void setTeamAWickets(int wickets){
        this.teamAWickets = wickets;
    }

    public void setTeamBWickets(int wickets){
        this.teamBWickets = wickets;
    }

    public int getTeamAWickets() {
        return teamAWickets;
    }

    public int getTeamBWickets() {
        return teamBWickets;
    }

    public int getTeamARuns(){
        return teamARuns;
    }

    public int getTeamBRuns(){
        return teamBRuns;
    }

    public String whoWon(){

        if(teamARuns > teamBRuns){
            send = "team a won";
        }
        else if (teamARuns < teamBRuns){
            send = "team b won";
        }
        if (teamBWickets == )
        return send ;
    }
}

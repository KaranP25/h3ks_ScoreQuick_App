package com.h3k.scorequick;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This class obtains who the winner is.
 * @author Karan P., Karan J., Kalpit, Harsh
 * @version Final Build
 */
public class WinnerFinder {
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private static final String GET_TEAM1_NAME = "getTeam1Name";
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private SharedPreferences mPrefs;
    private int teamARuns, teamBRuns, teamAWickets, teamBWickets, teamABalls, teamBBalls;
    private String send, teamAName, teamBName;
    private int MAX_OVER, MAX_PLAYER;

    /**
     * Constructor of Winner class.
     * @param context
     */
    public WinnerFinder(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        MAX_OVER = mPrefs.getInt(GET_MAX_OVER, 0);
        MAX_PLAYER = mPrefs.getInt(GET_MAX_PLAYER, 0);
        teamAName = mPrefs.getString(GET_TEAM1_NAME, " ");
        teamBName = mPrefs.getString(GET_TEAM2_NAME, " ");
    }

    /**
     * This method sets runs scored by team a.
     * @param runs
     */
    public void setTeamARuns(int runs) {
        this.teamARuns = runs;
    }

    /**
     * This method sets runs scored by team b.
     * @param runs
     */
    public void setTeamBRuns(int runs) {
        this.teamBRuns = runs;
    }

    /**
     * This method sets the number of players got out from team a.
     * @param wickets
     */
    public void setTeamAWickets(int wickets) {
        this.teamAWickets = wickets;
    }

    /**
     * This method sets the number of players got out from team b.
     * @param wickets
     */
    public void setTeamBWickets(int wickets) {
        this.teamBWickets = wickets;
    }

    /**
     * This method sets the balls played by team a.
     * @param balls
     */
    public void setTeamABalls(int balls) {this.teamABalls = balls;}

    /**
     * This method sets the balls played by team b.
     * @param balls
     */
    public void setTeamBBalls(int balls) {this.teamBBalls = balls;}

    /**
     * This method gets the number of players got out from team a.
     * @return
     */
    public int getTeamAWickets() {
        return teamAWickets;
    }

    /**
     * This method gets the number of players got out from team b.
     * @return
     */
    public int getTeamBWickets() {
        return teamBWickets;
    }

    /**
     * This method gets the number of runs scored by team a.
     * @return
     */
    public int getTeamARuns() {
        return teamARuns;
    }

    /**
     * This method gets the number of runs scored by team b.
     * @return
     */
    public int getTeamBRuns() {
        return teamBRuns;
    }

    /**
     * This method gets the number of balls played by team a.
     * @return
     */
    public int getTeamABalls(){ return teamABalls;}

    /**
     * This method gets the number of balls played by team b.
     * @return
     */
    public int getTeamBBalls(){ return teamBBalls;}

    /**
     * This method finds which team won.
     * @return
     */
    public String whoWon() {

        if (teamARuns > teamBRuns) {
            send = "Team " + teamAName + " won by: " + String.valueOf(getTeamARuns() - getTeamBRuns()) + " runs.";
        } else if (teamARuns < teamBRuns) {
            send = "Team " + teamBName + " won by: " + String.valueOf(MAX_PLAYER - getTeamBWickets() - 1) + " wickets";
        }else {
            send = "Both teams tied.";
        }
        return send;
    }
}

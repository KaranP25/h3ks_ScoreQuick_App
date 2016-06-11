package com.h3k.scorequick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * This class is use for displaying stats of game.
 */
public class StatsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String GET_TEAM1_NAME = "getTeam1Name";
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";

    private static final String GET_SCORE_T1 = "getScoreT1";
    private static final String GET_OVER_T1 = "getOverT1";
    private static final String GET_BALLS_T1 = "getBallsT1";
    private static final String GET_WICKET_T1 = "getWicketT1";
    private static final String GET_SCORE_T2 = "getScoreT2";
    private static final String GET_OVER_T2 = "getOverT2";
    private static final String GET_BALLS_T2 = "getBallsT2";
    private static final String GET_WICKET_T2 = "getWicketT2";

    private static final String GET_TEAM1STATE = "getTeam1State";
    private static final String GET_TEAM2STATE = "getTeam2State";

    private SharedPreferences mPrefs;
    private TextView nameT1, nameT2, scoreT1, scoreT2, ballOverT1, ballOverT2, maxPlayer, maxOver;
    private Button detailsT1, detailsT2;
    private Button btnTeamOne, btnTeamTwo;
    private String[] mOversInningT1, mOversInningT2;
    private int[] mRunsOfOverT1, mRunsOfOverT2, mRunsAfterOverT1, mRunsAfterOverT2;

    private int mOvers;

    /**
     * This method is called at runtime.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Show icon on top of actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        btnTeamOne = (Button) findViewById(R.id.Team1_btn);
        btnTeamOne.setOnClickListener(this);
        btnTeamTwo = (Button) findViewById(R.id.Team2_btn);
        btnTeamTwo.setOnClickListener(this);
        nameT1 = (TextView) this.findViewById(R.id.Team1_name);
        nameT2 = (TextView) this.findViewById(R.id.Team2_name);
        scoreT1 = (TextView) this.findViewById(R.id.Team1_score);
        scoreT2 = (TextView) this.findViewById(R.id.Team2_score);
        ballOverT1 = (TextView) this.findViewById(R.id.Team1_ball);
        ballOverT2 = (TextView) this.findViewById(R.id.Team2_ball);
        maxPlayer = (TextView) this.findViewById(R.id.max_players) ;
        maxOver = (TextView) this.findViewById(R.id.max_over);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        int team1Score, team2Score, team1Wickets, team2Wickets, team1Over, team2Over,
                team1Ball, team2Ball;
        boolean team1State, team2State;

        final int MAX_OVER = mPrefs.getInt(GET_MAX_OVER, 0);
        final int MAX_PLAYER = mPrefs.getInt(GET_MAX_PLAYER, 0);

        String team1Name = mPrefs.getString(GET_TEAM1_NAME, "Inning 1");
        String team2Name = mPrefs.getString(GET_TEAM2_NAME, "Inning 2");
        team1State = mPrefs.getBoolean(GET_TEAM1STATE, false);
        team2State = mPrefs.getBoolean(GET_TEAM2STATE, false);

        team1Score = mPrefs.getInt(GET_SCORE_T1, 0);
        team1Ball = mPrefs.getInt(GET_BALLS_T1, 0);
        team1Wickets = mPrefs.getInt(GET_WICKET_T1, 0);
        team1Over = mPrefs.getInt(GET_OVER_T1, 0);
        team2Score = mPrefs.getInt(GET_SCORE_T2, 0);
        team2Ball = mPrefs.getInt(GET_BALLS_T2, 0);
        team2Wickets = mPrefs.getInt(GET_WICKET_T2, 0);
        team2Over = mPrefs.getInt(GET_OVER_T2, 0);

        mOversInningT1 = new String[MAX_OVER];
        mRunsOfOverT1 = new int[MAX_OVER];
        mRunsAfterOverT1 = new int[MAX_OVER];
        for(int x = 0; x < MAX_OVER; x++) {
            mOversInningT1[x] = mPrefs.getString("overviewT1_" + x, "");
            mRunsOfOverT1[x] = mPrefs.getInt("runsOfOverT1_" + x, 0);
            mRunsAfterOverT1[x] = mPrefs.getInt("runsAfterOverT1_" + x, 0);
        }

        mOversInningT2 = new String[MAX_OVER];
        mRunsOfOverT2 = new int[MAX_OVER];
        mRunsAfterOverT2 = new int[MAX_OVER];
        //mOversInning2 = new String[mPrefs.getInt("overOverview_sizeT2", 0)];
        for(int x = 0; x < MAX_OVER; x++) {
            mOversInningT2[x] = mPrefs.getString("overviewT2_" + x, "");
            mRunsOfOverT2[x] = mPrefs.getInt("runsOfOverT2_" + x, 0);
            mRunsAfterOverT2[x] = mPrefs.getInt("runsAfterOverT2_" + x, 0);
        }

        maxPlayer.setText("Players Playing: " + MAX_PLAYER);
        maxOver.setText("Max Overs: " + MAX_OVER);
        nameT1.setText(team1Name + " (Inning 1)");
        nameT1.setPaintFlags(nameT1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        nameT2.setText(team2Name + " (Inning 2)");
        nameT2.setPaintFlags(nameT1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        if(team1State && !team2State) {
            scoreT1.setText(String.valueOf(team1Score) + "/" + String.valueOf(team1Wickets));
            ballOverT1.setText(String.valueOf(team1Over) + "." + String.valueOf(team1Ball));

            scoreT2.setText("DNP");
            ballOverT2.setText("DNP");
            btnTeamTwo.setEnabled(false);
        }else if(!team1State && team2State) {
            scoreT1.setText(String.valueOf(team1Score) + "/" + String.valueOf(team1Wickets));
            ballOverT1.setText(String.valueOf(team1Over) + "." + String.valueOf(team1Ball));

            scoreT2.setText(String.valueOf(team2Score) + "/" + String.valueOf(team2Wickets));
            ballOverT2.setText(String.valueOf(team2Over) + "." + String.valueOf(team2Ball));
            btnTeamTwo.setEnabled(true);
        }
    }

    /**
     * This method performs specific tasks on button press.
     * @param v
     */
    public void onClick(View v) {

        if (v.getId() == R.id.Team1_btn){
            ScrollView s = new ScrollView(this);
            LinearLayout vh = new LinearLayout(this);
            vh.setOrientation(LinearLayout.VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mOversInningT1.length; i++){
                TextView t = new TextView(this);
                String message;
                if(mOversInningT1[i].isEmpty()){
                    message = " OVER NOT PLAYED YET";
                }else {
                    message = " " + mOversInningT1[i] + " = " + String.valueOf(mRunsOfOverT1[i]) + "  > " +
                    String.valueOf(mRunsAfterOverT1[i] + " total Runs");
                }
                t.setText("  Over " + (i + 1) + ": " + message);
                t.setTextSize(16);
                vh.addView(t);
            }
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
        if (v.getId() == R.id.Team2_btn){
            ScrollView s = new ScrollView(this);
            LinearLayout vh = new LinearLayout(this);
            vh.setOrientation(LinearLayout.VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mOversInningT2.length; i++){
                TextView t = new TextView(this);
                String message;
                if(mOversInningT2[i].isEmpty()){
                    message = " OVER NOT PLAYED YET";
                }else {
                    message = " " + mOversInningT2[i] + " = " + String.valueOf(mRunsOfOverT2[i]) + "  > " +
                            String.valueOf(mRunsAfterOverT2[i] + " total Runs");
                }
                t.setText("  Over " + (i + 1) + ": " + message);
                t.setTextSize(16);
                vh.addView(t);
            }
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }
}
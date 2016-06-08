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
 * Created by Karan on 6/5/2016.
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

    StatsActivity () {

    }

    private SharedPreferences mPrefs;
    private TextView nameT1, nameT2, scoreT1, scoreT2, ballOverT1, ballOverT2, dnp;
    private Button detailsT1, detailsT2;
    private Button btnTeamOne, btnTeamTwo;
    private String[] mOversInning1, mOversInning2;

    private int mOvers;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

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

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        int team1Score, team2Score, team1Wickets, team2Wickets, team1Over, team2Over,
                team1Ball, team2Ball;
        boolean team1State, team2State;

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mOversInning1 = bundle.getStringArray("overOverviewT1");

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

        mOversInning1 = new String[mPrefs.getInt("overOverview_sizeT1", 0)];
        for(int x = 0; x < mOversInning1.length; x++) {
            mOversInning1[x] = mPrefs.getString("overviewT1_" + x, null);
        }
        mOversInning2 = new String[mPrefs.getInt("overOverview_sizeT2", 0)];
        for(int x = 0; x < mOversInning2.length; x++) {
            mOversInning2[x] = mPrefs.getString("overviewT2_" + x, null);
        }

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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.Team1_btn){
            ScrollView s = new ScrollView(this);
            LinearLayout vh = new LinearLayout(this);
            vh.setOrientation(LinearLayout.VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mOversInning1.length; i++){
                TextView t = new TextView(this);
                String message;
                if(mOversInning1[i].isEmpty()){
                    message = " OVER NOT PLAYED YET";
                }else {
                    message = mOversInning1[i];
                }
                t.setText("   Over " + (i + 1) + ": " + message);
                t.setTextSize(15);
                vh.addView(t);
            }
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })//.setCancelable(false)
                    .show();
        }
        if (v.getId() == R.id.Team2_btn){
            ScrollView s = new ScrollView(this);

            LinearLayout vh = new LinearLayout(this);
            vh.setOrientation(LinearLayout.VERTICAL);
            s.addView(vh);

            String overs [] = {"1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6","1: 0,0,W,0,1,6"};


            for (int i = 0; i < overs.length; i++){
                TextView t = new TextView(this);
                t.setText(overs[i]);
                t.setTextSize(35);
                vh.addView(t);
            }
            new AlertDialog.Builder(this).setView(s).show();
        }
    }
}
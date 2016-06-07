package com.h3k.scorequick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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
    StatsActivity () {

    }

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
        //dnp = (TextView) this.findViewById(R.id.DNP);

        int team1Score, team2Score, team1Wickets, team2Wickets, team1Over, team2Over,
        team1Ball, team2Ball;

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        String team1Name = bundle.getString("getTeam1Name");
        String team2Name = bundle.getString("getTeam2Name");
        boolean team1State = bundle.getBoolean("getTeam1State");
        boolean team2State = bundle.getBoolean("getTeam2State");
        mOversInning1 = bundle.getStringArray("overOverviewT1");
        nameT1.setText(team1Name);
        nameT1.setPaintFlags(nameT1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        nameT2.setText(team2Name);
        nameT2.setPaintFlags(nameT1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        if(team1State && !team2State) {
            team1Score = bundle.getInt("getScoreT1");
            team1Ball = bundle.getInt("getBallsT1");
            team1Wickets = bundle.getInt("getWicketT1");
            team1Over = bundle.getInt("getOverT1");

            scoreT1.setText(String.valueOf(team1Score) + "/" + String.valueOf(team1Wickets));
            ballOverT1.setText(String.valueOf(team1Over) + "." + String.valueOf(team1Ball));
            scoreT2.setVisibility(View.INVISIBLE);
            ballOverT2.setVisibility(View.INVISIBLE);
            //dnp.setText("DNP YET");


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
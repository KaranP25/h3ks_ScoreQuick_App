package com.h3k.scorequick;

import android.app.AlertDialog;
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

    private TextView nameT1, nameT2, scoreT1, scoreT2, ballOverT1, ballOverT2;
    private Button detailsT1, detailsT2;
    private Button btnTeamOne, btnTeamTwo;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        btnTeamOne = (Button) findViewById(R.id.Team1_btn);
        btnTeamOne.setOnClickListener(this);
        btnTeamTwo = (Button) findViewById(R.id.Team2_btn);
        btnTeamTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.Team1_btn){
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
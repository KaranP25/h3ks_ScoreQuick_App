package com.h3k.scorequick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class InningTwoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private static final String GET_SCORE_T2 = "getScoreT2";
    private static final String GET_OVER_T2 = "getOverT2";
    private static final String GET_BALLS_T2 = "getBallsT2";
    private static final String GET_WICKET_T2 = "getWicketT2";
    private static final String GET_TEAM1STATE = "getTeam1State";
    private static final String GET_TEAM2STATE = "getTeam2State";

    private Button mZeroRunBtn, mOneRunBtn, mTwoRunBtn, mThreeRunBtn, mFourRunBtn, mSixRunBtn,
            mByesBtn, mLegByesBtn, mNoBallBtn, mWidesBtn, mWicketBtn, mStatsBtn, mUndoBtn;
    private TextView mTotal, mOverOverview, mOverAndBallLeft, mTeamName;
    private String wicketType[] = {WicketsType.CAUGHT.value, WicketsType.BOWLED.value, WicketsType.LBW.value,
            WicketsType.RUN_OUT.value, WicketsType.STUMPED.value, WicketsType.HIT_WICKET.value, WicketsType.RETIRED_HURT.value};
    private int byes, legByes, wideRuns, noBallRuns, wickets;
    private String byeType[] = {String.valueOf(Runs.RunsAvalible.SINGLE_RUN.value), String.valueOf(Runs.RunsAvalible.DOUBLE_RUN.value),
            String.valueOf(Runs.RunsAvalible.TRIPLE_RUN.value), String.valueOf(Runs.RunsAvalible.FOUR_RUN.value)};
    private String runsOfExtras[] = {String.valueOf(Runs.RunsAvalible.DOT_BALL.value), String.valueOf(Runs.RunsAvalible.SINGLE_RUN.value),
            String.valueOf(Runs.RunsAvalible.DOUBLE_RUN.value), String.valueOf(Runs.RunsAvalible.TRIPLE_RUN.value),
            String.valueOf(Runs.RunsAvalible.FOUR_RUN.value)};
    private final int extraRun = 1;
    private Innings mInnings;
    private boolean legalBalls, lastBallWicket;

    private int MAX_OVERS, MAX_PLAYER, runNeededToWin;
    private String team2Name;

    private SharedPreferences mSharedPreferences;

    public InningTwoActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inning2);

        // Show icon on top of actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent i = getIntent();
        runNeededToWin = i.getIntExtra("getRunNeededToWin", 0);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        MAX_OVERS = mSharedPreferences.getInt(GET_MAX_OVER, 5);
        MAX_PLAYER = mSharedPreferences.getInt(GET_MAX_PLAYER, 11);
        team2Name = mSharedPreferences.getString(GET_TEAM2_NAME, "Inning 2");

        mInnings = new Innings(team2Name, MAX_OVERS, MAX_PLAYER, true);
        // true because team 2 is chasing team 1 runs
        mInnings.setRunsNeededToWin(runNeededToWin);

        //Toast.makeText(this, MAX_OVERS, Toast.LENGTH_SHORT).show();

        mZeroRunBtn = (Button) this.findViewById(R.id.zero_run);
        mZeroRunBtn.setOnClickListener(this);
        mOneRunBtn = (Button) this.findViewById(R.id.one_run);
        mOneRunBtn.setOnClickListener(this);
        mTwoRunBtn = (Button) this.findViewById(R.id.two_run);
        mTwoRunBtn.setOnClickListener(this);
        mThreeRunBtn = (Button) this.findViewById(R.id.three_run);
        mThreeRunBtn.setOnClickListener(this);
        mFourRunBtn = (Button) this.findViewById(R.id.four_run);
        mFourRunBtn.setOnClickListener(this);
        mSixRunBtn = (Button) this.findViewById(R.id.six_run);
        mSixRunBtn.setOnClickListener(this);
        mByesBtn = (Button) this.findViewById(R.id.byes);
        mByesBtn.setOnClickListener(this);
        mLegByesBtn = (Button) this.findViewById(R.id.leg_byes);
        mLegByesBtn.setOnClickListener(this);
        mNoBallBtn = (Button) this.findViewById(R.id.no_ball);
        mNoBallBtn.setOnClickListener(this);
        mWidesBtn = (Button) this.findViewById(R.id.wides);
        mWidesBtn.setOnClickListener(this);
        mWicketBtn = (Button) this.findViewById(R.id.wicket);
        mWicketBtn.setOnClickListener(this);
        mStatsBtn = (Button) this.findViewById(R.id.stats);
        mStatsBtn.setOnClickListener(this);
        mUndoBtn = (Button) this.findViewById(R.id.undo);
        mUndoBtn.setOnClickListener(this);
        mUndoBtn.setEnabled(false);
        mStatsBtn.setEnabled(false);

        mTotal = (TextView) this.findViewById(R.id.score_overview);
        mOverOverview = (TextView) this.findViewById(R.id.this_over);
        mOverAndBallLeft = (TextView) this.findViewById(R.id.ball_overview);
        mTeamName = (TextView) this.findViewById(R.id.Team2_name);
        mTeamName.setText("Inning 2 of " + team2Name);

        setTotal();
        //setBoardVisible(mInnings.hasInningStarted());

    }

    public void onClick(View v) {
        if(!mInnings.isInningDone()) {
            if (v.getId() == R.id.zero_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(Runs.RunsAvalible.DOT_BALL);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.DOT_BALL));
                    legalBalls = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.one_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(Runs.RunsAvalible.SINGLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.ONE));
                    legalBalls = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.two_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(Runs.RunsAvalible.DOUBLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.TWO));
                    legalBalls = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.three_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(Runs.RunsAvalible.TRIPLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.THREE));
                    legalBalls = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.four_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(Runs.RunsAvalible.FOUR_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.FOUR));
                    legalBalls = true;
                    lastBallWicket = false;
                    setTotal();
                }
            } else if (v.getId() == R.id.six_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(Runs.RunsAvalible.SIX_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.SIX));
                    legalBalls = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.byes) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Bye Runs")
                            .setItems(byeType, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            byes = 1;
                                            break;
                                        case 1:
                                            byes = 2;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.DOUBLE_RUN);
                                            break;
                                        case 2:
                                            byes = 3;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.TRIPLE_RUN);
                                            break;
                                        case 3:
                                            byes = 4;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(byes);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.BYES));
                                    legalBalls = true;
                                    lastBallWicket = false;
                                    setTotal();
                                    mUndoBtn.setEnabled(true);
                                }
                            })
                            .show();
                }
            } else if (v.getId() == R.id.leg_byes) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Leg Bye Runs")
                            .setItems(byeType, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            legByes = 1;
                                            break;
                                        case 1:
                                            legByes = 2;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.DOUBLE_RUN);
                                            break;
                                        case 2:
                                            legByes = 3;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.TRIPLE_RUN);
                                            break;
                                        case 3:
                                            legByes = 4;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(legByes);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.LEGBYES));
                                    legalBalls = true;
                                    lastBallWicket = false;
                                    setTotal();
                                    mUndoBtn.setEnabled(true);
                                }
                            })
                            .show();
                }
            } else if (v.getId() == R.id.wides) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Wide + Bye Runs")
                            .setItems(runsOfExtras, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            wideRuns = extraRun;
                                            break;
                                        case 1:
                                            wideRuns = extraRun + 1;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.SINGLE_RUN);
                                            break;
                                        case 2:
                                            wideRuns = extraRun + 2;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.DOUBLE_RUN);
                                            break;
                                        case 3:
                                            wideRuns = extraRun + 3;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.TRIPLE_RUN);
                                            break;
                                        case 4:
                                            wideRuns = extraRun + 4;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(wideRuns);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.WIDEBALL));
                                    legalBalls = false;
                                    lastBallWicket = false;
                                    setTotal();
                                    mUndoBtn.setEnabled(true);
                                }
                            })
                            .show();
                }
            } else if (v.getId() == R.id.no_ball) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("No Ball + Bye Runs")
                            .setItems(runsOfExtras, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            noBallRuns = extraRun;
                                            break;
                                        case 1:
                                            noBallRuns = extraRun + 1;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.SINGLE_RUN);
                                            break;
                                        case 2:
                                            noBallRuns = extraRun + 2;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.DOUBLE_RUN);
                                            break;
                                        case 3:
                                            noBallRuns = extraRun + 3;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.TRIPLE_RUN);
                                            break;
                                        case 4:
                                            noBallRuns = extraRun + 4;
                                            mInnings.setExtraRunOfBall(Runs.RunsAvalible.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(noBallRuns);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.NO_BALL));
                                    legalBalls = false;
                                    lastBallWicket = false;
                                    setTotal();
                                    mUndoBtn.setEnabled(true);
                                }
                            })
                            .show();
                }
            } else if (v.getId() == R.id.wicket) {
                if (!mInnings.isOverComplete()) {
                    new AlertDialog.Builder(this)
                            .setTitle("Type of Wicket")
                            .setItems(wicketType, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            mInnings.setFallenWicket(WicketsType.CAUGHT);
                                            break;
                                        case 1:
                                            mInnings.setFallenWicket(WicketsType.BOWLED);
                                            break;
                                        case 2:
                                            mInnings.setFallenWicket(WicketsType.LBW);
                                            break;
                                        case 3:
                                            mInnings.setFallenWicket(WicketsType.RUN_OUT);
                                            break;
                                        case 4:
                                            mInnings.setFallenWicket(WicketsType.STUMPED);
                                            break;
                                        case 5:
                                            mInnings.setFallenWicket(WicketsType.HIT_WICKET);
                                            break;
                                        case 6:
                                            mInnings.setFallenWicket(WicketsType.RETIRED_HURT);
                                            break;
                                    }
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.WICKET));
                                    legalBalls = true;
                                    lastBallWicket = true;
                                    setTotal();
                                    mUndoBtn.setEnabled(true);
                                }
                            }).show();
                }

            } else if (v.getId() == R.id.stats) {
                String[] mOvers = new String[MAX_OVERS];
                int[] mRunsOfOver = new int[MAX_OVERS];
                int[] mRunsAfterOver = new int[MAX_OVERS];
                for(int i = 0; i < MAX_OVERS; i++){
                    mOvers[i] = mInnings.getOverOverview(i, true);
                    mRunsOfOver[i] = mInnings.getRunsOfThatOver(i);
                    mRunsAfterOver[i] = mInnings.getRunsAfterOver(i);
                }
                int scoreAtInstant = mInnings.getTotalRunScored();
                int overAtInstant = mInnings.getCurrentOver();
                int ballsPlayedAtInstant = mInnings.getNumOfBallsPlayed();
                int wicketFallenAtInstant = mInnings.getCurrentNumOfWickets();

                SharedPreferences.Editor sfEditor = mSharedPreferences.edit();
                sfEditor.putInt(GET_SCORE_T2, scoreAtInstant);
                sfEditor.putInt(GET_OVER_T2, overAtInstant);
                sfEditor.putInt(GET_BALLS_T2, ballsPlayedAtInstant);
                sfEditor.putInt(GET_WICKET_T2, wicketFallenAtInstant);
                sfEditor.putBoolean(GET_TEAM2STATE, true);
                sfEditor.putBoolean(GET_TEAM1STATE, false);

                for(int x = 0; x < MAX_OVERS; x++) {
                    sfEditor.putString("overviewT2_" + x, mOvers[x]);
                    sfEditor.putInt("runsOfOverT2_" + x, mRunsOfOver[x]);
                    sfEditor.putInt("runsAfterOverT2_" + x, mRunsAfterOver[x]);
                }
                sfEditor.apply();

                Intent i = new Intent(this, StatsActivity.class);
                startActivity(i);
            } else if (v.getId() == R.id.undo) {
                mInnings.undo(mInnings.getCurrentOver(), legalBalls, lastBallWicket);
                lastBallWicket = false;
                legalBalls = false;
                setTotal();
                mUndoBtn.setEnabled(false);

            } else if (v.getId() == R.id.undo) {
                mInnings.undo(mInnings.getCurrentOver(), legalBalls, lastBallWicket);
                lastBallWicket = false;
                legalBalls = false;
                setTotal();
                mUndoBtn.setEnabled(false);

            }
        }
    }

    private void setTotal() {
        mStatsBtn.setEnabled(true);
        mTotal.setText(String.valueOf(mInnings.getTotalRunScored()) + "/" + mInnings.getCurrentNumOfWickets());
        mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver(), false));
        if(legalBalls){
            mInnings.legalBallsPlayed();
        }
        mOverAndBallLeft.setText(String.valueOf(mInnings.getCurrentOver()) + "." + String.valueOf(mInnings.getNumOfBallsPlayed()));
        mInnings.checkInningDone();
        if(mInnings.isInningDone()){
            inningCompletionPrompt();
        }else{
            if(mInnings.isOverComplete()) {
                ScrollView s = new ScrollView(this);
                LinearLayout vh = new LinearLayout(this);
                vh.setOrientation(LinearLayout.VERTICAL);
                s.addView(vh);
                vh.addView(new TextView(this));
                for (int i = 0; i < mInnings.getHowManyBallsBowled(mInnings.getCurrentOver()); i++) {
                    TextView t = new TextView(this);
                    t.setText("    Delivery " + (i + 1) + ": " + mInnings.getCertainBallOfOver(mInnings.getCurrentOver(), i));
                    t.setTextSize(20);
                    vh.addView(t);
                }
                vh.addView(new TextView(this));
                TextView x = new TextView(this);
                x.setText("                                                         MOVE TO NEXT OVER?");
                vh.addView(x);
                new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mInnings.setOverDone(true);
                        mTotal.setText(String.valueOf(mInnings.getTotalRunScored()) + "/" + mInnings.getCurrentNumOfWickets());
                        mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver() - 1, false));
                        mOverAndBallLeft.setText(String.valueOf(mInnings.getCurrentOver()) + "." + String.valueOf(mInnings.getNumOfBallsPlayed()));
                        legalBalls = false;
                        mUndoBtn.setEnabled(false);
                        mInnings.checkInningDone();
                        if (mInnings.isInningDone()) {
                            inningCompletionPrompt();
                        }

                    }
                }).setNegativeButton("Undo Last Ball", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mInnings.undo(mInnings.getCurrentOver(), legalBalls, lastBallWicket);
                        mInnings.setOverDone(false);
                        legalBalls = false;
                        setTotal();
                        mUndoBtn.setEnabled(false);
                    }
                }).setCancelable(false).show();
            }
        }


    }

    private void inningCompletionPrompt(){
        new AlertDialog.Builder(this)
                .setTitle("Inning Complete")
                .setMessage("Inning 2 Complete.\n")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setBoardVisible(false);
                    }
                }).setCancelable(false).show();
    }


    private void setBoardVisible(boolean visible){
        if(visible){
            mZeroRunBtn.setEnabled(true);
            mOneRunBtn.setEnabled(true);
            mTwoRunBtn.setEnabled(true);
            mThreeRunBtn.setEnabled(true);
            mFourRunBtn.setEnabled(true);
            mSixRunBtn.setEnabled(true);
            mByesBtn.setEnabled(true);
            mLegByesBtn.setEnabled(true);
            mNoBallBtn.setEnabled(true);
            mWidesBtn.setEnabled(true);
            mWicketBtn.setEnabled(true);
            mStatsBtn.setEnabled(true);
            mUndoBtn.setEnabled(true);
        }else{
            mZeroRunBtn.setEnabled(false);
            mOneRunBtn.setEnabled(false);
            mTwoRunBtn.setEnabled(false);
            mThreeRunBtn.setEnabled(false);
            mFourRunBtn.setEnabled(false);
            mSixRunBtn.setEnabled(false);
            mByesBtn.setEnabled(false);
            mLegByesBtn.setEnabled(false);
            mNoBallBtn.setEnabled(false);
            mWidesBtn.setEnabled(false);
            mWicketBtn.setEnabled(false);
            mStatsBtn.setEnabled(false);
            mUndoBtn.setEnabled(false);
        }
    }

    public void transferToNextInning(){
        String[] mOvers = new String[MAX_OVERS];
        int[] mRunsOfOver = new int[MAX_OVERS];
        int[] mRunsAfterOver = new int[MAX_OVERS];
        for(int i = 0; i < MAX_OVERS; i++){
            mOvers[i] = mInnings.getOverOverview(i, true);
            mRunsOfOver[i] = mInnings.getRunsOfThatOver(i);
            mRunsAfterOver[i] = mInnings.getRunsAfterOver(i);
        }
        int scoreAtInstant = mInnings.getTotalRunScored();
        int overAtInstant = mInnings.getCurrentOver();
        int ballsPlayedAtInstant = mInnings.getNumOfBallsPlayed();
        int wicketFallenAtInstant = mInnings.getCurrentNumOfWickets();

        SharedPreferences.Editor sfEditor = mSharedPreferences.edit();
        sfEditor.putInt(GET_SCORE_T2, scoreAtInstant);
        sfEditor.putInt(GET_OVER_T2, overAtInstant);
        sfEditor.putInt(GET_BALLS_T2, ballsPlayedAtInstant);
        sfEditor.putInt(GET_WICKET_T2, wicketFallenAtInstant);
        sfEditor.putBoolean(GET_TEAM2STATE, true);
        sfEditor.putBoolean(GET_TEAM1STATE, false);

        for(int x = 0; x < MAX_OVERS; x++) {
            sfEditor.putString("overviewT2_" + x, mOvers[x]);
            sfEditor.putInt("runsOfOverT2_" + x, mRunsOfOver[x]);
            sfEditor.putInt("runsAfterOverT2_" + x, mRunsAfterOver[x]);
        }
        sfEditor.apply();

        Intent i = new Intent(this, StatsActivity.class);
        startActivity(i);
    }

}
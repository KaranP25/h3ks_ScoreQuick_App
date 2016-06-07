package com.h3k.scorequick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class InningOneActivity extends AppCompatActivity implements View.OnClickListener {
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


    private int MAX_OVERS, MAX_PLAYER;
    private String TEAM_1NAME, TEAM_2NAME;



    public InningOneActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inning1);

        Bundle bundle = this.getIntent().getExtras();
        MAX_OVERS = bundle.getInt("getOvers");
        MAX_PLAYER = bundle.getInt("get");
        TEAM_1NAME = bundle.getString("getNameTeam1");
        TEAM_2NAME = bundle.getString("getNameTeam2");

        mInnings = new Innings(TEAM_1NAME, MAX_OVERS, MAX_PLAYER);

        //Toast.makeText(getContext(), MAX_OVERS, Toast.LENGTH_SHORT).show();

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

        mTotal = (TextView) this.findViewById(R.id.score_overview);
        mOverOverview = (TextView) this.findViewById(R.id.this_over);
        mOverAndBallLeft = (TextView) this.findViewById(R.id.ball_overview);
        mTeamName = (TextView) this.findViewById(R.id.Team1_name);
        mTeamName.setText(TEAM_1NAME);

        mTotal.setText(String.valueOf(MAX_OVERS));
        mOverOverview.setText("");
        mOverAndBallLeft.setText("");

        setTotal();
        //setBoardVisible(mInnings.hasInningStarted());

    }

    public void onClick(View v) {
        if(!mInnings.isInningDone()) {
            if (v.getId() == R.id.zero_run) {
                mInnings.setRunScored(Runs.RunsAvalible.DOT_BALL);
                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.DOT_BALL));
                legalBalls = true;
                lastBallWicket = false;
                setTotal();
                mUndoBtn.setEnabled(true);
            } else if (v.getId() == R.id.one_run) {
                mInnings.setRunScored(Runs.RunsAvalible.SINGLE_RUN);
                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.ONE));
                legalBalls = true;
                lastBallWicket = false;
                setTotal();
                mUndoBtn.setEnabled(true);
            } else if (v.getId() == R.id.two_run) {
                mInnings.setRunScored(Runs.RunsAvalible.DOUBLE_RUN);
                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.TWO));
                legalBalls = true;
                lastBallWicket = false;
                setTotal();
                mUndoBtn.setEnabled(true);
            } else if (v.getId() == R.id.three_run) {
                mInnings.setRunScored(Runs.RunsAvalible.TRIPLE_RUN);
                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.THREE));
                legalBalls = true;
                lastBallWicket = false;
                setTotal();
                mUndoBtn.setEnabled(true);
            } else if (v.getId() == R.id.four_run) {
                mInnings.setRunScored(Runs.RunsAvalible.FOUR_RUN);
                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.FOUR));
                legalBalls = true;
                lastBallWicket = false;
                setTotal();
            } else if (v.getId() == R.id.six_run) {
                mInnings.setRunScored(Runs.RunsAvalible.SIX_RUN);
                mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.SIX));
                legalBalls = true;
                lastBallWicket = false;
                setTotal();
                mUndoBtn.setEnabled(true);
            } else if (v.getId() == R.id.byes) {
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

            } else if (v.getId() == R.id.leg_byes) {
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
            } else if (v.getId() == R.id.wides) {
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
            } else if (v.getId() == R.id.no_ball) {
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
            } else if (v.getId() == R.id.wicket) {
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

            } else if (v.getId() == R.id.stats) {
                String[] mOvers = new String[MAX_OVERS];
                for(int i = 0; i < MAX_OVERS; i++){
                    mOvers[i] = mInnings.getOverOverview(i, true);
                }
                int scoreAtInstant = mInnings.getTotalRunScored();
                int overAtInstant = mInnings.getCurrentOver();
                int ballsPlayedAtInstant = mInnings.getNumOfBallsPlayed();
                int wicketFallenAtInstant = mInnings.getCurrentNumOfWickets();

                Intent i = new Intent(this, StatsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("getTeam1Name", TEAM_1NAME);
                bundle.putString("getTeam2Name", TEAM_2NAME);
                bundle.putStringArray("overOverviewT1", mOvers);
                bundle.putInt("getScoreT1", scoreAtInstant);
                bundle.putInt("getOverT1", overAtInstant);
                bundle.putInt("getBallsT1", ballsPlayedAtInstant);
                bundle.putInt("getWicketT1", wicketFallenAtInstant);
                bundle.putBoolean("getTeam1State", true);
                bundle.putBoolean("getTeam2State", false);
                i.putExtras(bundle);

                startActivity(i);
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
        mTotal.setText(String.valueOf(mInnings.getTotalRunScored()) + "/" + mInnings.getCurrentNumOfWickets());
        mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver(), false));
        if(legalBalls){
            mInnings.leagalBallsPlayed();
        }
        mOverAndBallLeft.setText(String.valueOf(mInnings.getCurrentOver()) + "." + String.valueOf(mInnings.getNumOfBallsPlayed()));
        if(mInnings.isOverComplete()){
            ScrollView s = new ScrollView(InningOneActivity.this);
            LinearLayout vh = new LinearLayout(InningOneActivity.this);
            vh.setOrientation(LinearLayout.VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mInnings.getHowManyBallsBowled(mInnings.getCurrentOver()); i++){
                TextView t = new TextView(this);
                t.setText("    Delivery " + (i + 1) + ": " + mInnings.getCertainBallOfOver(mInnings.getCurrentOver(), i));
                t.setTextSize(20);
                vh.addView(t);
            }
            vh.addView(new TextView(this));
            TextView x = new TextView(this);
            x.setText("                                                         MOVE TO NEXT OVER?");
            vh.addView(x);
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mInnings.setOverDone(true);
                    mTotal.setText(String.valueOf(mInnings.getTotalRunScored()) + "/" + mInnings.getCurrentNumOfWickets());
                    mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver() - 1, false));
                    mOverAndBallLeft.setText(String.valueOf(mInnings.getCurrentOver()) + "." + String.valueOf(mInnings.getNumOfBallsPlayed()));
                    legalBalls = false;
                    mUndoBtn.setEnabled(false);
                    mInnings.checkInningDone();
                    if(mInnings.isInningDone()){
                        inningCompletionPrompt();
                    }

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener(){
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

    private void inningCompletionPrompt(){
        new AlertDialog.Builder(this)
                .setTitle("Inning Complete")
                .setMessage("Inning 1 Complete.\nDo you want to start next Inning?")
            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setBoardVisible(false);
                transferToNextInning();
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
        Intent i = new Intent(this, InningTwoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("getTeam2Name", TEAM_2NAME);
        bundle.putInt("getMaxOver", MAX_OVERS);
        bundle.putInt("getMaxPlayers", MAX_PLAYER);
        i.putExtras(bundle);

        startActivity(i);

    }

}
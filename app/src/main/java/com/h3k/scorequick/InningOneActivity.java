package com.h3k.scorequick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * This class is used for first inning of the game.
 * @author Karan P., Karan J., Kalpit, Harsh
 */
public class InningOneActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String GET_TEAM1_NAME = "getTeam1Name";
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private static final String GET_SCORE_T1 = "getScoreT1";
    private static final String GET_OVER_T1 = "getOverT1";
    private static final String GET_BALLS_T1 = "getBallsT1";
    private static final String GET_WICKET_T1 = "getWicketT1";
    private static final String GET_TEAM1STATE = "getTeam1State";
    private static final String GET_TEAM2STATE = "getTeam2State";


    private Button mZeroRunBtn, mOneRunBtn, mTwoRunBtn, mThreeRunBtn, mFourRunBtn, mSixRunBtn,
            mByesBtn, mLegByesBtn, mNoBallBtn, mWidesBtn, mWicketBtn, mStatsBtn, mUndoBtn;
    private TextView mTotal, mOverOverview, mOverAndBallLeft, mTeamName;
    private String wicketType[] = {WicketsType.CAUGHT.value, WicketsType.BOWLED.value, WicketsType.LBW.value,
            WicketsType.RUN_OUT.value, WicketsType.STUMPED.value, WicketsType.HIT_WICKET.value, WicketsType.RETIRED_HURT.value};
    private int byes, legByes, wideRuns, noBallRuns, wickets;
    private String byeType[] = {String.valueOf(RunsAvailable.SINGLE_RUN.value), String.valueOf(RunsAvailable.DOUBLE_RUN.value),
            String.valueOf(RunsAvailable.TRIPLE_RUN.value), String.valueOf(RunsAvailable.FOUR_RUN.value)};
    private String runsOfExtras[] = {String.valueOf(RunsAvailable.DOT_BALL.value), String.valueOf(RunsAvailable.SINGLE_RUN.value),
            String.valueOf(RunsAvailable.DOUBLE_RUN.value), String.valueOf(RunsAvailable.TRIPLE_RUN.value),
            String.valueOf(RunsAvailable.FOUR_RUN.value)};
    private final int extraRun = 1;
    private Innings mInnings;
    private boolean legalBall, lastBallWicket;
    private int MAX_OVERS, MAX_PLAYER;
    private String team1Name;
    private SharedPreferences mSharedPreferences;

    /**
     * Constructor of InningOneActivity
     */
    public InningOneActivity() {
    }

    /**
     * Method called at runtime.
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inning1);

        // Show icon on top of actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        MAX_OVERS = mSharedPreferences.getInt(GET_MAX_OVER, 5);
        MAX_PLAYER = mSharedPreferences.getInt(GET_MAX_PLAYER, 11);
        team1Name = mSharedPreferences.getString(GET_TEAM1_NAME, "Inning 1");
        mInnings = new Innings(team1Name, MAX_OVERS, MAX_PLAYER, false);
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
        mTeamName = (TextView) this.findViewById(R.id.Team1_name);
        mTeamName.setText("Inning 1 of " + team1Name);

        setTotal();
    }

    /**
     * This method performs specific tasks on button press.
     *
     * @param v
     */
    public void onClick(View v) {
        if (!mInnings.isInningDone()) {
            if (v.getId() == R.id.zero_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(RunsAvailable.DOT_BALL);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.DOT_BALL));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.one_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(RunsAvailable.SINGLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.ONE));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.two_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(RunsAvailable.DOUBLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.TWO));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.three_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(RunsAvailable.TRIPLE_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.THREE));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                    mUndoBtn.setEnabled(true);
                }
            } else if (v.getId() == R.id.four_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(RunsAvailable.FOUR_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.FOUR));
                    legalBall = true;
                    lastBallWicket = false;
                    setTotal();
                }
            } else if (v.getId() == R.id.six_run) {
                if (!mInnings.isOverComplete()) {
                    mInnings.setRunScored(RunsAvailable.SIX_RUN);
                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.SIX));
                    legalBall = true;
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
                                            mInnings.setExtraRunOfBall(RunsAvailable.DOUBLE_RUN);
                                            break;
                                        case 2:
                                            byes = 3;
                                            mInnings.setExtraRunOfBall(RunsAvailable.TRIPLE_RUN);
                                            break;
                                        case 3:
                                            byes = 4;
                                            mInnings.setExtraRunOfBall(RunsAvailable.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(byes);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.BYES));
                                    legalBall = true;
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
                                            mInnings.setExtraRunOfBall(RunsAvailable.DOUBLE_RUN);
                                            break;
                                        case 2:
                                            legByes = 3;
                                            mInnings.setExtraRunOfBall(RunsAvailable.TRIPLE_RUN);
                                            break;
                                        case 3:
                                            legByes = 4;
                                            mInnings.setExtraRunOfBall(RunsAvailable.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(legByes);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.LEGBYES));
                                    legalBall = true;
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
                                            mInnings.setExtraRunOfBall(RunsAvailable.SINGLE_RUN);
                                            break;
                                        case 2:
                                            wideRuns = extraRun + 2;
                                            mInnings.setExtraRunOfBall(RunsAvailable.DOUBLE_RUN);
                                            break;
                                        case 3:
                                            wideRuns = extraRun + 3;
                                            mInnings.setExtraRunOfBall(RunsAvailable.TRIPLE_RUN);
                                            break;
                                        case 4:
                                            wideRuns = extraRun + 4;
                                            mInnings.setExtraRunOfBall(RunsAvailable.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(wideRuns);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.WIDEBALL));
                                    legalBall = false;
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
                                            mInnings.setExtraRunOfBall(RunsAvailable.SINGLE_RUN);
                                            break;
                                        case 2:
                                            noBallRuns = extraRun + 2;
                                            mInnings.setExtraRunOfBall(RunsAvailable.DOUBLE_RUN);
                                            break;
                                        case 3:
                                            noBallRuns = extraRun + 3;
                                            mInnings.setExtraRunOfBall(RunsAvailable.TRIPLE_RUN);
                                            break;
                                        case 4:
                                            noBallRuns = extraRun + 4;
                                            mInnings.setExtraRunOfBall(RunsAvailable.FOUR_RUN);
                                            break;
                                    }
                                    mInnings.setExtraRuns(noBallRuns);
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.NO_BALL));
                                    legalBall = false;
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
                                    mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(TypeOfBalls.WICKET));
                                    legalBall = true;
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
                for (int i = 0; i < MAX_OVERS; i++) {
                    mOvers[i] = mInnings.getOverOverview(i, true);
                    mRunsOfOver[i] = mInnings.getRunsOfThatOver(i);
                    mRunsAfterOver[i] = mInnings.getRunsAfterOver(i);
                }
                int scoreAtInstant = mInnings.getTotalRunScored();
                int overAtInstant = mInnings.getCurrentOver();
                int ballsPlayedAtInstant = mInnings.getNumOfBallsPlayed();
                int wicketFallenAtInstant = mInnings.getCurrentNumOfWickets();

                SharedPreferences.Editor sfEditor = mSharedPreferences.edit();
                sfEditor.putInt(GET_SCORE_T1, scoreAtInstant);
                sfEditor.putInt(GET_OVER_T1, overAtInstant);
                sfEditor.putInt(GET_BALLS_T1, ballsPlayedAtInstant);
                sfEditor.putInt(GET_WICKET_T1, wicketFallenAtInstant);
                sfEditor.putBoolean(GET_TEAM1STATE, true);
                sfEditor.putBoolean(GET_TEAM2STATE, false);

                for (int x = 0; x < MAX_OVERS; x++) {
                    sfEditor.putString("overviewT1_" + x, mOvers[x]);
                    sfEditor.putInt("runsOfOverT1_" + x, mRunsOfOver[x]);
                    sfEditor.putInt("runsAfterOverT1_" + x, mRunsAfterOver[x]);
                }
                sfEditor.apply();

                Intent i = new Intent(this, StatsActivity.class);
                startActivity(i);
            } else if (v.getId() == R.id.undo) {
                mInnings.undo(mInnings.getCurrentOver(), legalBall, lastBallWicket);
                lastBallWicket = false;
                legalBall = false;
                setTotal();
                mUndoBtn.setEnabled(false);

            }
        }
    }

    /**
     * This method displays the total score on the InningOneActivity.
     */
    private void setTotal() {
        mStatsBtn.setEnabled(true);
        mTotal.setText(String.valueOf(mInnings.getTotalRunScored()) + "/" + mInnings.getCurrentNumOfWickets());
        mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver(), false));
        if (legalBall) {
            mInnings.legalBallsPlayed();
        }
        mOverAndBallLeft.setText(String.valueOf(mInnings.getCurrentOver()) + "." + String.valueOf(mInnings.getNumOfBallsPlayed()));
        if (mInnings.isInningDone()) {
            inningCompletionPrompt(false);
        } else if (mInnings.isOverComplete()) {
            ScrollView s = new ScrollView(InningOneActivity.this);
            s.setPadding(100, 0, 100, 0);
            LinearLayout vh = new LinearLayout(InningOneActivity.this);
            vh.setOrientation(LinearLayout.VERTICAL);
            vh.setGravity(Gravity.CENTER_VERTICAL);
            s.addView(vh);
            vh.addView(new TextView(this));
            for (int i = 0; i < mInnings.getHowManyBallsBowled(mInnings.getCurrentOver()); i++) {
                TextView t = new TextView(this);
                t.setText("Delivery " + (i + 1) + ": " + mInnings.getCertainBallOfOver(mInnings.getCurrentOver(), i));
                t.setTextSize(20);
                vh.addView(t);
            }
            vh.addView(new TextView(this));
            TextView x = new TextView(this);
            x.setText("Over Complete");
            x.setTextSize(20);
            x.setTypeface(null, Typeface.BOLD);
            x.setGravity(Gravity.RIGHT);
            vh.addView(x);
            new AlertDialog.Builder(this).setView(s).setTitle("This Over").setPositiveButton("Next Over", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mInnings.setOverDone(true);
                    mTotal.setText(String.valueOf(mInnings.getTotalRunScored()) + "/" + mInnings.getCurrentNumOfWickets());
                    mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver() - 1, false));
                    mOverAndBallLeft.setText(String.valueOf(mInnings.getCurrentOver()) + "." + String.valueOf(mInnings.getNumOfBallsPlayed()));
                    legalBall = false;
                    mUndoBtn.setEnabled(false);
                    if (mInnings.isInningDone()) {
                        inningCompletionPrompt(true);
                    }

                }
            }).setNegativeButton("Undo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mInnings.undo(mInnings.getCurrentOver(), legalBall, lastBallWicket);
                    mInnings.setOverDone(false);
                    legalBall = false;
                    setTotal();
                    mUndoBtn.setEnabled(false);
                }
            }).setCancelable(false).show();
        }
    }



    /**
     * This method checks if the back button is pressed.
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.support.v7.app.AlertDialog.Builder(InningOneActivity.this).setTitle("Exit Application?")
                    .setMessage("Do you want to quit this application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent close = new Intent(Intent.ACTION_MAIN);
                            close.addCategory(Intent.CATEGORY_HOME);
                            close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            onDestroy();
                            startActivity(close);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * This method exits the app.
     */
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }

    /**
     * This method prompts user when the inning has been completed.
     */
    private void inningCompletionPrompt(boolean wasOverComplete) {
        if (wasOverComplete) {
            new AlertDialog.Builder(this)
                    .setTitle("Inning Complete")
                    .setMessage("Completion of Inning 1")
                    .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setBoardVisible(false);
                            transferData();
                        }
                    }).setCancelable(false).show();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Inning Complete")
                    .setMessage("Completion of Inning 1")
                    .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setBoardVisible(false);
                            transferData();
                        }
                    })
                    .setNegativeButton("Undo Last Delivery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mInnings.undo(mInnings.getCurrentOver(), legalBall, lastBallWicket);
                            lastBallWicket = false;
                            legalBall = false;
                            setTotal();
                            mUndoBtn.setEnabled(false);
                        }
                    }).setCancelable(false).show();
        }

    }

    /**
     * This methods sets the visiblity of all the buttons.
     *
     * @param visible
     */
    private void setBoardVisible(boolean visible) {
        if (visible) {
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
        } else {
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

    /**
     * This method transfers the variables and its values into SharedPreferences
     * and calls the next activity.
     */
    public void transferData() {
        String[] mOvers = new String[MAX_OVERS];
        int[] mRunsOfOver = new int[MAX_OVERS];
        int[] mRunsAfterOver = new int[MAX_OVERS];
        for (int i = 0; i < MAX_OVERS; i++) {
            mOvers[i] = mInnings.getOverOverview(i, true);
            mRunsOfOver[i] = mInnings.getRunsOfThatOver(i);
            mRunsAfterOver[i] = mInnings.getRunsAfterOver(i);
        }
        int scoreAtInstant = mInnings.getTotalRunScored();
        int overAtInstant = mInnings.getCurrentOver();
        int ballsPlayedAtInstant = mInnings.getNumOfBallsPlayed();
        int wicketFallenAtInstant = mInnings.getCurrentNumOfWickets();

        SharedPreferences.Editor sfEditor = mSharedPreferences.edit();
        sfEditor.putInt(GET_SCORE_T1, scoreAtInstant);
        sfEditor.putInt(GET_OVER_T1, overAtInstant);
        sfEditor.putInt(GET_BALLS_T1, ballsPlayedAtInstant);
        sfEditor.putInt(GET_WICKET_T1, wicketFallenAtInstant);
        sfEditor.putBoolean(GET_TEAM1STATE, true);
        sfEditor.putBoolean(GET_TEAM2STATE, false);

        for (int x = 0; x < MAX_OVERS; x++) {
            sfEditor.putString("overviewT1_" + x, mOvers[x]);
            sfEditor.putInt("runsOfOverT1_" + x, mRunsOfOver[x]);
            sfEditor.putInt("runsAfterOverT1_" + x, mRunsAfterOver[x]);
        }
        sfEditor.apply();

        Intent i = new Intent(this, InningTwoActivity.class);
        i.putExtra("runsMadeInInning1", mInnings.getTotalRunScored());
        i.putExtra("totalBallsBowledInning1", mInnings.getTotalBallsBowled());
        i.putExtra("wicketsInning1", mInnings.getTotalBallsBowled());
        startActivity(i);

    }

}
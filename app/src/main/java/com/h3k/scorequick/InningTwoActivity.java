package com.h3k.scorequick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class InningTwoActivity extends Fragment implements View.OnClickListener {
    private Button mZeroRunBtn, mOneRunBtn, mTwoRunBtn, mThreeRunBtn, mFourRunBtn, mSixRunBtn, mByesBtn, mLegByesBtn, mNoBallBtn, mWidesBtn, mWicketBtn;
    private TextView mTotal, mOverOverview, mOverAndBallLeft;
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

    public InningTwoActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_inning2, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        final int MAX_OVERS = bundle.getInt("getOvers");
        final int MAX_PLAYER = bundle.getInt("get");
        final String TEAM_2 = bundle.getString("getNameTeam2");

        mInnings = new Innings(TEAM_2, MAX_OVERS, MAX_PLAYER);

        //Toast.makeText(getContext(), MAX_OVERS, Toast.LENGTH_SHORT).show();

        mZeroRunBtn = (Button) view.findViewById(R.id.zero_run);
        mZeroRunBtn.setOnClickListener(this);
        mOneRunBtn = (Button) view.findViewById(R.id.one_run);
        mOneRunBtn.setOnClickListener(this);
        mTwoRunBtn = (Button) view.findViewById(R.id.two_run);
        mTwoRunBtn.setOnClickListener(this);
        mThreeRunBtn = (Button) view.findViewById(R.id.three_run);
        mThreeRunBtn.setOnClickListener(this);
        mFourRunBtn = (Button) view.findViewById(R.id.four_run);
        mFourRunBtn.setOnClickListener(this);
        mSixRunBtn = (Button) view.findViewById(R.id.six_run);
        mSixRunBtn.setOnClickListener(this);
        mByesBtn = (Button) view.findViewById(R.id.byes);
        mByesBtn.setOnClickListener(this);
        mLegByesBtn = (Button) view.findViewById(R.id.leg_byes);
        mLegByesBtn.setOnClickListener(this);
        mNoBallBtn = (Button) view.findViewById(R.id.no_ball);
        mNoBallBtn.setOnClickListener(this);
        mWidesBtn = (Button) view.findViewById(R.id.wides);
        mWidesBtn.setOnClickListener(this);
        mWicketBtn = (Button) view.findViewById(R.id.wicket);
        mWicketBtn.setOnClickListener(this);

        mTotal = (TextView) view.findViewById(R.id.score_overview);
        mOverOverview = (TextView) view.findViewById(R.id.this_over);
        mOverAndBallLeft = (TextView) view.findViewById(R.id.ball_overview);

        setTotal();

        return view;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.zero_run) {
            mInnings.setRunScored(Runs.RunsAvalible.DOT_BALL);
            mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.DOT_BALL));
            mInnings.leagalBallsPlayed();
            setTotal();
        } else if (v.getId() == R.id.one_run) {
            mInnings.setRunScored(Runs.RunsAvalible.SINGLE_RUN);
            mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.ONE));
            mInnings.leagalBallsPlayed();
            setTotal();
        } else if (v.getId() == R.id.two_run) {
            mInnings.setRunScored(Runs.RunsAvalible.DOUBLE_RUN);
            mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.TWO));
            mInnings.leagalBallsPlayed();
            setTotal();
        } else if (v.getId() == R.id.three_run) {
            mInnings.setRunScored(Runs.RunsAvalible.TRIPLE_RUN);
            mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.THREE));
            mInnings.leagalBallsPlayed();
            setTotal();
        } else if (v.getId() == R.id.four_run) {
            mInnings.setRunScored(Runs.RunsAvalible.FOUR_RUN);
            mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.FOUR));
            mInnings.leagalBallsPlayed();
            setTotal();
        } else if (v.getId() == R.id.six_run) {
            mInnings.setRunScored(Runs.RunsAvalible.SIX_RUN);
            mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.SIX));
            mInnings.leagalBallsPlayed();
            setTotal();
        }else if (v.getId() == R.id.byes) {
            new AlertDialog.Builder(getContext())
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
                            mInnings.leagalBallsPlayed();
                            setTotal();
                        }
                    })
                    .show();

        } else if (v.getId() == R.id.leg_byes) {
            new AlertDialog.Builder(getContext())
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
                            mInnings.leagalBallsPlayed();
                            setTotal();
                        }
                    })
                    .show();
        }else if (v.getId() == R.id.wides){
            new AlertDialog.Builder(getContext())
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
                            setTotal();
                        }
                    })
                    .show();
        }else if (v.getId() == R.id.no_ball) {
            new AlertDialog.Builder(getContext())
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
                            setTotal();
                        }
                    })
                    .show();
        }else if (v.getId() == R.id.wicket) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Type of Wicket")
                    .setItems(wicketType, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    mInnings.setFallenWicket(WicketsType.CAUGHT);
                                    wickets++;
                                    break;
                                case 1:
                                    mInnings.setFallenWicket(WicketsType.BOWLED);
                                    wickets++;
                                    break;
                                case 2:
                                    mInnings.setFallenWicket(WicketsType.LBW);
                                    wickets++;
                                    break;
                                case 3:
                                    mInnings.setFallenWicket(WicketsType.RUN_OUT);
                                    wickets++;
                                    break;
                                case 4:
                                    mInnings.setFallenWicket(WicketsType.STUMPED);
                                    wickets++;
                                    break;
                                case 5:
                                    mInnings.setFallenWicket(WicketsType.HIT_WICKET);
                                    wickets++;
                                    break;
                                case 6:
                                    mInnings.setFallenWicket(WicketsType.RETIRED_HURT);
                                    wickets++;
                                    break;
                            }
                            mInnings.setThisOverOverview(mInnings.getCurrentOver(), new BallBowled(BallBowled.TypeOfBalls.WICKET));
                            mInnings.leagalBallsPlayed();
                            setTotal();
                        }
                    }).show();
        }
    }

    private void setBoardVisible(boolean visible){
        if(visible){

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
            //showDialoguess();
        }
    }

    private void setTotal() {
        mTotal.setText(String.valueOf(mInnings.getTotalRunScored()) + "/" + wickets);
        mOverOverview.setText("This Over: " + mInnings.getOverOverview(mInnings.getCurrentOver()));
        mOverAndBallLeft.setText(String.valueOf(mInnings.getCurrentOver()) + "." + String.valueOf(mInnings.getNumOfBallsPlayed()));
    }

}
package com.h3k.scorequick;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to get the settings of the game (# of players, # of wickets etc...)
 * @author Karan P., Karan J., Kalpit, Harsh
 */
public class SettingActivity extends AppCompatActivity {

    private static final String GET_TEAM1_NAME = "getTeam1Name";
    private static final String GET_TEAM2_NAME = "getTeam2Name";
    private static final String GET_MAX_OVER = "getMaxOvers";
    private static final String GET_MAX_PLAYER = "getMaxPlayers";
    private EditText overs, players, nameTeam1, nameTeam2;
    private Toast mToast;
    private boolean noBack;


    /**
     * Constructor of SettingsActivity
     */
    public SettingActivity() {
    }

    /**
     * This method is called at runtime.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent i = getIntent();
        noBack = i.getBooleanExtra("newStart", false);

        // Show icon on top of actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final int MAX_INPUT_LENGTH = 12;

        overs = (EditText) findViewById(R.id.overs);
        players = (EditText) findViewById(R.id.num_total_player);
        nameTeam1 = (EditText) findViewById(R.id.team1_txtF);
        nameTeam1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LENGTH)}); // limits input lenth
        nameTeam2 = (EditText) findViewById(R.id.team2_txtF);
        nameTeam2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LENGTH)}); // limits input lenth


        nameTeam1.setInputType(InputType.TYPE_CLASS_TEXT); // enables the Text keyboard
        nameTeam2.setInputType(InputType.TYPE_CLASS_TEXT); // enables the Text keyboard
        overs.setInputType(InputType.TYPE_CLASS_NUMBER); // enables the number keyboard
        players.setInputType(InputType.TYPE_CLASS_NUMBER); // enables the number keyboard

        nameTeam1.setImeOptions(EditorInfo.IME_ACTION_NEXT); // setting next button in keyboard
        nameTeam2.setImeOptions(EditorInfo.IME_ACTION_NEXT); // setting next button in keyboard
        overs.setImeOptions(EditorInfo.IME_ACTION_NEXT); // setting next button in keyboard
        players.setImeOptions(EditorInfo.IME_ACTION_DONE); // setting done button in keyboard
        message("");
        nameTeam1.requestFocus();


        nameTeam1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && nameTeam1.getText().length() == 0) {
                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            mToast.cancel();
                            nameTeam1.requestFocus();
                            nameTeam2.clearFocus();
                            message("Please write the name of the team that will play Inning 1...");
                            mToast.show();
                        }
                    }, 0);
                }
            }
        });

        nameTeam2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && nameTeam2.getText().length() == 0 && nameTeam1.getText().length() != 0) {
                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            mToast.cancel();
                            nameTeam2.requestFocus();
                            overs.clearFocus();
                            message("Please write the name of the team that will play Inning 2...");
                            mToast.show();
                        }
                    }, 0);
                }
            }
        });
        overs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (overs.getText().length() == 0 || Integer.valueOf(overs.getText().toString()) == 0)
                        && nameTeam2.getText().length() != 0) {

                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            mToast.cancel();
                            overs.requestFocus();
                            players.clearFocus();
                            if (overs.getText().length() == 0) {
                                message("Please enter the number of overs being played...");
                            } else if (Integer.valueOf(overs.getText().toString()) == 0) {
                                message("You can never play 0 overs");
                                ;
                            }
                            mToast.show();
                        }
                    }, 0);
                }

            }
        });


        players.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    if (actionId == EditorInfo.IME_ACTION_DONE &&
                            Integer.valueOf(players.getText().toString()) != 0 &&
                            Integer.valueOf(players.getText().toString()) != 1 &&
                            Integer.valueOf(players.getText().toString()) <= 11) {
                        Intent i = new Intent(SettingActivity.this, InningOneActivity.class);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor sfEditor = sharedPreferences.edit();

                        sfEditor.putString(GET_TEAM1_NAME, nameTeam1.getText().toString().toUpperCase());
                        sfEditor.putString(GET_TEAM2_NAME, nameTeam2.getText().toString().toUpperCase());
                        sfEditor.putInt(GET_MAX_OVER, Integer.valueOf(overs.getText().toString()));
                        sfEditor.putInt(GET_MAX_PLAYER, Integer.valueOf(players.getText().toString()));
                        sfEditor.apply();

                        startActivity(i);

                        //onBackPressed();
                        return true;
                    } else if (Integer.valueOf(players.getText().toString()) == 0 || Integer.valueOf(players.getText().toString()) == 1) {
                        mToast.cancel();
                        message("You need more people");
                        mToast.show();
                    } else if (Integer.valueOf(players.getText().toString()) > 11) {
                        mToast.cancel();
                        message("MAX 11 PLAYERS");
                        mToast.show();
                    }
                } catch (NumberFormatException e) {
                    mToast.cancel();
                    message("Fields cannot be empty");
                    mToast.show();
                }
                return false;
            }
        });
    }

    /**
     * This method checks if back button is pressed.
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(noBack){
                new AlertDialog.Builder(SettingActivity.this).setTitle("Error...")
                        .setMessage("Cant Go back").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }else {
                new AlertDialog.Builder(SettingActivity.this).setTitle("Exit Application?")
                        .setMessage("Do you want to quit this application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        }
        return true;
    }

    /**
     * This method displays the toast.
     * @param message
     */
    private void message(String message) {
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }
}


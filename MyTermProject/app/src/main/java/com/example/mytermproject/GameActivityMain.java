package com.example.mytermproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;

import java.util.Random;

public class GameActivityMain extends AppCompatActivity {
    private Handler handler;
    private boolean shown_dialog = false;
    boolean isTimeOut = false;
    public static int tap = 0;
    public static int dt= 10000;
    public static int LEVEL = 1;
    public static int MAX_TAP = 100;
    int n =100;
    private ProgressBar progressBar;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private Dialog countdownDialog;
    private int countdownValue = 3; // Set your initial countdown value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        handler = new Handler(Looper.getMainLooper());

        // Start the automatic countdown dialog
        startAutoCountdownDialog();
    }

    private void start() {
        tap = 0;
        LEVEL = 1;
        MAX_TAP = 100;
        timerTextView = findViewById(R.id.timerTextView);
        TextView t1 = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.simpleProgressBar);
        // Set the time duration in milliseconds (e.g., 10 seconds)
        long durationMillis = dt;

        countDownTimer = new CountDownTimer(durationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the TextView with the remaining time
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText("Time remaining: " + secondsRemaining + " seconds");
            }

            @Override
            public void onFinish() {
                // Action to be performed when the countdown is finished
                timerTextView.setText("Time Out!!!!");
                showDialog();
            }
        };

        // Start the countdown timer
        countDownTimer.start();
        LinearLayout gamelayout = findViewById(R.id.activity_game_main);
        gamelayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TouchEvent", "Touch is detected");
                tap = tap + 1;
                progressBar.setProgress(tap);

                // Adjust thresholds based on constants
                if (tap == MAX_TAP) {
                    tap = 0;
                    LEVEL += 1;
                    MAX_TAP = MAX_TAP * LEVEL;
                    t1.setText("level" + LEVEL);
                    progressBar.setMax(MAX_TAP);
                }
                return true;
            }
        });



    }

    private void startAutoCountdownDialog() {
        // Create a custom dialog
        countdownDialog = new Dialog(this);
        countdownDialog.setCancelable(false);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.countdown, null);
        countdownDialog.setContentView(dialogView);

        // Get the TextView from the dialog layout
        TextView countdownTextView = dialogView.findViewById(R.id.countdownText);

        // Start the automatic countdown
        startAutoCountdown(countdownTextView);

        // Show the dialog
        countdownDialog.show();
    }

    private void startAutoCountdown(final TextView countdownTextView) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Decrease the countdown value
                countdownValue--;

                // Update the TextView with the countdown value
                countdownTextView.setText("Countdown: " + countdownValue);

                // Check if the countdown is complete
                if (countdownValue >= 0) {
                    // Schedule the next update after 1 second
                    handler.postDelayed(this, 1000);
                } else {
                    // Update the TextView with "start"
                    countdownTextView.setText("start");
                    // Dismiss the dialog when the countdown is finished after a brief delay
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            countdownDialog.dismiss();
                            start(); // Call the start method after the countdown is complete
                        }
                    }, 1000);
                }
            }
        }, 1000); // Start the countdown immediately and update every 1 second
    }

    private void showDialog(){

        Resources res = getResources();
            if (shown_dialog!=true) { shown_dialog = true ;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("GameOver");
                builder.setMessage("TimeOut");
                builder.setPositiveButton("backToMenu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle OK button click if needed
                        Intent intent = new Intent(GameActivityMain.this,MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        shown_dialog = false;
                    }
                });
                builder.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GameActivityMain.this,GameActivityMain.class);
                        startActivity(intent);
                        startAutoCountdownDialog();
                        dialog.dismiss();
                        shown_dialog = false;
                    }
                });
                builder.setCancelable(false); // Prevent dialog from being dismissed by tapping outside

                AlertDialog dialog = builder.create();
                dialog.show();
            }






    }

}

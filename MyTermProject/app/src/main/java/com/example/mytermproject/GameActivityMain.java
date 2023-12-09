package com.example.mytermproject;
import static com.example.mytermproject.Constants.DATE;
import static com.example.mytermproject.Constants.LEVEL;
import static com.example.mytermproject.Constants.TABLE_NAME;
import static com.example.mytermproject.Constants.scoreTap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class GameActivityMain extends AppCompatActivity {
    private Handler handler;
    public static  ImageView gg;
    private Button itemButon ;
    private boolean isX2 = false;
    private boolean shown_dialog = false;
    boolean isTimeOut = false;
    public static int ShowPicin = 0;
    public static int tap = 0;
    public static int dt= 10000;
    public static int level = 1;
    public static int MAX_TAP = 100;
    int n =100;
    private ProgressBar progressBar;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private Dialog countdownDialog;
    ImageView img;
    EventData events;    int bool ;
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
        Random ran = new Random();
        itemButon = findViewById(R.id.imageButton);
gg = findViewById(R.id.imageView3);
        tap = 0;
        ShowPicin = 0;
        level = 1;
        MAX_TAP = 100;
        timerTextView = findViewById(R.id.timerTextView);
        TextView t1 = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.simpleProgressBar);
        // Set the time duration in milliseconds (e.g., 10 seconds)
        long durationMillis = dt;
        countDownTimer = new CountDownTimer(durationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bool = ran.nextInt(4);
                if(bool == 1){
                    int delayTime = 3000;
                    int a = ran.nextInt(6);
                    // Use a Handler to delay the appearance of the button
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            switch (a){
                                case 0:itemButon.setTranslationX(50);break;
                                case 1:itemButon.setTranslationX(150);break;
                                case 2:itemButon.setTranslationX(175);break;
                                default:itemButon.setTranslationX(200);break;
                            }
                            // Show the button
                            itemButon.setVisibility(View.VISIBLE);
                            int disappearanceDelay = 3000;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Hide the button after the specified disappearance delay
                                    itemButon.setVisibility(View.INVISIBLE);
                                }
                            }, disappearanceDelay);
                        }
                    }, delayTime);
                    itemButon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isX2 = true;
                            int disappearanceDelay = 3000;


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isX2 = false;
                                }
                            }, disappearanceDelay);
                        }
                    });
                }else if(bool == 2){
                    int delayTime = 3000;
                   int a = ran.nextInt(4);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            switch (a){
                                case 0:gg.setTranslationX(50);break;
                                case 1:gg.setTranslationX(150);break;
                                case 2:gg.setTranslationX(175);break;
                                default:gg.setTranslationX(200);break;
                            }
                            // Show the button
                            gg.setVisibility(View.VISIBLE);
                            int disappearanceDelay = 3000;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Hide the button after the specified disappearance delay
                                    itemButon.setVisibility(View.INVISIBLE);
                                }
                            }, disappearanceDelay);
                        }
                    }, delayTime);
                }
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText("Time remaining: " + secondsRemaining + " seconds");;
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
                if(isX2 == true){
                    tap = (tap+20) * level;
                    ShowPicin = (ShowPicin+20) * level;
                }else{
                tap = tap + 1;
                ShowPicin = ShowPicin + 1;
                }
                progressBar.setProgress(tap);
                // Adjust thresholds based on constants
                Scase(level,MAX_TAP,ShowPicin);
                if (tap >= MAX_TAP) {
                    ShowPicin = 0;
                    tap = 0;
                    level += 1;
                    MAX_TAP = MAX_TAP * level;
                    t1.setText("level" + level);
                    progressBar.setMax(MAX_TAP);
                }
                return true;
            }
        });
    }
    public void Scase(int level, int max_tap, int showpic) {
        img = findViewById(R.id.imageView);
        int baseResourceId = R.drawable.test;

        // Construct the resource name based on the level
        String resourceName;

        if (showpic >= 0 && showpic < (max_tap * 25) / 100) {
            resourceName = "test" + level + '1';
            img.setImageResource(getResources().getIdentifier(resourceName, "drawable", getPackageName()));
        } else if (showpic >= (max_tap * 25) / 100 && showpic < (max_tap * 50) / 100) {
            resourceName = "test" + level + '2';
            img.setImageResource(getResources().getIdentifier(resourceName, "drawable", getPackageName()));
        } else if (showpic >= (max_tap * 50) / 100 && showpic < (max_tap * 75) / 100) {
            resourceName = "test" + level + '3';
            img.setImageResource(getResources().getIdentifier(resourceName, "drawable", getPackageName()));
        } else if (showpic >= (max_tap * 75) / 100) {
            resourceName = "test" + level + '4';
            img.setImageResource(getResources().getIdentifier(resourceName, "drawable", getPackageName()));
        }
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
                     events = new EventData(GameActivityMain.this);
                     addEvent();
                     events.close();
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
    private void addEvent(){
       // Change this line to use 'tap' directly
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateAndTime = sdf.format(new Date());
        values.put(DATE, currentDateAndTime);
        values.put(scoreTap, tap);
        values.put(LEVEL, level);
        db.insert(TABLE_NAME, null, values);
    }
}
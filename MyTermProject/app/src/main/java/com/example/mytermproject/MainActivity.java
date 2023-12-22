package com.example.mytermproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    private boolean shown_dialog = false;
    private boolean isPlaying;

    @Override
    protected void onStop() {
        super.onStop();
        if (isPlaying) {
            mp.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isPlaying) {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            mp.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton bt1 = findViewById(R.id.button);

        if (!isPlaying) {
            mp = MediaPlayer.create(MainActivity.this, R.raw.i);
            mp.setLooping(true);
            mp.start();
            isPlaying = true;
        }

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivityMain.class);
                startActivity(intent);
                if (isPlaying) {
                    mp.pause();
                    isPlaying = false;
                }
            }
        });

        ImageButton bt2 = findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent2);
                if (isPlaying) {
                    mp.pause();
                    isPlaying = false;
                }
            }
        });

        ImageButton bt3 = findViewById(R.id.ScoreBoard);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, ScoreBoardActivity.class);
                startActivity(intent3);
                if (isPlaying) {
                    mp.pause();
                    isPlaying = false;
                }
            }
        });
        ImageButton bt4 = findViewById(R.id.imageButton3);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mp.pause();
                    isPlaying = false;
                }showDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }


    private void showDialog() {
        if(!shown_dialog) {
            shown_dialog = true;
            final AlertDialog.Builder viewDialog = new AlertDialog.Builder(this);
            viewDialog.setIcon(android.R.drawable.btn_star_big_on);
            viewDialog.setTitle("วิธีการเล่นเกมนี้"); viewDialog.setMessage("คุณสามารถปรับความยากง่ายได้เพียงเเค่กดปุ่มsettingเเละเริ่มเกมกดที่ปุ่มStart เมื่อคุณเข้าเกมไปคุณจะต้องใช้นิ้วของคุณกดที่หน้าจอให้ไวที่สุด เเละจะมีitem x 2 ไว้ช่วยในการจบเกมไว้ขึ้น");
            viewDialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); shown_dialog = false;
                        }
                    });
            viewDialog.show();
        }//end if
    }
}

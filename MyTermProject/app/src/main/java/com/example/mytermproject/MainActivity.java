package com.example.mytermproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
MediaPlayer mp;
private  boolean isplay ;
    @Override
    protected void onStop() {
        super.onStop();isplay =true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();isplay =true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton bt1 = findViewById(R.id.button);
        if(isplay==false){
            mp = MediaPlayer.create(MainActivity.this,R.raw.i);
            mp.setLooping(true);
            mp.start();
        }

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GameActivityMain.class);
                startActivity(intent);
            }
        });
        ImageButton bt2 = findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent2);
            }
        });
        ImageButton bt3 = findViewById(R.id.ScoreBoard);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                Intent intent3 = new Intent(MainActivity.this,ScoreBoardActivity.class);
                startActivity(intent3);
            }
        });



    }

}
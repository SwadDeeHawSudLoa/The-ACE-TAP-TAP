package com.example.mytermproject;

import static com.example.mytermproject.Constants.DATE;
import static com.example.mytermproject.Constants.LEVEL;
import static com.example.mytermproject.Constants.TABLE_NAME;
import static com.example.mytermproject.Constants.scoreTap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ScoreBoardActivity extends AppCompatActivity {
    private EventData events;
    private Cursor cursor;
    private ImageButton btt;

    private ImageButton btt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        btt = findViewById(R.id.button5);


        events = new EventData(ScoreBoardActivity.this);

        try {
            cursor = getEvents();
            showEvents(cursor);
        } finally {
            events.close();
        }

        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                events = new EventData(ScoreBoardActivity.this);
                try {
                    resetAutoInc();
                    cursor = getEvents();
                    showEvents(cursor);
                } finally {
                    events.close();
                }
            }
        });
        btt2= findViewById(R.id.imageButton2);
        btt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ScoreBoardActivity.this,MainActivity.class);
                startActivity(intent2);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Cursor getEvents() {
        String[] FROM = {DATE, scoreTap, LEVEL};
        String ORDER_BY = DATE + " DESC";
        SQLiteDatabase db = events.getReadableDatabase();
        return db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
    }

    private void showEvents(Cursor cursor) {
        final ListView listView = findViewById(R.id.list);
        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<>();
        HashMap<String, String> map;

        while (cursor.moveToNext()) {
            map = new HashMap<>();
            map.put("date", cursor.getString(0));
            map.put("TAP", String.valueOf(cursor.getLong(1)));
            map.put("level", String.valueOf(cursor.getLong(2)));
            MyArrList.add(map);
        }

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(ScoreBoardActivity.this, MyArrList, R.layout.column,
                new String[]{"date", "TAP", "level"},
                new int[]{R.id.col_trans_id, R.id.col_name, R.id.col_msg});
        listView.setAdapter(sAdap);
    }

    private void resetAutoInc() {
        SQLiteDatabase db = events.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                TABLE_NAME + "'");
    }
}

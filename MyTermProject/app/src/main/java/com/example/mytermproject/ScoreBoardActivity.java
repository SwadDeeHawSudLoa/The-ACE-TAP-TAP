package com.example.mytermproject;

import static com.example.mytermproject.Constants.DATE;
import static com.example.mytermproject.Constants.LEVEL;
import static com.example.mytermproject.Constants.TABLE_NAME;
import static com.example.mytermproject.Constants.scoreTap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ScoreBoardActivity extends AppCompatActivity {
    private EventData events;
    AbstractCursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        events = new EventData(ScoreBoardActivity.this);
        try {
            Cursor cursor = getEvents();
            showEvents(cursor);
        }finally {
            events.close();
        }
    }

    private Cursor getEvents() {
        String[] FROM = { DATE, scoreTap, LEVEL};
        String ORDER_BY = DATE + " DESC";
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
        return cursor;
    }

    private void showEvents(Cursor cursor) {
        final ListView listView = (ListView)findViewById(R.id.list);
        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        while(cursor.moveToNext()) {
            map = new HashMap<String, String>();
            map.put("date", cursor.getString(0));
            map.put("TAP", String.valueOf(cursor.getLong(1)));
            map.put("level", String.valueOf(cursor.getLong(2)));
            MyArrList.add(map);
        }
        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter( ScoreBoardActivity.this, MyArrList, R.layout.column,
                new String[] {"date", "TAP", "level"},
                new int[] {R.id.col_trans_id, R.id.col_name, R.id.col_msg} );
        listView.setAdapter(sAdap);
    }

}
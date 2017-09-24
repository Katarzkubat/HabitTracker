package com.example.katarzkubat.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.katarzkubat.habittracker.data.HabitContract;
import com.example.katarzkubat.habittracker.data.HabitDbHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HabitActivity extends AppCompatActivity {

    private HabitDbHelper hDbHelper;

    @InjectView(R.id.fab)
    public FloatingActionButton fab;

    @InjectView(R.id.text_view_habit)
    public TextView displayView;

    @OnClick(R.id.fab)
    public void fabClick(){
        Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_activity_main);
        ButterKnife.inject(this);

        hDbHelper = new HabitDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private Cursor getCursor() {

        SQLiteDatabase db = hDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_TYPE,
                HabitContract.HabitEntry.COLUMN_LOCATION,
                HabitContract.HabitEntry.COLUMN_TIME,
                HabitContract.HabitEntry.COLUMN_COST,
                HabitContract.HabitEntry.COLUMN_DAY
        };

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    private void displayDatabaseInfo() {

        Cursor cursor = getCursor();

        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_TYPE + " - " +
                    HabitContract.HabitEntry.COLUMN_LOCATION + " - " +
                    HabitContract.HabitEntry.COLUMN_TIME + " - " +
                    HabitContract.HabitEntry.COLUMN_COST + " - " +
                    HabitContract.HabitEntry.COLUMN_DAY + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int typeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_TYPE);
            int locationColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_LOCATION);
            int timeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_TIME);
            int costColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_COST);
            int dayColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DAY);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentType = cursor.getString(typeColumnIndex);
                String currentLocation = cursor.getString(locationColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                int currentCost = cursor.getInt(costColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentType + " - " +
                        currentLocation + " - " +
                        currentTime + " - " +
                        currentCost + " - " +
                        currentDay));
            }
        } finally {

            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_habit_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_habit:
                Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(intent);
                return true;

            case R.id.delete_habits:
                delete();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        hDbHelper.getWritableDatabase().delete(HabitContract.HabitEntry.TABLE_NAME, "1", null);
        displayView.setText("");
        displayDatabaseInfo();
    }
}

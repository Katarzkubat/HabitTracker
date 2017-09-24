package com.example.katarzkubat.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.katarzkubat.habittracker.data.HabitContract;
import com.example.katarzkubat.habittracker.data.HabitDbHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditorActivity extends AppCompatActivity {

    @InjectView(R.id.habit_type)
    public EditText hTypeEditText;

    @InjectView(R.id.habit_location)
    public EditText hLocationEditText;

    @InjectView(R.id.habit_time)
    public EditText hTimeEditText;

    @InjectView(R.id.habit_cost)
    public EditText hCostEditText;

    @InjectView(R.id.day_spinner)
    public Spinner hDaySpinner;

    private int hDay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);
        ButterKnife.inject(this);

        setupSpinner();
    }

    private void setupSpinner() {

        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day_options, android.R.layout.simple_spinner_item);

        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hDaySpinner.setAdapter(daySpinnerAdapter);

        hDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.day_monday))) {
                        hDay = HabitContract.HabitEntry.DAY_MONDAY;
                    } else if (selection.equals(getString(R.string.day_tuesday))) {
                        hDay = HabitContract.HabitEntry.DAY_TUESDAY;
                    } else if (selection.equals(getString(R.string.day_wednesday))) {
                        hDay = HabitContract.HabitEntry.DAY_WEDNESDAY;
                    } else if (selection.equals(getString(R.string.day_thursday))) {
                        hDay = HabitContract.HabitEntry.DAY_THURSDAY;
                    } else if (selection.equals(getString(R.string.day_friday))) {
                        hDay = HabitContract.HabitEntry.DAY_FRIDAY;
                    } else if (selection.equals(getString(R.string.day_saturday))) {
                        hDay = HabitContract.HabitEntry.DAY_SATURDAY;
                    } else if (selection.equals(getString(R.string.day_sunday))) {
                        hDay = HabitContract.HabitEntry.DAY_SUNDAY;
                    } else {
                        hDay = HabitContract.HabitEntry.DAY_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hDay = HabitContract.HabitEntry.DAY_UNKNOWN;
            }
        });
    }

    private void insertHabit() {

        String habit = hTypeEditText.getText().toString().trim();
        String location = hLocationEditText.getText().toString().trim();
        String timeString = hTimeEditText.getText().toString().trim();
        String costString = hCostEditText.getText().toString().trim();

        if (isValid(habit, location, timeString, costString)) {
            double time = Double.parseDouble(timeString);
            int cost = Integer.parseInt(costString);

            HabitDbHelper hDbHelper = new HabitDbHelper(this);

            SQLiteDatabase db = hDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(HabitContract.HabitEntry.COLUMN_HABIT_TYPE, habit);
            values.put(HabitContract.HabitEntry.COLUMN_LOCATION, location);
            values.put(HabitContract.HabitEntry.COLUMN_TIME, time);
            values.put(HabitContract.HabitEntry.COLUMN_COST, cost);
            values.put(HabitContract.HabitEntry.COLUMN_DAY, hDay);

            db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
            finish();
        }
    }

    private boolean isValid(String habit, String location, String timeString, String costString) {

        if(!TextUtils.isEmpty(habit) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(timeString) && !TextUtils.isEmpty(costString)){
            return true;
        }

        Toast.makeText(this, "Complete your data", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                insertHabit();

                return true;
            case R.id.clear:
                clear();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void clear(){

        hTypeEditText.setText("");
        hLocationEditText.setText("");
        hTimeEditText.setText("");
        hCostEditText.setText("");
        hDaySpinner.setSelection(0);
    }

}

package com.example.katarzkubat.habittracker.data;


import android.provider.BaseColumns;

public final class HabitContract {

        private HabitContract() {}

        public static final class HabitEntry implements BaseColumns {

            public static final String _ID = BaseColumns._ID;
            public static final String TABLE_NAME = "Habits";
            public static final String COLUMN_HABIT_TYPE = "Habit";
            public static final String COLUMN_LOCATION = "Location";
            public static final String COLUMN_TIME = "Time";
            public static final String COLUMN_COST = "Cost";
            public static final String COLUMN_DAY = "Day";

            public static final int DAY_MONDAY = 0;
            public static final int DAY_TUESDAY = 1;
            public static final int DAY_WEDNESDAY= 2;
            public static final int DAY_THURSDAY = 3;
            public static final int DAY_FRIDAY = 4;
            public static final int DAY_SATURDAY = 5;
            public static final int DAY_SUNDAY = 6;

            public static final int DAY_UNKNOWN = -1;
        }
    }

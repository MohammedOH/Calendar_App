package com.example.discreteproject.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.discreteproject.listeners.OnSwipeTouchListener;
import com.example.discreteproject.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    // CONSTANTS
    private static final String TABLE = "table";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final int START_DAY_FOR_JAN_1_1800 = 3;
    // UI
    private TextView title;
    private TextView[][] tableUI;
    // Current year and month
    private int year;
    private int month;
    // Current calendar table
    private int[][] currentTable;
    // Months names
    private String[] months;
    // Custom listener
    private OnSwipeTouchListener onSwipeTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        if (currentTable == null) {
            currentTable = new int[6][7];
            // Getting data from intent
            Intent intent = getIntent();
            year = intent.getIntExtra("year", 2019);
            month = intent.getIntExtra("month", 1);
            // Show final result
            getTableOfMonth(year, month);
        }
        // Inflating items
        inflateItems();
        // Setting result
        title.setText(String.format(getResources().getString(R.string.date_format), year, getMonthName(month)));
        setTableToUI(currentTable);
        // Listeners
        findViewById(R.id.calender_table_layout).setOnTouchListener(onSwipeTouchListener);
        findViewById(R.id.activity).setOnTouchListener(onSwipeTouchListener);
        // Null check of ActionBar
        if (getSupportActionBar() != null) {
            //show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void inflateItems() {
        tableUI = new TextView[6][7];
        tableUI[0][0] = findViewById(R.id._1);
        tableUI[0][1] = findViewById(R.id._2);
        tableUI[0][2] = findViewById(R.id._3);
        tableUI[0][3] = findViewById(R.id._4);
        tableUI[0][4] = findViewById(R.id._5);
        tableUI[0][5] = findViewById(R.id._6);
        tableUI[0][6] = findViewById(R.id._7);

        tableUI[1][0] = findViewById(R.id._8);
        tableUI[1][1] = findViewById(R.id._9);
        tableUI[1][2] = findViewById(R.id._10);
        tableUI[1][3] = findViewById(R.id._11);
        tableUI[1][4] = findViewById(R.id._12);
        tableUI[1][5] = findViewById(R.id._13);
        tableUI[1][6] = findViewById(R.id._14);

        tableUI[2][0] = findViewById(R.id._15);
        tableUI[2][1] = findViewById(R.id._16);
        tableUI[2][2] = findViewById(R.id._17);
        tableUI[2][3] = findViewById(R.id._18);
        tableUI[2][4] = findViewById(R.id._19);
        tableUI[2][5] = findViewById(R.id._20);
        tableUI[2][6] = findViewById(R.id._21);

        tableUI[3][0] = findViewById(R.id._22);
        tableUI[3][1] = findViewById(R.id._23);
        tableUI[3][2] = findViewById(R.id._24);
        tableUI[3][3] = findViewById(R.id._25);
        tableUI[3][4] = findViewById(R.id._26);
        tableUI[3][5] = findViewById(R.id._27);
        tableUI[3][6] = findViewById(R.id._28);

        tableUI[4][0] = findViewById(R.id._29);
        tableUI[4][1] = findViewById(R.id._30);
        tableUI[4][2] = findViewById(R.id._31);
        tableUI[4][3] = findViewById(R.id._32);
        tableUI[4][4] = findViewById(R.id._33);
        tableUI[4][5] = findViewById(R.id._34);
        tableUI[4][6] = findViewById(R.id._35);

        tableUI[5][0] = findViewById(R.id._36);
        tableUI[5][1] = findViewById(R.id._37);
        tableUI[5][2] = findViewById(R.id._38);
        tableUI[5][3] = findViewById(R.id._39);
        tableUI[5][4] = findViewById(R.id._40);
        tableUI[5][5] = findViewById(R.id._41);
        tableUI[5][6] = findViewById(R.id._42);

        months = getResources().getStringArray(R.array.months);
        title = findViewById(R.id.title);
        onSwipeTouchListener = new OnSwipeTouchListener(this) {

            @Override
            public void onSwipeRight() {
                boolean isRTL = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR;
                if (isRTL) {
                    setPrevMonth();
                } else {
                    setNextMonth();
                }
            }

            @Override
            public void onSwipeLeft() {
                boolean isRTL = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR;
                if (isRTL) {
                    setNextMonth();
                } else {
                    setPrevMonth();
                }
            }

        };
    }

    /* Clearing table */
    private void emptyTable() {
        for (int[] rows : currentTable) {
            Arrays.fill(rows, 0);
        }
    }

    /* Getting the table calendar of the month */
    private int[][] getTableOfMonth(int year, int month) {
        emptyTable();
        int totalNumberOfDays = 0;
        // Get the total days from 1/ 1/ 1800 to 1 /1 /year
        for (int i = 1800; i < year; i++) {
            if (isLeapYear(i))
                totalNumberOfDays += 366;
            else
                totalNumberOfDays += 365;
        }
        // Add days from Jan to the month prior to the calendar month
        for (int i = 1; i < month; i++) {
            totalNumberOfDays += getNumberOfDaysInMonth(year, i);
        }
        // Finding the first day of the week in the month
        int startDay = (totalNumberOfDays + START_DAY_FOR_JAN_1_1800) % 7;
        // Total number of days in the month entered
        int numberOfDaysInMonth = getNumberOfDaysInMonth(year, month);
        // Storing table into two dimensional array
        for (int i = 1, j = 0, k = startDay; i <= numberOfDaysInMonth; i++) {
            currentTable[j % 6][k % 7] = i;
            k++;
            if ((i + startDay) % 7 == 0) {
                k = 0;
                j++;
            }
        }
        return currentTable;
    }

    /* Determine if it is a leap year */
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /* Get the number of days in a month */
    private int getNumberOfDaysInMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12)
            return 31;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        if (month == 2) return isLeapYear(year) ? 29 : 28;
        // If month is incorrect
        return 0;
    }

    /* Setting result calendar table to the user interface table */
    private void setTableToUI(int[][] calenderTable) {
        // Getting current date
        int longDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        int currentDay = longDate % 100;
        longDate /= 100;
        int currentMonth = longDate % 100;
        longDate /= 100;
        int currentYear = longDate;
        // Iterating over the UI table to set its result
        for (int i = 0; i < tableUI.length; i++) {
            for (int j = 0; j < tableUI[0].length; j++) {
                // Ignore empty items
                if (calenderTable[i][j] != 0) {
                    if (currentYear == year && currentMonth == month && currentDay == calenderTable[i][j]) {
                        tableUI[i][j].setBackgroundColor(Color.parseColor("#A6ECF5"));
                    } else {
                        tableUI[i][j].setBackground(null);
                    }
                    tableUI[i][j].setText(String.valueOf(calenderTable[i][j]));
                } else {
                    tableUI[i][j].setText("");
                }
            }
        }
    }

    /* Setting next month of the current month */
    private void setNextMonth() {
        if (month == 12) {
            year++;
            month = 1;
        } else {
            month++;
        }
        // Show final result
        setResult();
    }

    /* Setting previous month of the current month */
    private void setPrevMonth() {
        if (month == 1) {
            year--;
            month = 12;
        } else {
            month--;
        }
        // Show final result
        setResult();
    }

    /* Get the English name for the month */
    public String getMonthName(int month) {
        return months[month - 1];
    }

    /* Setting both table and month title */
    public void setResult() {
        setTableToUI(getTableOfMonth(year, month));
        title.setText(String.format(getResources().getString(R.string.date_format), year, getMonthName(month)));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TABLE, currentTable);
        outState.putInt(YEAR, year);
        outState.putInt(MONTH, month);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        year = (int) savedInstanceState.get(YEAR);
        month = (int) savedInstanceState.get(MONTH);
        currentTable = (int[][]) savedInstanceState.getSerializable(TABLE);
        setTableToUI(currentTable);
        title.setText(String.format(getResources().getString(R.string.date_format), year, getMonthName(month)));
    }

}
package com.example.discreteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CalenderActivity extends AppCompatActivity {

    private TextView[][] tableUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        // Getting data from intent
        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 2019);
        int month = intent.getIntExtra("month", 1);
        // Inflating items
        inflateItems();
        // Getting results table
        int[][] calenderTable = getTableOfMonth(year, month);

        setResultToUI(calenderTable);

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
    }

    private int[][] getTableOfMonth(int year, int month) {
        int[][] monthBody = new int[6][7];
        final int START_DAY_FOR_JAN_1_1800 = 3;


        int totalNumberOfDays = 0;

        // Get the total days from 1800 to 1/1/year
        for (int i = 1800; i < year; i++)
            if (isLeapYear(i))
                totalNumberOfDays += 366;
            else
                totalNumberOfDays += 365;

        // Add days from Jan to the month prior to the calendar month
        for (int i = 1; i < month; i++)
            totalNumberOfDays += getNumberOfDaysInMonth(year, i);

        int startDay = (totalNumberOfDays + START_DAY_FOR_JAN_1_1800) % 7;

        int numberOfDaysInMonth = getNumberOfDaysInMonth(year, month);
        int j = 0;
        int k = startDay;
        for (int i = 1; i <= numberOfDaysInMonth; i++) {
            monthBody[j % 6][k % 7] = i;
            k++;
            if ((i + startDay) % 7 == 0) {
                k = 0;
                j++;
            }
        }
        return monthBody;
    }

    /* Determine if it is a leap year */
    public boolean isLeapYear(int year) {
        /* Determine if it is a leap year */
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /* Get the number of days in a month */
    public int getNumberOfDaysInMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12)
            return 31;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        if (month == 2) return isLeapYear(year) ? 29 : 28;
        return 0; // If month is incorrect
    }

    private void setResultToUI(int[][] calenderTable) {
        for (int i = 0; i < tableUI.length; i++) {
            for (int j = 0; j < tableUI[0].length; j++) {
                if (calenderTable[i][j] != 0)
                    tableUI[i][j].setText(String.valueOf(calenderTable[i][j]));
            }
        }
    }

}
package com.example.discreteproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discreteproject.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.discreteproject.helpers.Helper.hideKeyboard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout yearTextInputLayout, monthTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inflating items
        inflateItems();
        // Click Listener for the activity
        findViewById(R.id.activity).setOnClickListener(this);
    }

    private void inflateItems() {
        yearTextInputLayout = findViewById(R.id.text_input_year);
        monthTextInputLayout = findViewById(R.id.text_input_month);
    }

    /* Button clicked */
    public void showCalender(View view) {
        // Reading input as String
        String year = yearTextInputLayout.getEditText().getText().toString().trim();
        String month = monthTextInputLayout.getEditText().getText().toString().trim();
        // Edge case: Empty fields
        if (year.isEmpty() || month.isEmpty()) {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_LONG).show();
            return;
        }
        // Parsing after Checking
        int yearValue = Integer.parseInt(year);
        int monthValue = Integer.parseInt(month);
        // Checks values
        boolean validYear = validYear(yearValue), validMonth = validMonth(monthValue);
        if (!validYear || !validMonth)
            return;
        // Start new Activity with the final data
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("year", yearValue);
        intent.putExtra("month", monthValue);
        startActivity(intent);
        // Clearing data
        yearTextInputLayout.getEditText().setText(null);
        monthTextInputLayout.getEditText().setText(null);
    }

    /* Validating year */
    private boolean validYear(int year) {
        // Checking years value
        if (year < 1800) {
            yearTextInputLayout.setError(getResources().getText(R.string.invalid_year));
            return false;
        }
        yearTextInputLayout.setError(null);
        return true;
    }

    /* Validating month */
    private boolean validMonth(int month) {
        // Checking months value
        if (month < 1 || month > 12) {
            monthTextInputLayout.setError(getResources().getText(R.string.not_month));
            return false;
        }
        monthTextInputLayout.setError(null);
        return true;
    }

    // Hides keyboard when touch the screen
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_input_year:
            case R.id.text_input_month:
            default:
                hideKeyboard(this);
        }
    }

}
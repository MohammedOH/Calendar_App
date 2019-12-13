package com.example.discreteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static com.example.discreteproject.Helper.hideKeyboard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout yearTextInputLayout, monthTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflateItems();

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
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_LONG).show();
            return;
        }
        // Parsing after Checking
        int yearValue = Integer.parseInt(year);
        int monthValue = Integer.parseInt(month);
        // Checks values
        if (!validYear(yearValue) || !validMonth(monthValue))
            return;
        // Start new Activity with the final data
        Intent intent = new Intent(this, CalenderActivity.class);
        intent.putExtra("year", yearValue);
        intent.putExtra("month", monthValue);
        startActivity(intent);
        // Clearing data
        yearTextInputLayout.getEditText().setText(null);
        monthTextInputLayout.getEditText().setText(null);
    }

    private boolean validYear(int year) {
        // Checking years value
        if (year < 1800) {
            yearTextInputLayout.setError("Please Enter a year > 1800!");
            return false;
        }
        yearTextInputLayout.setError(null);
        return true;
    }

    private boolean validMonth(int month) {
        // Checking months value
        if (month < 1 || month > 12) {
            monthTextInputLayout.setError("Not a month!");
            return false;
        }
        monthTextInputLayout.setError(null);
        return true;
    }

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

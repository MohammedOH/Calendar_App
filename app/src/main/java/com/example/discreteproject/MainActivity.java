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
        // Checks values
        if (!validYear(year) || !validMonth(month))
            return;
        // Parsing after Checking
        int yearValue = Integer.parseInt(year);
        int monthValue = Integer.parseInt(month);
        // Start new Activity with the final data
        Intent intent = new Intent(this, CalenderActivity.class);
        intent.putExtra("year", yearValue);
        intent.putExtra("month", monthValue);
        startActivity(intent);
        // Clearing data
        yearTextInputLayout.getEditText().setText(null);
        monthTextInputLayout.getEditText().setText(null);
    }

    private boolean validYear(String year) {
        boolean isValid = true;
        // Checking containing characters
        for (int i = 0; i < year.length(); i++) {
            if (!Character.isDigit(year.charAt(i))) {
                isValid = false;
                yearTextInputLayout.setError("Only digits allowed!");
            }
        }
        // Checking years value
        if (isValid) {
            if (Integer.parseInt(year) < 1800) {
                yearTextInputLayout.setError("Please Enter a year > 1800!");
                return false;
            }
            yearTextInputLayout.setError(null);
        }
        return isValid;
    }

    private boolean validMonth(String month) {
        boolean isValid = true;
        // Checking containing characters
        for (int i = 0; i < month.length(); i++) {
            if (!Character.isDigit(month.charAt(i))) {
                isValid = false;
                monthTextInputLayout.setError("Only digits allowed!");
            }
        }
        // Checking months value
        if (isValid) {
            int monthValue = Integer.parseInt(month);
            if (monthValue < 1 || monthValue > 12) {
                yearTextInputLayout.setError("Not a month!");
                return false;
            }
            yearTextInputLayout.setError(null);
        }
        return isValid;
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

package com.jovanovic.stefan.sqlitetutorial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class Update_Trip_Activity extends AppCompatActivity {

    EditText input_trip_name, date_start_input, date_end_input, place_from, place_to;
    Button update_button, delete_button;
    Trip selectedTrip;
    String risk ="Yes";
    Expense selectedExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);

        input_trip_name = findViewById(R.id.input_trip_name2);
        date_start_input = findViewById(R.id.date_start_input2);
        date_end_input = findViewById(R.id.date_end_input2);
        place_from = findViewById(R.id.place_from2);
        place_to = findViewById(R.id.place_to2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndDisplayInfo_Trip();

        //Set actionbar title after getAndDisplayInfo_Trip method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(selectedTrip.getTrip_name());
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName() && validateDateStart() && validateDateEnd()&&validateFrom()&&validateTo()) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Update_Trip_Activity.this);
                selectedTrip.setTrip_name(input_trip_name.getText().toString().trim());
                selectedTrip.setDate_start(date_start_input.getText().toString().trim());
                selectedTrip.setDate_end(date_end_input.getText().toString().trim());
                selectedTrip.setPlace_from(place_from.getText().toString().trim());
                selectedTrip.setPlace_to(place_to.getText().toString().trim());
                long result = myDB.updateTrip(selectedTrip,risk);
                    Toast.makeText(getBaseContext(), "Update Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean validateName() {
                String val = input_trip_name.getText().toString();


                if (val.trim().isEmpty()) {
                    input_trip_name.setError("Field cannot be empty");
                    return false;
                }
                else {
                    input_trip_name.setError(null);
                    return true;
                }
            }

            private boolean validateDateStart() {
                String val1 = date_start_input.getText().toString();
                if (val1.trim().isEmpty()) {
                    date_start_input.setError("Field cannot be empty");
                    return false;
                }
                else {
                    date_start_input.setError(null);
                    return true;
                }

            }

            private boolean validateDateEnd() {
                String val2 = date_end_input.getText().toString();
                if (val2.trim().isEmpty()) {
                    date_end_input.setError("Field cannot be empty");
                    return false;
                }
                else {
                    date_end_input.setError(null);
                    return true;
                }
            }

            private boolean validateFrom() {
                String val3 = place_from.getText().toString();
                if (val3.trim().isEmpty()) {
                    place_from.setError("Field cannot be empty");
                    return false;
                }
                else {
                    place_from.setError(null);
                    return true;
                }
            }

            private boolean validateTo() {
                String val4 = place_to.getText().toString();
                if (val4.trim().isEmpty()) {
                    place_to.setError("Field cannot be empty");
                    return false;
                }
                else {
                    place_to.setError(null);
                    return true;
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAction_Trip();
            }
        });

        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        date_start_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Update_Trip_Activity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day + "/" + month + "/" + year;
                        date_start_input.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        date_end_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Update_Trip_Activity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day + "/" + month + "/" + year;
                        date_end_input.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

     void getAndDisplayInfo_Trip() {
        Intent intent = getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("selectedTrip");

        //display in textview
        input_trip_name.setText(selectedTrip.getTrip_name());
        date_start_input.setText(selectedTrip.getDate_start());
        date_end_input.setText(selectedTrip.getDate_end());
        place_from.setText(selectedTrip.getPlace_from());
        place_to.setText(selectedTrip.getPlace_to());

        //display in action bar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(selectedTrip.getTrip_name());
        }
    }

    void deleteAction_Trip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + selectedTrip.getTrip_name() + " ?");
        builder.setMessage("Are you sure you want to delete " + selectedTrip.getTrip_name() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Update_Trip_Activity.this);
                long result = myDB.deleteTrip(Long.parseLong(String.valueOf(selectedTrip.getId())));
                if (result == -1) {
                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Delete Successfully!", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rY:
                if (checked)
                    risk="Yes";
                break;
            case R.id.rN:
                if (checked)
                    risk="No";
                break;
        }
    }
}

package com.jovanovic.stefan.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class Add_Trip_Activity extends AppCompatActivity {

    EditText input_trip_name, date_start_input, date_end_input, place_from, place_to;
    Button add_button, cancel;
    String risk = "Yes" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        input_trip_name = findViewById(R.id.input_trip_name);
        date_start_input = findViewById(R.id.date_start_input);
        date_end_input = findViewById(R.id.date_end_input);
        place_from = findViewById(R.id.place_from);
        place_to = findViewById(R.id.place_to);
        add_button = findViewById(R.id.add_button);
        cancel = findViewById(R.id.btnCancel);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date_start_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Add_Trip_Activity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
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
                        Add_Trip_Activity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        date_end_input.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Add_Trip_Activity.this, MainActivity.class);
                startActivity(intent);
                setResult(RESULT_CANCELED);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName() && validateDateStart() && validateDateEnd()&&validateFrom()&&validateTo()) {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(Add_Trip_Activity.this);
                    Trip trip = new Trip();
                    trip.setTrip_name(input_trip_name.getText().toString().trim());
                    trip.setDate_start(date_start_input.getText().toString().trim());
                    trip.setDate_end(date_end_input.getText().toString().trim());
                    trip.setPlace_from(place_from.getText().toString().trim());
                    trip.setPlace_to(place_to.getText().toString().trim());
                    myDB.addTrip(trip ,risk);
                    Toast.makeText(getBaseContext(), "Added Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Add_Trip_Activity.this, MainActivity.class);
                    startActivity(intent);
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

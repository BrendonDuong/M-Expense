package com.jovanovic.stefan.sqlitetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Detail_Trip_Activity extends AppCompatActivity {

    TextView tvTripDt, input_trip_name, date_start_input, date_end_input, place_from, place_to;
    TextView expense_input, no_data;
    ImageView empty_imageview;
    Button add_button, button_reload;

    Trip selectedTrip;
    List<Expense> expenses;
    RecyclerView rcvExpense;
    MyDatabaseHelper myDB;
    Expense_Adapter expense_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_trip);

        tvTripDt = findViewById(R.id.tvTripDt);
        input_trip_name = findViewById(R.id.tvTripNameDt);
        date_start_input = findViewById(R.id.dateStartDt);
        date_end_input = findViewById(R.id.dateEndDt);
        place_from = findViewById(R.id.tvPlaceFromDt);
        place_to = findViewById(R.id.tvPlaceToDt);
        expense_input = findViewById(R.id.tvTotalEx);
        rcvExpense = findViewById(R.id.rcvExpense);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button = findViewById(R.id.btnAddExpenseDt);
        button_reload = findViewById(R.id.btnLoad);


        button_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_Trip_Activity.this, Add_Expense_Activity.class);
                intent.putExtra("id",selectedTrip.getId());
                intent.putExtra("name",selectedTrip.getTrip_name());
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(Detail_Trip_Activity.this);
        Intent intent=getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("trip");


        expenses = new ArrayList<>();

        displayOrNot();
        //First we call this
        getAndDisplayInfo_Trip();



        expense_adapter = new Expense_Adapter(Detail_Trip_Activity.this,this, expenses);
        rcvExpense.setAdapter(expense_adapter);
        rcvExpense.setLayoutManager(new LinearLayoutManager(Detail_Trip_Activity.this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    private void displayOrNot() {
        expenses = myDB.getListExpenseByTripID(selectedTrip.getId());
        if(expenses.size() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    public void getAndDisplayInfo_Trip() {
        Intent intent = getIntent();
        selectedTrip = (Trip) intent.getSerializableExtra("trip");

        //display in textview
        tvTripDt.setText(String.valueOf(selectedTrip.getId()));
        input_trip_name.setText(selectedTrip.getTrip_name());
        date_start_input.setText(selectedTrip.getDate_start());
        date_end_input.setText(selectedTrip.getDate_end());
        place_from.setText(selectedTrip.getPlace_from());
        place_to.setText(selectedTrip.getPlace_to());
        expense_input.setText(String.valueOf(myDB.getTotalExpense(selectedTrip.getId())));

        //display in action bar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(selectedTrip.getTrip_name());
        }
    }
}

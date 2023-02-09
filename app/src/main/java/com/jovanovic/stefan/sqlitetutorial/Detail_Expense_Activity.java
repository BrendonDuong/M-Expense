package com.jovanovic.stefan.sqlitetutorial;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Detail_Expense_Activity extends AppCompatActivity {

    TextView category, total_expense, date_time, notes;
    Button delete_button, btnCancel, btnExport_data;
    Expense selectedExpense;

    private int index = -1;
    ArrayList<String> listFile = new ArrayList<>();
    private static final String FILE_NAME = "jsonfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_expense);

        category = findViewById(R.id.category_2);
        total_expense = findViewById(R.id.total_expense_2);
        date_time = findViewById(R.id.date_time);
        notes = findViewById(R.id.notes_2);
        delete_button = findViewById(R.id.delete_button_2);
        btnCancel = findViewById(R.id.btnCancel);
        btnExport_data = findViewById(R.id.btnExport_data);
        getAndDisplayInfo_Expense();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        //Set actionbar title after getAndDisplayInfo_Trip method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(selectedExpense.getCategory());
        }

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAction_Expense();
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                Detail_Expense_Activity.this.finish();
            }
        });

        btnExport_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFile.clear();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(selectedExpense);
                listFile.add(json);
                saveTextFile(String.valueOf(listFile));
                try {
                    ReadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setResult(RESULT_CANCELED);
                Detail_Expense_Activity.this.finish();
            }
        });
    }

    void getAndDisplayInfo_Expense() {
        Intent intent = getIntent();
        selectedExpense = (Expense) intent.getSerializableExtra("selectedExpense");

        //display in textview
        category.setText(selectedExpense.getCategory());
        total_expense.setText(String.valueOf(selectedExpense.getTotal_expense()));
        date_time.setText(selectedExpense.getUsed_Time());
        notes.setText(selectedExpense.getNotes());

        //display in action bar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(selectedExpense.getCategory());
        }
    }

    private void ReadFile() throws IOException {
        FileInputStream fileInputStream = getApplicationContext().openFileInput(FILE_NAME);
        if (fileInputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineData = bufferedReader.readLine();
            while (lineData != null) {
                listFile.add(lineData);
                lineData = bufferedReader.readLine();
            }
        }
    }

    private void saveTextFile(String filename) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        try {
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path + "/Download/");
            dir.mkdirs();
            String fileName = "MyFile_" + timeStamp + ".txt";
            File file = new File(dir, fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(filename);
            bw.close();
            Toast.makeText(Detail_Expense_Activity.this, "Export Successfully!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(Detail_Expense_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Detail_Expense_Activity.this, "PERMISSION GRANTED!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Detail_Expense_Activity.this, "PERMISSION NOT GRANTED!", Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }

    void deleteAction_Expense() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + selectedExpense.getCategory() + " ?");
        builder.setMessage("Are you sure you want to delete " + selectedExpense.getCategory() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Detail_Expense_Activity.this);
                long result = myDB.deleteExpense(String.valueOf(selectedExpense.getId()));
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
}

package com.jovanovic.stefan.sqlitetutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Login_Activity extends AppCompatActivity {

        EditText edEmail,edPassword;
        Button btnLogin,btnCancel;
        CheckBox chkRememberPass;
        String strUser,strPass;
        MyDatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");
        edEmail=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnCancel=findViewById(R.id.btnCancel);
        chkRememberPass=findViewById(R.id.chkRememberPass);
        myDb= new MyDatabaseHelper(this);

        SharedPreferences pref =getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user =pref.getString("EMAIL","");
        String pass =pref.getString("PASSWORD","");
        Boolean rem =pref.getBoolean("REMEMBER",false);

        edEmail.setText(user);
        edPassword.setText(pass);
        chkRememberPass.setChecked(rem);

    btnCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edEmail.setText("");
            edPassword.setText("");
        }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
    checkLogin();
        }
    });
}

public void rememberUser(String u,String p,boolean status){
    SharedPreferences pref =getSharedPreferences("USER_FILE",MODE_PRIVATE);
    SharedPreferences.Editor edit =pref.edit();
    if (!status){
        edit.clear();
    }else {
        edit.putString("EMAIL",u);
        edit.putString("PASSWORD",p);
        edit.putBoolean("REMEMBER",status);
    }
    edit.commit();
}

public void checkLogin(){
        strUser=edEmail.getText().toString();
        strPass=edPassword.getText().toString();
        if (strUser.isEmpty()||strPass.isEmpty()){
        Toast.makeText(getApplicationContext(),"Can't be left blank!",Toast.LENGTH_SHORT).show();
        }else {
        if (myDb.checkLogin(strUser,strPass)>0){
            Toast.makeText(getApplicationContext(),"Login Successfully!",Toast.LENGTH_SHORT).show();
             rememberUser(strUser,strPass,chkRememberPass.isChecked());
             Intent i=new Intent(getApplicationContext(),MainActivity.class);
             i.putExtra("user",strUser);
             startActivity(i);
             finish();
        }else {
            Toast.makeText(getApplicationContext(),"Enter Wrong",Toast.LENGTH_SHORT).show();
        }
        }
   }
}
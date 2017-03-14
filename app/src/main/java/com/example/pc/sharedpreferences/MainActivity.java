package com.example.pc.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button btLogin;
    //creating object of shared preference variable
    SharedPreferences pref;
    //creating a editor which will edit the shared preference values.
    SharedPreferences.Editor editor;
    EditText etUsername,etEmail,etMobile,etDOB;
    CheckBox chkRemember;
    String strUserName,strEmail,strMobile,strDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("MyPref",1); // 0 - for private mode

        editor = pref.edit();
        strUserName=pref.getString("uname",null);
        //check whether the value is set or not of shared preference variable stored in string.
        if(strUserName!=null)
        {
            //function
            alreadyLoggedIn();
        }
        else {
            //function
            logIn();
        }



    }
    private void logIn()
    {

        btLogin= (Button) findViewById(R.id.btLogin);
        etUsername= (EditText) findViewById(R.id.etUserName);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etMobile= (EditText) findViewById(R.id.etMobile);
        etDOB= (EditText) findViewById(R.id.etDOB);
        chkRemember=(CheckBox)findViewById(R.id.chkRemember);
        chkRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    editor.putBoolean("isRemember",true);
                    editor.commit();
                }
                else
                {
                    editor.putBoolean("isRemember",false);
                    editor.commit();

                }
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserName=etUsername.getText().toString();
                strEmail=etEmail.getText().toString();
                strMobile=etMobile.getText().toString();
                strDOB=etDOB.getText().toString();

                if(strUserName.length()!=0&&strEmail.length()!=0&&strMobile.length()!=0&&strDOB.length()!=0) {

                        //storing values using editor object
                        editor.putString("uname", strUserName);
                        editor.putString("pwd", strEmail);
                        editor.putString("mobile", strMobile);
                        editor.putString("dob", strDOB);
                        editor.commit();
                        Intent i = new Intent(MainActivity.this, UserProfile.class);
                        startActivity(i);

                }
                else
                {
                    if(TextUtils.isEmpty(strUserName)) {
                        etUsername.setError("Required");
                        return;
                    }
                    if(TextUtils.isEmpty(strEmail)) {
                        etEmail.setError("Required");
                        return;
                    }
                    if(TextUtils.isEmpty(strMobile)) {
                        etMobile.setError("Required");
                        return;
                    }
                    if(TextUtils.isEmpty(strDOB)) {
                        etDOB.setError("Required");
                        return;
                    }
                   // Toast.makeText(MainActivity.this,"All Fields Required",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void alreadyLoggedIn()
    {
        Intent i =new Intent(MainActivity.this,UserProfile.class);
        startActivity(i);
    }
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

package com.example.pc.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView tv1,tv2,tv3,tv4;
    Button btLogout;
    boolean chkSession;
    String strUserName,strEmail,strMobile,strDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        pref = getApplicationContext().getSharedPreferences("MyPref",1); // 0 - for private mode
        editor = pref.edit();
        tv1= (TextView) findViewById(R.id.tvUserName);
        tv2= (TextView) findViewById(R.id.tvEmail);
        tv3= (TextView) findViewById(R.id.tvMobile);
        tv4= (TextView) findViewById(R.id.tvDOB);
        btLogout= (Button) findViewById(R.id.btLogout);
        //fetching data from shared preference
        strUserName=pref.getString("uname",null);
        strEmail=pref.getString("pwd",null);
        strMobile=pref.getString("mobile",null);
        strDOB=pref.getString("dob",null);
        tv1.setText(strUserName);
        tv2.setText(strEmail);
        tv3.setText(strMobile);
        tv4.setText(strDOB);

        chkSession=pref.getBoolean("isRemember",false);
        if (chkSession==false) {
            //clearing data on logout.
            editor.clear();
            editor.commit();
        }

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                Intent i =new Intent(UserProfile.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}

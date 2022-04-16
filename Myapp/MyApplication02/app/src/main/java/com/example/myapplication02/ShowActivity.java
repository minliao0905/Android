package com.example.myapplication02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ShowActivity extends Activity {
    private TextView tv_name;
    private TextView tv_pwd;
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_show);
       Intent intent = getIntent();
       Bundle bundle = intent.getExtras();
       String name = bundle.getString("name");
       String pwd =  bundle.getString("password");
       tv_name = (TextView) findViewById(R.id.show_name);
       tv_pwd = (TextView) findViewById(R.id.show_pwd);
       tv_name.setText("用户名："+name);
       tv_pwd.setText("密  码："+pwd);

    }
}

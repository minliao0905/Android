package com.example.talkapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.talkapp.R;
import com.example.talkapp.server.ChatRoom;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_cnt;
    private EditText et_ip;
    private EditText et_name;
    private EditText et_port;
    private EditText myName;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_cnt = (Button) findViewById(R.id.btn_cnt);
        et_ip = findViewById(R.id.et_ip);
        et_port = findViewById(R.id.et_port);
        et_name = findViewById(R.id.et_name);
//        myName = findViewById(R.id.my_name);
        btn_cnt.setOnClickListener(LoginActivity.this);
    }

    public void onClick(View view) {
        String name = et_name.getText().toString();
        if ("".equals(name)) {
            Toast.makeText(this, "请输入用户名：", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LoginActivity.this, ChatRoom.class);
            intent.putExtra("name", et_name.getText().toString());
            intent.putExtra("ip", et_ip.toString());
            intent.putExtra("port", et_port.toString().trim());
            startActivity(intent);
        }
    }

}
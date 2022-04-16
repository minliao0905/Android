package com.example.study_6_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study_6_2.databinding.FragmentHomeBinding;

import java.util.Map;

public class loadqq extends Activity {

    Button btn_save ;
    EditText ed_number;
    EditText ed_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadqq);
        ed_number = (EditText) findViewById(R.id.et_number);
        ed_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_save = (Button) findViewById(R.id.btn_login);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = ed_number.getText().toString();
                String password = ed_pwd.getText().toString();
                boolean issaveInfo = SPSave.savedUserInfo(loadqq.this,username,password);
                if(issaveInfo){
                    Toast.makeText(loadqq.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //跳转到主界面查看消息。
                    Intent intent = new Intent(loadqq.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(loadqq.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

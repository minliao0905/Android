package com.example.study6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends Activity {
    private EditText etNumber;
    private EditText etPassword;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
//
//        Map<String,String> userInfo = SPSaveQQ.getUserInfo(this);
//        etNumber = (EditText) findViewById(R.id.et_number);
//        etPassword = (EditText) findViewById(R.id.et_pwd);
//        btn_login = (Button) findViewById(R.id.btn_login);
//        etNumber.setText(userInfo.get("userName"));
//        etPassword.setText(userInfo.get("userpassword"));
        btn_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String number = etNumber.getText().toString();
                String pwd = etPassword.getText().toString();

           if(TextUtils.isEmpty(number)){
               Toast.makeText(MainActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
               return ;
           }
           if(TextUtils.isEmpty(pwd)){
               Toast.makeText(MainActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
               return ;
           }
//                保存用户信息
        boolean isSaveSuccess  = SPSaveQQ.savedUserInfo(MainActivity.this,number,pwd);

        if(isSaveSuccess){
            Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
        }
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("name", number);
                bundle.putCharSequence("password", pwd);
                intent.putExtras(bundle);
                startActivity(intent);
//                Toast.makeText(MainActivity.this,"登录成功"+number + pwd,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
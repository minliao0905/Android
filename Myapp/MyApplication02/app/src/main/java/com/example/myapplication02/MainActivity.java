package com.example.myapplication02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Map;
import java.util.Stack;

public class MainActivity extends AppCompatActivity{
    private EditText etNumber;
    private EditText etpassword;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        etNumber = findViewById(R.id.et_number);
        etpassword = findViewById(R.id.et_pwd);

        initView();
        Map<String,String> userInfo = FileSaveQQ.getUserInfo(this);
        if(userInfo !=null)
        {
            etNumber.setText(userInfo.get("number"));
            etpassword.setText(userInfo.get("password"));
        }
    }

   public void initView(){
       Onclick click = new Onclick();
       btn_login.setOnClickListener(click);
   }
    private class Onclick implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            //当单击登录按钮时，获取账号和密码
            String number = etNumber.getText().toString().trim();
            String pwd = etpassword.getText().toString().trim();
            if(TextUtils.isEmpty(number)){
                Toast.makeText(MainActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                return ;
            }
            if(TextUtils.isEmpty(pwd)){
                Toast.makeText(MainActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                return ;
            }
               Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            //保存用户信息
        boolean isSaveSuccess  = FileSaveQQ.saveUserInfo(MainActivity.this,number,pwd);

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

        }
    }

}
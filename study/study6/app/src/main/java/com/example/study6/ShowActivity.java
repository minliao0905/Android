package com.example.study6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();//获取Intent对象

        Bundle bundle = intent.getExtras();//获取传递的Bundle信息
        TextView name = (TextView) findViewById(R.id.show_name);//获取显示姓名的TextView组件
        name.setText("用户姓名："+bundle.getString("name"));//获取输入的姓名并显示到TextView组件中
        TextView phone = (TextView) findViewById(R.id.show_pwd);//获取显示手机号码的TextView组件
        phone.setText("用户密码："+bundle.getString("password"));//获取输入的电话号码并显示到TextView组件中


    }

}

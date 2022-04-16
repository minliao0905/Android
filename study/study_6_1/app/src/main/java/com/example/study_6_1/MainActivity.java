package com.example.study_6_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn_save ;
    Button btn_take;
    EditText ed_text;
    TextView tv_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_save = (Button) findViewById(R.id.save_btn);
        btn_take = (Button) findViewById(R.id.take_btn);
        ed_text = (EditText) findViewById(R.id.ed_text);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String text = ed_text.getText().toString();
                boolean issaveInfo = SPSave.savedUserInfo(MainActivity.this,text);
                boolean issaveintofile = FileSave.saveUserInfo(MainActivity.this,text);
                if(issaveInfo){
                    Toast.makeText(MainActivity.this, "xml保存成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "xml保存失败", Toast.LENGTH_SHORT).show();
                }
               if(issaveintofile){
                   Toast.makeText(MainActivity.this,"file保存成功",Toast.LENGTH_SHORT).show();
               }
            }
        });
        btn_take.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String,String> userInfo = SPSave.getUserInfo(MainActivity.this);
                if(userInfo!=null){
                    tv_text = (TextView)findViewById(R.id.show_text) ;
                    String  text = userInfo.get("text");
                    if(text!=null){
                        tv_text.setText(text);
                        Toast.makeText(MainActivity.this, "获取成功"+text, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}
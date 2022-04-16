package com.example.study_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private  Button btn1 ;
    private  Button btn2;
    private  Button btn3 ;
    private  Button btn4 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        setListeners();

    }
    public void setListeners(){
        Onclick onclick = new Onclick();

        btn1.setOnClickListener(onclick);
        btn2.setOnClickListener(onclick);
        btn3.setOnClickListener(onclick);
        btn4.setOnClickListener(onclick);
    }

    private class Onclick implements  View.OnClickListener{
        @Override
            public void onClick(View v){
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.btn1:
                    intent = new Intent(MainActivity.this, main1.class);
                    break;
                case R.id.btn2:
                    intent = new Intent(MainActivity.this, main2.class);
                    break;
                case R.id.btn3:
                    intent = new Intent(MainActivity.this, main3.class);
                    break;
                case R.id.btn4:
                    intent = new Intent(MainActivity.this, main4.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
        }

}
package com.example.study3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final long CLICK_INTERVAL_TIME = 300;
    private static long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.ed_show);
        TextView textView = (TextView) findViewById(R.id.text_home);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button btn4 = (Button) findViewById(R.id.btn4);
        Button btn5 = (Button) findViewById(R.id.shiyan4);
        LinearLayout linearLayout = findViewById(R.id.lay1);
        btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Editable str = editText.getText();
                                        textView.setText(str);
                                    }
                                }
        );
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "button2当前显示", Toast.LENGTH_SHORT).show();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity","log显示，button3");
            }
        });
        btn4.setOnClickListener(new buttonListener());

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,listview_3.class);
                startActivity(intent);
            }
        });
    }


    private class buttonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            long currentTimeMillis = SystemClock.uptimeMillis();//两次点击间隔时间小于300ms代表双击
            if (currentTimeMillis - lastClickTime < CLICK_INTERVAL_TIME) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lay1);
                linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                return;
            }
            lastClickTime = currentTimeMillis;

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lay1);
            linearLayout.setBackgroundColor(Color.parseColor("#FF3700B3"));
        }
    }

}

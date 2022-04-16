package com.example.study_1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class main3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();    //获取Intent对象
        setContentView(R.layout.main_3);

    }
}

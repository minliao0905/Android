package com.example.myemo.Splash;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myemo.MainActivity;
import com.example.myemo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
     private CountDownProgressView countDownProgressView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState  ) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_splash);
        countDownProgressView = findViewById(R.id.countdownProgressView);
        initData();
        initListener();
    }


    public void initData() {
        countDownProgressView.setTimeMillis(2000);
        countDownProgressView.setProgressType(CountDownProgressView.ProgressType.COUNT_BACK);
        countDownProgressView.start();
    }


    public void initListener() {
        countDownProgressView.setOnClickListener(v -> {
            countDownProgressView.stop();
                startActivity();
        });

        countDownProgressView.setProgressListener(progress -> {
            if (progress == 0) {
                // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    startActivity();
                }
            }
        });
    }

    public void startActivity() {
        Intent it=new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
        startActivity(it);
        finish();
        countDownProgressView.stop();
    }

}

package com.example.study_6_2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.study_6_2.databinding.ActivityMainBinding;

import java.lang.reflect.Field;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView tv_number ;
    private TextView tv_pwd;
    @NonNull ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        设置标题栏
//       获取自定义标题栏权限
//       加载自定义标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Map<String,String> userInfo = SPSave.getUserInfo(MainActivity.this);
//判断加载页面若已经登录就跳转主页面，
        if(userInfo.get("username")!=null&&userInfo.get("password")!=null){
            Toast.makeText(MainActivity.this, "当前用户已登录", Toast.LENGTH_SHORT).show();
            setContentView(binding.getRoot());
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
       else{

           Toast.makeText(MainActivity.this,"当前用户未登录",Toast.LENGTH_SHORT).show();
//通过intent跳转页面，然后同时也通过intent在loadqq页面跳转回来主页面
           Intent intent = new Intent(MainActivity.this,loadqq.class);

            startActivity(intent);
        }
//        setContentView(binding.getRoot());
//

    }



}
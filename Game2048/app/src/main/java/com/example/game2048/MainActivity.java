package com.example.game2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui.menu.Game_Record;
import com.example.ui.view.GameView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    public static MainActivity mainActivity = null;
    private int score = 0;
    public TextView tvScore;
    public TextView tvHScore;
    public Button restartBtn ;
    int count =0 ;
    private Map<String,Integer> userinfo;
    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvHScore = (TextView) findViewById(R.id.tvHScore);
        tvHScore.setText(getHighestSore()+"分");
        count = userinfo.get("nowcount");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_back:
                finish();
                break;
            case R.id.action_myrecord:
                startActivity(new Intent(MainActivity.this,Game_Record.class));
                break;
            default:
                break;
        }
        return true;
    }

    public void clearScore(){
        RefreshHightScore();
        score = 0;
        showScore();
    }

    public void showScore(){
        tvScore.setText(score+" 分");
        tvHScore.setText(getHighestSore() + "分");
    }

    public void addScore(int s){
        score+=s;
        showScore();
    }

    public int getHighestSore(){
        int Mnum=0;
        //获取记录的最高分
        userinfo = SPSave.getUserInfo(MainActivity.this);
        if(userinfo.get("HightestScore")!=null){
         Mnum   = userinfo.get("HightestScore");
        }
        return Mnum;
    }
    public void RefreshHightScore(){
        int Max = getHighestSore();
        if(score>Max){
            Max = score;
        }
        SPSave.savedUserInfo(MainActivity.this,score,Max,count);//更新最高分
    }
   public int getScore(){
        return score;
   }

    //添加监听器，重开游戏

}

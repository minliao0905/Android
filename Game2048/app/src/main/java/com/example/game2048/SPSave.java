package com.example.game2048;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
//问题：关于记录的关键id,无法确定保存，不知道该如何确定当前应该保存的id,本实验中利用count记录，但不知道如何获取缺少的id，即用户上次退出游戏所应该保存的记录应该是第几条
//每次用户开始游戏，都将从第一条记录开始修改，直到保存到15条为止，循环更新
public class SPSave {

    public static boolean savedUserInfo(Context context, int score,int hightscore,int count){
        SharedPreferences sp = context.getSharedPreferences("scoredata",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        count=count%15;   //最多保存15条记录
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");// HH:mm:ss//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        edit.putString("time"+count, simpleDateFormat.format(date));
        edit.putInt("HightestScore",hightscore);
        edit.putString("score"+count, String.valueOf(score));
        count++;
        edit.putInt("nowcount",count);  //注意要在存放之前就把count修改不然count永远是0

        edit.commit();
        return true;
    }
    public static Map<String,Integer> getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("scoredata",Context.MODE_PRIVATE);
        int hightestScore = sp.getInt("HightestScore",0);
        int count = sp.getInt("nowcount",0);
        Map<String,Integer> userMap = new HashMap<String,Integer>();
        userMap.put("HightestScore",hightestScore);
        userMap.put("nowcount",count);
        return userMap;
    }
    public static Map<String,String> getUserRecord(Context context){
        SharedPreferences sp = context.getSharedPreferences("scoredata",Context.MODE_PRIVATE);
        //获取保存数据，遍历获取。
        Map<String,String> userMap = new HashMap<String,String>();
        for(int i=0;i<10;i++){
            String score= sp.getString("score"+i,null);
            String time = sp.getString("time"+i,null);
            if(score!=null){
                userMap.put("score"+i,score);
            }
            if(time!=null){
                userMap.put("time"+i,time);
            }
        }
        return userMap;
    }
}

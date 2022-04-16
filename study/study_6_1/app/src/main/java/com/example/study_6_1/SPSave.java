package com.example.study_6_1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SPSave {
    public static boolean savedUserInfo(Context context, String text){
        SharedPreferences sp = context.getSharedPreferences("data1",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("text",text);
        edit.commit();
        return true;
    }
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("data1",Context.MODE_PRIVATE);
        String text= sp.getString("text",null);
        Map<String,String> userMap = new HashMap<String,String>();
        userMap.put("text",text);
        return userMap;
    }
}

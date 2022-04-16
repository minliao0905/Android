package com.example.study_6_2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SPSave {
    public static boolean savedUserInfo(Context context, String username,String password){
        SharedPreferences sp = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("username",username);
        edit.putString("password",password);
        edit.commit();
        return true;
    }
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
        String username= sp.getString("username",null);
        String password = sp.getString("password",null);
        Map<String,String> userMap = new HashMap<String,String>();
        userMap.put("username",username);
        userMap.put("password",password);
        return userMap;
    }
}

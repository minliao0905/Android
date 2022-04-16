package com.example.study6;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SPSaveQQ {
    public static boolean savedUserInfo(Context context,String number,String password){
        SharedPreferences sp = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName",number);
        edit.putString("userpassword",password);
        edit.commit();
        return true;
    }
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String number = sp.getString("userName",null);
        String password = sp.getString("userpassword",null);
        Map<String,String> userMap = new HashMap<String,String>();
        userMap.put("numer",number);
        userMap.put("password",password);
        return userMap;
    }
}

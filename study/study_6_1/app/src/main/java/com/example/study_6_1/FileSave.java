package com.example.study_6_1;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileSave {
    //保存QQ账号和密码到data.txt 文件中
    public static boolean saveUserInfo(Context context, String text){
        try{
            FileOutputStream fos = context.openFileOutput("data.txt",Context.MODE_PRIVATE);
            fos.write((text).getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Map<String,String> getUserInfo(Context context){
        String content = "";
        try{
            FileInputStream fis  = context.openFileInput("data.txt");
            byte[] buffer = new byte[fis.available()] ;
            content = new String(buffer);
            Map<String,String> userMap = new HashMap<String,String>();
            userMap.put("text",content);
            fis.close();
            return userMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

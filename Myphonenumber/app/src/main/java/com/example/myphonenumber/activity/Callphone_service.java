package com.example.myphonenumber.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.myphonenumber.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Callphone_service extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callphone_service);
        Button callbtn = findViewById(R.id.callservicebtn);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText teltext = findViewById(R.id.tel);
                String tel =teltext.getText().toString().trim();
                if(TextUtils.isEmpty(tel)||!isMobileNO(tel)){
                    //如果号码为空
                    Toast.makeText(Callphone_service.this,"请填写正确的联系号码",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"   + tel));
                    //检查权限但没必要
//                    if (ActivityCompat.checkSelfPermission(Callphone_service.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
//                    {
//                        return;
//                    }
                    startActivity(intent);
                }
            }
        });
    }
    //判断电话号码格式
    public static boolean isMobileNO(String mobiles){
        boolean flag = false;
        try{
//            String regex = "[1][34578][0-9]{9}"; //手机号码的格式：第一位只能为1，第二位可以是3，4，5，7，8，第三位到第十一位可以为0-9中任意一个数字
            Pattern p = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
//            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            //LOG.error("验证手机号码错误", e);
            flag = false;
        }
        return flag;
    }


}

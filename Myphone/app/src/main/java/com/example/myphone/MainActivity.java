package com.example.myphone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myphone.db.PersonDBOpenHelper;
import com.example.myphone.enty.SmSInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText myed_name;
    private EditText myed_phone;
    private Button AddBtn;
    private Button DeleteBtn;
    private Button UpdateBtn;
    private Button QueryBtn;
    private ContentResolver resolver;
    private Uri uri;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//初始化控件
    }
    private void init(){
        myed_name = findViewById(R.id.ed_name);
        myed_phone = findViewById(R.id.ed_phone);
        AddBtn = findViewById(R.id.btn1);
        DeleteBtn = findViewById(R.id.btn2);
        UpdateBtn = findViewById(R.id.btn3);
        QueryBtn = findViewById(R.id.btn4);
        AddBtn.setOnClickListener(this);
        DeleteBtn.setOnClickListener(this);
        UpdateBtn.setOnClickListener(this);
        QueryBtn.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
       resolver = getContentResolver();
       uri = Uri.parse("content://com.example.myphone.userphonedb/userinfo");
       values = new ContentValues();
       switch(v.getId()){
            case R.id.btn1:
                Random random = new Random();
                values.put("name","add_itcast"+random.nextInt(10));
                Uri newuri = resolver.insert(uri,values);
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                int deleteCount = resolver.delete(uri,"name=?",new String[]{"itcast0"});
                Toast.makeText(this,"删除成功"+deleteCount+"行",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                List<Map<String,String>> data = new ArrayList<Map<String,String>>();
                Cursor  cursor = resolver.query(uri,new String[]{"_id","name"},null,null);
                while(cursor.moveToNext()){
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("_id",cursor.getString(0));
                    map.put("name",cursor.getString(1));
                    data.add(map);
                }
                cursor.close();
                break;
           case R.id.btn4:
               values.put("name","update_itcast");
               int updateCount = resolver.update(uri,values,"name=?",new String[]{"itcast1"});
               Toast.makeText(this,"当前数据更新成功！",Toast.LENGTH_SHORT).show();
               break;
           default:
               break;
        }
    }
    private void createDB(){
        PersonDBOpenHelper helper = new PersonDBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        for(int i=0;i<3;i++){
            ContentValues values = new ContentValues();
            values.put("name","itcast"+i);
            db.insert("userphone",null,values);
        }
        db.close();
    }

    public void readSMS(View v){
        Uri uri = Uri.parse("content://sms/");
        ContentResolver  resolver = getContentResolver();
        Cursor cursor = resolver.query(uri,new String[]{"_id","address","type","body","date"},null,null,null);
        List<SmSInfo> smsInfoList = new ArrayList<SmSInfo>();
        if(cursor !=null&&cursor.getCount()>0){
            while(cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String address = cursor.getString(1);
                int type = cursor.getInt(2);
                String body = cursor.getString(3);
                long data = cursor.getLong(4);
                SmSInfo smsInfo = new SmSInfo(id, address, type, body, data);
                smsInfoList.add(smsInfo);
            }
            cursor.close();
        }
       else if(cursor.getCount() == 0){
            Toast.makeText(MainActivity.this,"当前获取信息为空！",Toast.LENGTH_SHORT).show();
        }
        for(int i=0 ;i<smsInfoList.size();i++){
            Log.v("Main",smsInfoList.toString());
        }

    }


}
package com.example.myemo.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//创建数据库帮助类 实现对数据库的创建修改编辑等操作
public class DatabaseHelper extends SQLiteOpenHelper {
        // 创建数据库 ，数据表 user log
    public static final  String Create_logtable = "create table user_log ("   + "logid integer primary key autoincrement, "
                + "log text,  "
                + "logtime  datetime,  " +  " styleid integer not null ) ";
    public static final String Create_styletable = "create table style( " + "styleid integer primary key autoincrement, "
            + "background text, " + " textcolor text )";
  public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version)
    {
       super(context, name, cursorFactory, version);
       Log.v("Createdb","创建数据库成功！");
    }     
     
    @Override    
    public void onCreate(SQLiteDatabase db) {     
        // TODO 创建数据库后，对数据库的操作
//        创建日志表和 样式表
        db.execSQL(Create_logtable);
        db.execSQL(Create_styletable);
        Log.v("Createdb" ,"创建两个数据库表成功！");
    }     
     
    @Override    
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {     
        // TODO 更改数据库版本的操作     
    }     
     
@Override    
public void onOpen(SQLiteDatabase db) {     
        super.onOpen(db);       
        // TODO 每次成功打开数据库后首先被执行     
    }
}     
package com.example.myphone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PersonDBOpenHelper extends SQLiteOpenHelper {
  //创建一个数据库
    public PersonDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PersonDBOpenHelper(Context context) {
        super(context,"userphone.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//创建数据库的表
        db.execSQL("create table userinfo(_id,integer primary key autoincrement, name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

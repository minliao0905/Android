package com.example.myphonenumber.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class PersonProvider  extends ContentProvider {
    //定义一个uri路径的匹配器，如果路径匹配不成功则返回-1
    private static UriMatcher mUriMatcher = new UriMatcher(-1);
    private static final int SUCCESS = 1;
    private PersonDBOpenHelper hepler;
  //添加路径匹配器的规则
    static {
        mUriMatcher.addURI("com.example.myphone.userphonedb","userinfo",SUCCESS);
    }
    @Override
    public boolean onCreate() {
       hepler  = new PersonDBOpenHelper(getContext());
       return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
       int code = mUriMatcher.match(uri);
       if(code == SUCCESS){
           SQLiteDatabase db = hepler.getReadableDatabase();
           return db.query("userphone",projection,selection,selectionArgs,null,null,sortOrder);
       }else{
           throw new IllegalArgumentException("路径不正确，无法提供数据！");
       }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = mUriMatcher.match(uri);
        if(code == SUCCESS){
            SQLiteDatabase db = hepler.getReadableDatabase();
            long rowId = db.insert("userphone",null,values);
            if(rowId>0){
                Uri insertedUri = ContentUris.withAppendedId(uri,rowId);
                getContext().getContentResolver().notifyChange(insertedUri,null);
                return insertedUri;
            } db.close();
            return uri;
        }else{
            throw new IllegalArgumentException("路径不正确，无法插入数据");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
      int code = mUriMatcher.match(uri);
      if(code == SUCCESS){
          SQLiteDatabase db = hepler.getWritableDatabase();
          int count = db.delete("userphone",selection,selectionArgs);
          if(count>0){
              getContext().getContentResolver().notifyChange(uri,null);
          }
          db.close();
          return count;
      }else{
          throw new IllegalArgumentException("路径不正确，无法删除数据");
      }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = mUriMatcher.match(uri);
        if(code == SUCCESS){
            SQLiteDatabase db = hepler.getWritableDatabase();
            int count = db.update("userphone",values,selection,selectionArgs);
            if(count>0){
                getContext().getContentResolver().notifyChange(uri,null);
            }
            db.close();
            return count;
        }else{
            throw new IllegalArgumentException("路径不正确，无法更新数据");
        }
    }
}

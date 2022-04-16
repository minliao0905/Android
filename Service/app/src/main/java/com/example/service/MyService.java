package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
   @Nullable
    @Override
    public IBinder onBind(Intent intent){
       // throw new UnsupportedOperationException("No yet implemented");
       return null;
    }
    public void onCreate(){
        super.onCreate();
        Log.i("StartService","onCreate()");
    }
    public int onStartCommand(Intent intent ,int flags,int startId){
        Log.i("StartService","onStartCommand()");
        return super.onStartCommand(intent,flags,startId);
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("StartService","onDestroy()");
    }


}

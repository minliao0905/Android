package com.example.bindservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.invoke.MutableCallSite;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText path;
    private Intent intent;
    private myConn conn;
    MusicService.MyBinder binder;
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        path = (EditText) findViewById(R.id.ed_inputpath);
        conn = new myConn();
        intent = new Intent(this, MusicService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);

    }
    private class myConn implements ServiceConnection{
        public void onServiceConnected(ComponentName name,IBinder service){
            binder = (MusicService.MyBinder) service;

        }
        public void onServiceDisconnected(ComponentName name){

        }
    }
    @Override
    public void onClick(View v) {
        String pathway = path.getText().toString().trim();
        switch(v.getId()){
            case R.id.tv_paly:
                if(!TextUtils.isEmpty(pathway)){
                    binder.play(pathway);
                }else{
                    Toast.makeText(this, "找不到音乐文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_pause:
                binder.pause();
                break;
            default:
                break;
        }
    }
    protected void onDestroy(){
        unbindService(conn);
        super.onDestroy();
    }
}
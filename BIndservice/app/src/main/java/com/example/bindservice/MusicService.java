package com.example.bindservice;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends Service {
   private static final String TAG = "MusicService";
   public MediaPlayer mediaPlayer;

    class MyBinder extends Binder{
       public void play(String path){
           try{
               if(mediaPlayer == null){
                   mediaPlayer = new MediaPlayer();
                   //创建一个音乐播放器
                   mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                   mediaPlayer.setDataSource(path);
                   mediaPlayer.prepare();
                   mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                       public void onPrepared(MediaPlayer  mp){
                           mediaPlayer.start();
                       }
                   });
               }else{
                   int position = mediaPlayer.getCurrentPosition();
                   mediaPlayer.seekTo(position);
                   try{
                       mediaPlayer.prepare();
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                   mediaPlayer.start();
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       public void pause(){
           if(mediaPlayer!=null && mediaPlayer.isPlaying()){
               mediaPlayer.pause();
           }else if(mediaPlayer!=null &&(!mediaPlayer.isPlaying())){
               mediaPlayer.start();
           }
       }
   }

   public void onCreate(){
        super.onCreate();
   }
   public int getCurrentProgress(){
        if(mediaPlayer !=null & mediaPlayer.isPlaying()){
            return mediaPlayer.getCurrentPosition();
        }else if(mediaPlayer!=null & (!mediaPlayer.isPlaying())){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
   }
   public void onDestroy(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
   }
   @Override
    public IBinder onBind(Intent intent){
        return new MyBinder();
   }
}

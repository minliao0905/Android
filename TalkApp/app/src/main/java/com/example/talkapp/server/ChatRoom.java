package com.example.talkapp.server;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.talkapp.R;
public class ChatRoom extends AppCompatActivity implements View.OnClickListener{
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private Button back;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private Socket socketSend;
    private String ip="192.168.1.3";
    private String port="6666";
    DataInputStream dis;
    DataOutputStream dos;
    boolean isRunning = false;
    private TextView myName;
    private String recMsg;
    private boolean isSend=false;
    private String name;
    private Handler handler = new Handler(Looper.myLooper()){//获取当前进程的Looper对象传给handler
        @Override
        public void handleMessage(@NonNull Message msg){//?
            if(!recMsg.isEmpty()){
                addNewMessage(recMsg,Msg.TYPE_RECEIVED);//接收发送过来的消息
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Intent intent =getIntent();
        name=intent.getStringExtra("name");
        inputText = findViewById(R.id.input_text);
        send=findViewById(R.id.send);
        send.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder dialog= new AlertDialog.Builder(ChatRoom.this);
                dialog.setTitle("退出");
                dialog.setMessage("退出登录?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();//finish()是在程序执行的过程中使用它来将对象销毁,finish（）方法用于结束一个Activity的生命周期
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();//让返回键开始启动
            }
        });
//        以使用Activity的runOnUiThread方法将toast的显示发布到主线程。 从主线程调用时，
//        runOnUiThread方法立即执行传递的操作，但是当从主线程以外调用时，它会将传递的操作发布到主线程的事件队列。
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(ChatRoom.this);
                msgRecyclerView= findViewById(R.id.msg_recycler_view);
                msgRecyclerView.setLayoutManager(layoutManager);
                adapter = new MsgAdapter(msgList);
                msgRecyclerView.setAdapter(adapter);
            }
        });
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    if((socketSend = new Socket(ip,Integer.parseInt(port)))==null){
                        Log.v("ttw","发送了一条消息1");
                    }
                    else{
                        isRunning = true;
                        Log.v("ttw","发送了一条消息2");
                        dis = new DataInputStream(socketSend.getInputStream());
                        dos = new DataOutputStream(socketSend.getOutputStream());
                        new Thread(new receive(),"接收线程").start();
                        new Thread(new Send(),"发送线程").start();
                    }
                }catch(Exception e){
                    isRunning = false;
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(ChatRoom.this, "连接服务器失败！！！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    try{
                        socketSend.close();
                    }catch(IOException e1){
                        e1.printStackTrace();
                    }
                    finish();
                }
            }
        }).start();
    }
    public void addNewMessage(String msg,int type){
        Msg message = new Msg(msg,type);
        msgList.add(message);
        adapter.notifyItemInserted(msgList.size()-1);
        msgRecyclerView.scrollToPosition(msgList.size()-1);
    }

    @Override
    public void onClick(View view){
        String content = inputText.getText().toString();
        @SuppressLint("SimpleDateFormat")
        String date = new SimpleDateFormat("hh:mm:ss").format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(content).append("\n\n"+date);
        content = sb.toString();
        if(!"".equals(content)){
            Msg msg = new Msg(content,Msg.TYPE_SENT);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size()-1);
            msgRecyclerView.scrollToPosition(msgList.size()-1);
            isSend = true;
        }
        sb.delete(0,sb.length());
    }
    class Send implements Runnable{
        @Override
        public void run(){
            while(isRunning){
                String content = inputText.getText().toString();
                Log.d("ttw","发了一条消息");
                if(!"".equals(content)&&isSend){
                    @SuppressLint("SimpleDateFormat")
                    String date = new SimpleDateFormat("hh:mm:ss").format(new Date());
                    StringBuilder sb = new StringBuilder();
                    sb.append(content).append("\n\n来自：").append(name).append("\n"+date);
                    content = sb.toString();
                    try{
                        dos.writeUTF(content);// 写入socket流中
                        sb.delete(0,sb.length());
                        Log.d("ttw","发送了一条消息");
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    isSend = false;
                    inputText.setText("");
                }
            }
        }
    }
    class receive implements Runnable{
        public void run(){
            recMsg = "";
            while(isRunning){
                try{
                    recMsg = dis.readUTF();
                    Log.v("ttw","收到了一条消息"+"recMsg: "+ recMsg);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(!TextUtils.isEmpty(recMsg)){
                    Log.v("ttw","inputStream:"+dis);
                    Message message = new Message();
                    message.obj=recMsg;
                    handler.sendMessage(message);
                }
            }
        }
    }
}

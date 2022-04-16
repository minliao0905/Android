package com.example.myphonenumber.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.SyncStateContract;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myphonenumber.Adapter.MessageBoxListAdapter;
import com.example.myphonenumber.MainActivity;
import com.example.myphonenumber.R;
import com.example.myphonenumber.entity.MessageBean;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class message_edit extends Activity {
    private String number;
    private String name;
    private String message;
    private List<MessageBean> messageBeanList = new ArrayList<MessageBean>();;
    private ListView talkView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(message_edit.this,"用户跳转短信编辑界面",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.message_edit);
        init();
    }
    public void init(){
        Intent intent = getIntent();
        number = intent.getStringExtra("usernumber");
        name = intent.getStringExtra("username");
        TextView  toptext = findViewById(R.id.text_room);
        EditText teltext = findViewById(R.id.smstelnumber_ed);
        if(!(TextUtils.isEmpty(name)&&TextUtils.isEmpty(number))){
            toptext.setText("To "+name+"-"+number);
            teltext.setText(number);
        }

        talkView = findViewById(R.id.msg_edit_view);
        MessageBoxListAdapter messageAdapter = new MessageBoxListAdapter(message_edit.this,messageBeanList);
        Button sendbtn = findViewById(R.id.send);
        //发送短信按钮   //获取number和短信信息
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = teltext.getText().toString().trim();
                if(TextUtils.isEmpty(number)||!isMobileNO(number)){
                    Toast.makeText(message_edit.this,"请正确填写发送号码",Toast.LENGTH_SHORT).show();
                    return ;
                }

                EditText message_ed = findViewById(R.id.message_edit_ed);
                message = message_ed.getText().toString();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(message_edit.this,"发送信息不能为空",Toast.LENGTH_SHORT).show();
                    return ;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");// HH:mm:ss//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                MessageBean messageBean = new MessageBean(name, simpleDateFormat.format(date), message, R.layout.message_list_say_me_item);
                sendSMS(number,message);
                messageBeanList.add(messageBean);
                messageAdapter.notifyDataSetChanged();
                message_ed.setText("");
            }
        });
        talkView.setAdapter(messageAdapter);
        talkView.setDivider(null);
        talkView.setSelection(messageBeanList.size());
        Button backbtn = findViewById(R.id.meditbackarr);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 直接调用短信接口发短信
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(String phoneNumber, String message) {
        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）   // create the deilverIntent parameter

        List<String> divideContents = smsManager.divideMessage(message);
        Intent sentIntent = new Intent("SENT_SMS_ACTION");
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);

        Intent deliverIntent = new Intent("DELIVERED_SMS_ACTION");
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,  deliverIntent, 0);

        for (int i = 0; i < divideContents.size(); i++) {
            System.out.println("sendSMS text=" + divideContents.get(i));
            smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
        }

    }
    //判断电话号码格式
    public static boolean isMobileNO(String mobiles){
        boolean flag = false;
        try{
            Pattern p = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            //LOG.error("验证手机号码错误", e);
            flag = false;
        }
        return flag;
    }


}

package com.example.myphonenumber.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myphonenumber.Adapter.SMSAdpter;
import com.example.myphonenumber.MainActivity;
import com.example.myphonenumber.entity.RexseeSMS;
import com.example.myphonenumber.R;
import com.example.myphonenumber.entity.SMSBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class message_show extends Activity {
    private ListView smsListView;
    private SMSAdpter smsAdpter;
    private RexseeSMS rsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_list_view);
        smsListView = (ListView) findViewById(R.id.sms_list);
        smsAdpter = new SMSAdpter(message_show.this);
        rsms = new RexseeSMS(message_show.this);
        List<SMSBean> list_mmt = rsms.getThreadsNum(rsms.getThreads(0));
        // 注入短信列表数据
        smsAdpter.assignment(list_mmt);
        // 填充数据
        smsListView.setAdapter(smsAdpter);
        // 短信列表项点击事件
        smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Map<String, String> map = new HashMap<String, String>();
                SMSBean sb = (SMSBean) smsAdpter.getItem(position);
//                map.put("phoneNumber", sb.getAddress());
//                map.put("threadId", sb.getThread_id());
                Intent intent = new Intent(message_show.this,
                        MessageBoxList.class);

//                myMap myMap = new myMap();
//                myMap.setMap(map);
//                Bundle bundle = new Bundle();
//                bundle.put("threadid", myMap);
                intent.putExtra("threadid",sb.getThread_id());
                startActivity(intent);
            }
        });

        //添加按钮点击事件转换到信息编辑界面
        Button phone_main = findViewById(R.id.phone_main);
        phone_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("message_showActivity","从当前短信界面跳转到主界面");
                startActivity(new Intent(message_show.this, MainActivity.class));
            }
        });
        ImageButton sms_editbtn = findViewById(R.id.smsaddnew_btn);
        sms_editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("message_addnew","用户点击编辑短信");
                startActivity(new Intent(message_show.this,message_edit.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        List<SMSBean> list_mmt = rsms.getThreadsNum(rsms.getThreads(0));
       smsAdpter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<SMSBean> list_mmt = rsms.getThreadsNum(rsms.getThreads(0));
        smsAdpter.notifyDataSetChanged();
    }
    public class myMap implements Serializable {
        private Map<String,String> map;
        private static  final long serialVersionUID = 1L;
        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String,String> map) {
            this.map = map;
        }
    }
}

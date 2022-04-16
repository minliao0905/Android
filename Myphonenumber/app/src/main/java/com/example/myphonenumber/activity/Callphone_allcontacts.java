package com.example.myphonenumber.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.myphonenumber.Adapter.DialAdapter;
import com.example.myphonenumber.R;
import com.example.myphonenumber.entity.CallLogBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Callphone_allcontacts extends Activity {
    private DialAdapter allAdapter ;
    private ListView alllist ;
    private List callogs;
    private AsyncQueryHandler asynccall_contact_Query;
    private static final String[] contact_projection = {
            CallLog.Calls.DATE, // 日期
            CallLog.Calls.NUMBER, // 号码
            CallLog.Calls.TYPE, // 类型
            CallLog.Calls.CACHED_NAME, // 名字
            CallLog.Calls._ID, // id
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callphone_allcontacts);
        asynccall_contact_Query = new Callphone_allcontacts.MyAsyncQueryHandler(getContentResolver()); //调用私有内部类·，所以调用的时候要修改
        init();
    }
    public void onStart() {
        super.onStart();
        Log.v("callphone_allcontacts:", "onstart");
        if(allAdapter!=null){
            init();  //在更新列表前一定要记得更新数据，重新调用数据库查找
            allAdapter.notifyDataSetChanged();
        }
    }
    public void init(){
        alllist = findViewById(R.id.call_allcontact);
        Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
        asynccall_contact_Query.startQuery(0, null, uri, contact_projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER); //按时间排序
    }

    // 写一个异步查询类,用于查询通话记录
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }
        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                callogs = new ArrayList();
                SimpleDateFormat sfd = new SimpleDateFormat("MM-dd hh:mm");
                Date date;
                while (cursor.moveToNext()) { // 游标移动到第一项 //存放数据到callLogs
                    String number = cursor.getString(1);
                        date = new Date(cursor.getLong(0));
                        int type = cursor.getInt(2);
                        String cachedName = cursor.getString(3);// 缓存的名称与电话号码，如果它的存在
                        int id = cursor.getInt(4);
                        CallLogBean callLogBean = new CallLogBean();
                        callLogBean.setId(id);
                        callLogBean.setNumber(number);
                        callLogBean.setName(cachedName);
                        if (null == cachedName || "".equals(cachedName)) {
                            callLogBean.setName(number);
                        }
                        callLogBean.setType(type);
                        callLogBean.setDate(sfd.format(date));
                        callogs.add(callLogBean);
                    }
                }else{
                Log.v("call_logall" ,"当前查找为空");
            }
//设置Adapter
                if (callogs.size() > 0) {
                    setAdapter(callogs);
                }
            super.onQueryComplete(token, cookie, cursor);
        }

    }

    private void setAdapter(List callLogs) {
        allAdapter = new DialAdapter(this, callLogs);

        alllist.setAdapter(allAdapter);

    }
}

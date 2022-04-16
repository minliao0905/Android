package com.example.myphonenumber.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.*;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.example.myphonenumber.Adapter.DialAdapter;
import com.example.myphonenumber.MainActivity;
import com.example.myphonenumber.R;
import com.example.myphonenumber.db.PersonDBOpenHelper;
import com.example.myphonenumber.entity.CallLogBean;
import com.example.myphonenumber.entity.myPhone;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Numberuser_show extends Activity  implements View.OnClickListener {

    private TextView numbershow;
    private TextView usernameshow;
    private Long contactid =null;
    private myPhone mp ;
    private ListView callLogListView;
    private AsyncQueryHandler asynccall_contact_Query;
    private DialAdapter adapter;
    private List callLogs;

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
        setContentView(R.layout.numberuser_show);
        asynccall_contact_Query = new MyAsyncQueryHandler(getContentResolver());
        init();

        Log.v("number_show :", "oncreate");
    }
    //onResume方法和oncstart方法在它之后调用；在onpause之后 onstart调用
  public void onStart() {
      super.onStart();
      Log.v("number_show :", "onstart");
     if(adapter!=null){
//         Log.v("number_show :", "onstart");
         init();  //在更新列表前一定要记得更新数据，重新调用数据库查找
         adapter.notifyDataSetChanged();
     }

  }
    private void init() {
        ImageButton callbtn;
        ImageButton textbtn;
        ImageButton editbtn;
        ImageButton showim_lfbtn;
        ImageButton userheadimg;
        ImageButton addbackbutton;
        ImageButton deletebtn;
        callbtn = findViewById(R.id.usershow_callbtn);
        textbtn = findViewById(R.id.usershow_textbtn);
        editbtn = findViewById(R.id.usershow_editbtn);
        deletebtn = findViewById(R.id.usershow_deletebtn);
        showim_lfbtn = findViewById(R.id.usershowim_lf);
        userheadimg = (ImageButton) findViewById(R.id.usershow_headimg);
        addbackbutton = (ImageButton) findViewById(R.id.shownumberback_arr);
        callbtn.setOnClickListener(this);
        textbtn.setOnClickListener(this);
        editbtn.setOnClickListener(this);
        showim_lfbtn.setOnClickListener(this);
        userheadimg.setOnClickListener(this);
        addbackbutton.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        numbershow = findViewById(R.id.usershow_tvnum);
        usernameshow = findViewById(R.id.usershow_tvname);
        callLogListView = (ListView) findViewById(R.id.number_call_record);
        //通过intent获取用户的电话和姓名相关信息
        getIdFromMainActivity();
        // 查询的列
        Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
        asynccall_contact_Query.startQuery(0, null, uri, contact_projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

    }

    public void getIdFromMainActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //从mainActivity获取当前的用户名和电话号码
        contactid =  intent.getLongExtra("contactid",0);
        mp = findContacts(contactid);
        numbershow.setText(mp.getPhonenumber());
        usernameshow.setText(mp.getUsername());
    }

    //usershow页面添加监听事件
    @Override
    public void onClick(View v) {
        int id = v.getId();
       String username = mp.getUsername();
       String usernumber = mp.getPhonenumber();
       Long usercontactid = mp.getContactId();
        switch (id) {
            case R.id.usershow_callbtn:
            case R.id.usershowim_lf:
                //点击 List，获取 List 返回的 object-ContentValues
                //  String number = ContentValues.getString(usernumber);//用 intent 启动拨打电话
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + usernumber));
                startActivity(intent1);
                break;
            case R.id.usershow_textbtn:
                //跳转到编辑短信界面
                Intent intent2 = new Intent(Numberuser_show.this,message_edit.class);
                intent2.putExtra("usernumber",usernumber);
                intent2.putExtra("username",username);
                intent2.putExtra("contactid",usercontactid);
                startActivity(intent2);
                break;
            case R.id.usershow_editbtn:
                Log.d("usershow", "用户跳转编辑界面");//跳转到编辑短信界面
                Intent intent3 = new Intent(Numberuser_show.this, Edituser_number.class);
                intent3.putExtra("username", username);
                intent3.putExtra("usernumber", usernumber);
                intent3.putExtra("contactid",usercontactid);
                startActivity(intent3);
                break;
            case R.id.shownumberback_arr:
                startActivity(new Intent(Numberuser_show.this, MainActivity.class));
                break;
            case R.id.usershow_deletebtn:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("删除？")
                        .setMessage("确定删除当前联系人吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Log.v("Number_show_delete", "用户确定删除当前用户");
                                        deleteuser(username, usernumber);
                                        startActivity(new Intent(Numberuser_show.this, MainActivity.class));
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("Number_show_delete", "用户取消删除当前联系人");
                    }
                });
                dialog.show();
                break;
        }
    }


    private void deleteuser(String username, String usernumber) {
        ContentResolver resolver = this.getContentResolver();
        String selectionClause = Data.RAW_CONTACT_ID + " = ? ";
        String[] selectionArgs = {String.valueOf(contactid)};
        resolver.delete(Data.CONTENT_URI, selectionClause, selectionArgs);
        Log.v("Numberuser_show", contactid+"删除成功");
    }

    private myPhone findContacts(Long contactid) {
        String[] PHONES_PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.LABEL,
        };
        String PHONES_SELECTION = null;
        int PHONES_DISPLAY_NAME_INDEX = 0;
        int PHONES_NUMBER_INDEX = 1;
        int PHONES_PHOTO_ID_INDEX = 2;
        int PHONES_CONTACT_ID_INDEX = 3;
        ContentResolver resolver = this.getContentResolver();
        Cursor phoneCursor = resolver.query(CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
               Long  contid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
                if (contactid.equals(contid)) {
                    //得到联系人 ID //查找的关于该账号的其他信息
                    Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
                    //得到手机号码
                    String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                    //当手机号码为空的或者为空字段 跳过当前循环
                    if (TextUtils.isEmpty(phoneNumber))
                        continue;
                    //得到联系人名称
                    String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                   mp = new myPhone(contactid, phoneNumber,contactName,null,null,null,null,null);
                    Log.v("Numberuser",mp.toString() ) ;
                    Log.v("Numberuser_show" + contactName, "根据号码和姓名查找到的用户contactid为" + contactid + " photoid  " + photoid);
                    //得到联系人头像 Bitamp
//                    Bitmap contactPhoto = null;
//                    //photoid 大于 0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
//                    if (photoid > 0) {
//                        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
//                        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
//                        contactPhoto = BitmapFactory.decodeStream(input);
                }
            }
        }
        phoneCursor.close();
        return mp;
    }


    // 写一个异步查询类,用于查询通话记录
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }
        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                callLogs = new ArrayList();
                SimpleDateFormat sfd = new SimpleDateFormat("MM-dd hh:mm");
                Date date;
                String usernumber = mp.getPhonenumber();
                String usernamee = mp.getUsername();
                Log.v("numberusercursor_count ", "usernumber为"+usernumber +" " + String.valueOf(cursor.getCount()));
                while (cursor.moveToNext()) { // 游标移动到第一项 //存放数据到callLogs
                    String number = cursor.getString(1);
                    if(number.equals(usernumber)){
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
                        callLogs.add(callLogBean);
                    }
               }
//设置Adapter
                    if (callLogs.size() > 0) {
                        setAdapter(callLogs);
                    }
                }
            super.onQueryComplete(token, cookie, cursor);
            }

    }

    private void setAdapter(List callLogs) {
        adapter = new DialAdapter(this, callLogs);

        callLogListView.setAdapter(adapter);

    }
}

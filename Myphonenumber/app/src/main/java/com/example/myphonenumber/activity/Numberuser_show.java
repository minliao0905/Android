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
            CallLog.Calls.DATE, // ??????
            CallLog.Calls.NUMBER, // ??????
            CallLog.Calls.TYPE, // ??????
            CallLog.Calls.CACHED_NAME, // ??????
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
    //onResume?????????oncstart??????????????????????????????onpause?????? onstart??????
  public void onStart() {
      super.onStart();
      Log.v("number_show :", "onstart");
     if(adapter!=null){
//         Log.v("number_show :", "onstart");
         init();  //???????????????????????????????????????????????????????????????????????????
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
        //??????intent??????????????????????????????????????????
        getIdFromMainActivity();
        // ????????????
        Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
        asynccall_contact_Query.startQuery(0, null, uri, contact_projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

    }

    public void getIdFromMainActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //???mainActivity???????????????????????????????????????
        contactid =  intent.getLongExtra("contactid",0);
        mp = findContacts(contactid);
        numbershow.setText(mp.getPhonenumber());
        usernameshow.setText(mp.getUsername());
    }

    //usershow????????????????????????
    @Override
    public void onClick(View v) {
        int id = v.getId();
       String username = mp.getUsername();
       String usernumber = mp.getPhonenumber();
       Long usercontactid = mp.getContactId();
        switch (id) {
            case R.id.usershow_callbtn:
            case R.id.usershowim_lf:
                //?????? List????????? List ????????? object-ContentValues
                //  String number = ContentValues.getString(usernumber);//??? intent ??????????????????
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + usernumber));
                startActivity(intent1);
                break;
            case R.id.usershow_textbtn:
                //???????????????????????????
                Intent intent2 = new Intent(Numberuser_show.this,message_edit.class);
                intent2.putExtra("usernumber",usernumber);
                intent2.putExtra("username",username);
                intent2.putExtra("contactid",usercontactid);
                startActivity(intent2);
                break;
            case R.id.usershow_editbtn:
                Log.d("usershow", "????????????????????????");//???????????????????????????
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
                dialog.setTitle("?????????")
                        .setMessage("?????????????????????????????????")
                        .setPositiveButton("??????",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Log.v("Number_show_delete", "??????????????????????????????");
                                        deleteuser(username, usernumber);
                                        startActivity(new Intent(Numberuser_show.this, MainActivity.class));
                                    }
                                }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("Number_show_delete", "?????????????????????????????????");
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
        Log.v("Numberuser_show", contactid+"????????????");
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
                    //??????????????? ID //???????????????????????????????????????
                    Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
                    //??????????????????
                    String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                    //?????????????????????????????????????????? ??????????????????
                    if (TextUtils.isEmpty(phoneNumber))
                        continue;
                    //?????????????????????
                    String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                   mp = new myPhone(contactid, phoneNumber,contactName,null,null,null,null,null);
                    Log.v("Numberuser",mp.toString() ) ;
                    Log.v("Numberuser_show" + contactName, "???????????????????????????????????????contactid???" + contactid + " photoid  " + photoid);
                    //????????????????????? Bitamp
//                    Bitmap contactPhoto = null;
//                    //photoid ?????? 0 ???????????????????????? ?????????????????????????????????????????????????????????
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


    // ????????????????????????,????????????????????????
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
                Log.v("numberusercursor_count ", "usernumber???"+usernumber +" " + String.valueOf(cursor.getCount()));
                while (cursor.moveToNext()) { // ???????????????????????? //???????????????callLogs
                    String number = cursor.getString(1);
                    if(number.equals(usernumber)){
                        date = new Date(cursor.getLong(0));
                        int type = cursor.getInt(2);
                        String cachedName = cursor.getString(3);// ???????????????????????????????????????????????????
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
//??????Adapter
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

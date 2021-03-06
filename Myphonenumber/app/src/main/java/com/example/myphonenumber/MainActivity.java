package com.example.myphonenumber;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myphonenumber.activity.Callphone_allcontacts;
import com.example.myphonenumber.activity.Callphone_service;
import com.example.myphonenumber.activity.Newphone_user;
import com.example.myphonenumber.activity.Numberuser_show;
import com.example.myphonenumber.activity.message_show;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.myphonenumber.entity.myPhone;
import com.example.myphonenumber.permission.permissionUtil;
public class MainActivity extends Activity {
    private ListView phonelistView;
    public static MainActivity mainActivity = null;
    Button message_main;
    ImageButton callphone_btn;
    ImageButton callall_contactbtn;
    ImageButton AddnewBtn;
    public List<myPhone> myPhoneuserList = new ArrayList<myPhone>();
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.LABEL
    };
    /**
     * the contact name
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**
     * the phone number
     **/
    private static final int PHONES_NUMBER_INDEX = 1;
    /**
     * the phone ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    /**
     * the contact ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    /**
     * the contact name
     **/
    private ArrayList<String> mContactsName = new ArrayList<String>();
    /**
     * the contact number
     **/
    private ArrayList<String> mContactsNumber = new ArrayList<String>();
    /**
     * the contact photo
     **/
    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
    /**
     * the contact photo
     **/
    private ArrayList<Bitmap> mContactPhontoTemp = new ArrayList<Bitmap>();

    private myAdapter mylistadapter;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //????????????
        checkPermission();
        //???????????????????????????????????????????????????
        phonelistView = findViewById(R.id.phonumberlist);
        init();//???????????????
        getPhoneContacts();
        mylistadapter = new myAdapter();
        phonelistView.setAdapter(mylistadapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        init();//???????????????
        mylistadapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();//???????????????
        mylistadapter.notifyDataSetChanged();
    }

    //    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        message_main = findViewById(R.id.message_main);
        message_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "????????????????????????????????????");
                startActivity(new Intent(MainActivity.this, message_show.class));
            }
        });

        AddnewBtn = findViewById(R.id.addnewimbtn);
        AddnewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "???????????????????????????", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Newphone_user.class));
            }
        });

//        AddnewBtn.setTooltipText("???????????????");
        callphone_btn = findViewById(R.id.callphonebtn);
        callphone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "??????????????????????????????????????????????????????");
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +0));

                startActivity(new Intent(MainActivity.this, Callphone_service.class));
            }
        });
//        callphone_btn.setTooltipText("????????????");
        callall_contactbtn = findViewById(R.id.callallcontactimbtn);
        callall_contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "??????????????????????????????????????????????????????");
                startActivity(new Intent(MainActivity.this, Callphone_allcontacts.class));
            }
        });
//        callall_contactbtn.setTooltipText("????????????");
    }


    private void getPhoneContacts() {
        ContentResolver resolver = this.getContentResolver();
// ?????????????????????
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null,
                null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //??????????????????
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //?????????????????????????????????????????? ??????????????????
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                //?????????????????????
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                //??????????????? ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
                //????????????????????? ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                //????????????????????? Bitamp
                Bitmap contactPhoto = null;
                //photoid ?????? 0 ???????????????????????? ?????????????????????????????????????????????????????????
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    //??????????????????
                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.touxiang1);
                }
                myPhone mp = new myPhone(contactid, phoneNumber, contactName, null, null, null, null, null);
                myPhoneuserList.add(mp);
//                mContactsPhonto.add(contactPhoto);
                Log.d("??????", "getPhoneContacts: " + contactName + " phoneNumber " + phoneNumber + " photoid " + photoid);
            }
            phoneCursor.close();
            Log.d("??????", "???????????????????????????");
        }
    }

    public void reloaslist() {
        mylistadapter.notifyDataSetChanged();
    }


    /**
     * ????????????
     * //??????????????????
     * //??????????????????????????????????????????????????????
     */
    private int times = 0;
    //???????????????????????????
    private final int REQUEST_PHONE_PERMISSIONS = 0;

    //???????????????????????????
    private void checkPermission() {
        times++;
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //????????????????????????????????????????????????list;
            if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_CONTACTS);
            if ((checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_CONTACTS);
            if ((checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_CALL_LOG);
            if ((checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.SEND_SMS);
            if ((checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_SMS);
            if ((checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.CALL_PHONE);

            if (permissionsList.size() != 0) {
                if (times==1) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_PHONE_PERMISSIONS);
                } else {
                    new AlertDialog.Builder(this)
                            .setCancelable(true)
                            .setTitle("??????")
                            .setMessage("?????????????????????APP?????????????????????????????????????????????APP???????????????")
                            .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                                REQUEST_PHONE_PERMISSIONS);
                                    }
                                }
                            }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();//?????????????????????????????????????????????????????????????????????
                        }
                    }).show();
                }
            } else {
                //initData();//???????????????
            }
        } else {
            //initData();//???????????????
        }
    }

    //?????????????????????
    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkPermission();
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        long mExitTime = System.currentTimeMillis();
////        if(keyCode == KeyEvent.KEYCODE_BACK){           //want to do
////            Toast.makeText(MainActivity.this,"???????????????????????????????????????",Toast.LENGTH_SHORT).show();
////        }
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                    Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
//                    //System.currentTimeMillis()??????????????????
//                    mExitTime = System.currentTimeMillis();
//                } else {
//                    finish();
//                }
//                return true;
//            }
//            return super.onKeyDown(keyCode, event);
//        }



//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//          // ??????????????????
//            if(keyCode == KeyEvent.KEYCODE_BACK){           //want to do
//                Toast.makeText(MainActivity.this,"???????????????????????????????????????",Toast.LENGTH_SHORT).show();
//            }
//            return super.onKeyDown(keyCode, event);
//        }
//    }

    //    private final int REQUEST_CODE = 2;//?????????
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case REQUEST_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.i("request", "success");
//                    Toast.makeText(this,"????????????????????????",Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.i("request", "failed");
//                    Toast.makeText(this,"??????????????????????????????????????????????????????",Toast.LENGTH_SHORT).show();
//
//                }
//              return ;
//            }
//        }
/////?????????????????????????????????????????????????????????????????????????????????????????????
//    }
    /*
    ??????????????????
     */
    private class myAdapter extends BaseAdapter {
        int length = myPhoneuserList.size();

        @Override
        public int getCount() {
            return length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.phonenumberlist_item, null);
            TextView tv_phonenumber = view.findViewById(R.id.showpitem_number);
            TextView tv_phoneuser = view.findViewById(R.id.showpitem_username);

            tv_phonenumber.setText(myPhoneuserList.get(position).getPhonenumber());
            tv_phoneuser.setText(myPhoneuserList.get(position).getUsername());

            //?????????listitem???????????????????????????numbershow??????
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Numberuser_show.class);
                    Bundle bundle = new Bundle();
//                    bundle.putCharSequence("username", myPhoneuserList.get(position).getUsername());//????????????
//                    bundle.putCharSequence("usernumber", myPhoneuserList.get(position).getPhonenumber());//??????????????????
                    bundle.putLong("contactid", myPhoneuserList.get(position).getContactId());
//                    bundle.putInt("userheadimg", R.drawable.touxiang1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            //????????????
            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            getPhoneContacts();

            super.notifyDataSetChanged();
        }
    }
    /*
    ??????????????????????????????
     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public boolean onHover(View v, MotionEvent event) {
//        int eventid = event.getAction();
//        int imid = v.getId();
//        switch(eventid){
//            case MotionEvent.ACTION_HOVER_ENTER: //????????????view
//                break;
//            case MotionEvent.ACTION_HOVER_MOVE: //?????????view???
//                System.out.println("bottom ACTION_HOVER_MOVE");
//                break;
//            case MotionEvent.ACTION_HOVER_EXIT: //????????????view
//                System.out.println("bottom ACTION_HOVER_EXIT");
//                break;
//        }
//        return false;
//    }

}


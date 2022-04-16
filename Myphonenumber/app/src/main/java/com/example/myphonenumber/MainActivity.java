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
        //访问权限
        checkPermission();
        //添加按钮点击事件转换到信息编辑界面
        phonelistView = findViewById(R.id.phonumberlist);
        init();//初始化控件
        getPhoneContacts();
        mylistadapter = new myAdapter();
        phonelistView.setAdapter(mylistadapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        init();//初始化控件
        mylistadapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();//初始化控件
        mylistadapter.notifyDataSetChanged();
    }

    //    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        message_main = findViewById(R.id.message_main);
        message_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "从当前界面跳转到短信界面");
                startActivity(new Intent(MainActivity.this, message_show.class));
            }
        });

        AddnewBtn = findViewById(R.id.addnewimbtn);
        AddnewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "跳转到添加用户界面", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Newphone_user.class));
            }
        });

//        AddnewBtn.setTooltipText("添加新用户");
        callphone_btn = findViewById(R.id.callphonebtn);
        callphone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "从当前页面跳转到打电话页面，拨号服务");
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +0));

                startActivity(new Intent(MainActivity.this, Callphone_service.class));
            }
        });
//        callphone_btn.setTooltipText("拨号服务");
        callall_contactbtn = findViewById(R.id.callallcontactimbtn);
        callall_contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainActivity", "从当前页面跳转到所有通话记录查看服务");
                startActivity(new Intent(MainActivity.this, Callphone_allcontacts.class));
            }
        });
//        callall_contactbtn.setTooltipText("通话记录");
    }


    private void getPhoneContacts() {
        ContentResolver resolver = this.getContentResolver();
// 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null,
                null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                //得到联系人 ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
                //得到联系人头像 ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                //得到联系人头像 Bitamp
                Bitmap contactPhoto = null;
                //photoid 大于 0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    //设置默认头像
                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.touxiang1);
                }
                myPhone mp = new myPhone(contactid, phoneNumber, contactName, null, null, null, null, null);
                myPhoneuserList.add(mp);
//                mContactsPhonto.add(contactPhoto);
                Log.d("获取", "getPhoneContacts: " + contactName + " phoneNumber " + phoneNumber + " photoid " + photoid);
            }
            phoneCursor.close();
            Log.d("获取", "获取联系人列表完成");
        }
    }

    public void reloaslist() {
        mylistadapter.notifyDataSetChanged();
    }


    /**
     * 权限处理
     * //处理权限请求
     * //首先定义一个变量来记录处理权限了几次
     */
    private int times = 0;
    //在处理权限时的回调
    private final int REQUEST_PHONE_PERMISSIONS = 0;

    //检查全新的核心方法
    private void checkPermission() {
        times++;
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取权限，将需要获取的权限添加入list;
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
                            .setTitle("提示")
                            .setMessage("获取不到授权，APP将无法正常使用，请在设置中允许APP获取权限！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                                REQUEST_PHONE_PERMISSIONS);
                                    }
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();//这里对该界面直接进行销毁，让用户从新进入该界面
                        }
                    }).show();
                }
            } else {
                //initData();//初始化数据
            }
        } else {
            //initData();//初始化数据
        }
    }

    //权限处理的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkPermission();
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        long mExitTime = System.currentTimeMillis();
////        if(keyCode == KeyEvent.KEYCODE_BACK){           //want to do
////            Toast.makeText(MainActivity.this,"再点击返回一次，即退出程序",Toast.LENGTH_SHORT).show();
////        }
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                    //System.currentTimeMillis()系统当前时间
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
//          // 如果是返回键
//            if(keyCode == KeyEvent.KEYCODE_BACK){           //want to do
//                Toast.makeText(MainActivity.this,"再点击返回一次，即退出程序",Toast.LENGTH_SHORT).show();
//            }
//            return super.onKeyDown(keyCode, event);
//        }
//    }

    //    private final int REQUEST_CODE = 2;//请求码
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case REQUEST_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.i("request", "success");
//                    Toast.makeText(this,"成功获取应用权限",Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.i("request", "failed");
//                    Toast.makeText(this,"获取应用权限失败，应用将无法正常运行",Toast.LENGTH_SHORT).show();
//
//                }
//              return ;
//            }
//        }
/////该方法内不能设置循环，申请权限，不然会陷入循环，无法运行而死机
//    }
    /*
    自定义适配器
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

            //给每个listitem添加监听事件，转向numbershow界面
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Numberuser_show.class);
                    Bundle bundle = new Bundle();
//                    bundle.putCharSequence("username", myPhoneuserList.get(position).getUsername());//保存姓名
//                    bundle.putCharSequence("usernumber", myPhoneuserList.get(position).getPhonenumber());//保存手机号码
                    bundle.putLong("contactid", myPhoneuserList.get(position).getContactId());
//                    bundle.putInt("userheadimg", R.drawable.touxiang1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            //设置数据
            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            getPhoneContacts();

            super.notifyDataSetChanged();
        }
    }
    /*
    设置鼠标相关监听事件
     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public boolean onHover(View v, MotionEvent event) {
//        int eventid = event.getAction();
//        int imid = v.getId();
//        switch(eventid){
//            case MotionEvent.ACTION_HOVER_ENTER: //鼠标进入view
//                break;
//            case MotionEvent.ACTION_HOVER_MOVE: //鼠标在view上
//                System.out.println("bottom ACTION_HOVER_MOVE");
//                break;
//            case MotionEvent.ACTION_HOVER_EXIT: //鼠标离开view
//                System.out.println("bottom ACTION_HOVER_EXIT");
//                break;
//        }
//        return false;
//    }

}


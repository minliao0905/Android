package com.example.myphonenumber.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.*;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myphonenumber.MainActivity;
import com.example.myphonenumber.R;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Newphone_user extends Activity {
    private EditText username ;
    private EditText userphone;
    private EditText useraddress;
    private EditText userbirth;
    private EditText othertext;
    private EditText useremail;
    private Button addnewphonebtn;
    private ImageButton addnewbackbtn;
    private ImageButton userheadimg;
    private String path="android.resource:/com.example.myphonenumber/drawable/add";
    private int headimageId;
    String user_name;
    String user_phone;
    String user_address;
    String user_birth;
    String other_text;
    String user_email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newphone_user);
        init();
    }

    public void init(){
        username = findViewById(R.id.ed_name);
        useraddress = findViewById(R.id.ed_address);
        userphone = findViewById(R.id.ed_phone);
        userbirth = findViewById(R.id.ed_birth);
        othertext = findViewById(R.id.ed_othertext);
        useremail= findViewById(R.id.ed_email);
        addnewphonebtn = findViewById(R.id.addnewphonebtn);
        addnewbackbtn = findViewById(R.id.newnumberback_arr);
        userheadimg = findViewById(R.id.newuser_headimg);
        addnewphonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInsert();
            }
        });
        addnewbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Newphone_user.this, MainActivity.class));
            }
        });
        userheadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Newphone_user.this,headimgActivity.class);
                startActivityForResult(intent, 0x11);//??????intent?????????Activity
            }
        });

    }
    public void onRestart() {
        super.onRestart();
    }
    //?????????????????????
    public void testInsert(){
        ContentValues values = new ContentValues();
        //????????? RawContacts.CONTENT_URI ?????????????????????????????????????????????????????????  rawContactId
        Uri rawContactUri =
                this.getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        //??????????????????
        if(get_checkInformation()){
            //??? data ??????????????????
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
            values.put(StructuredName.GIVEN_NAME, user_name);
            this.getContentResolver().insert( Data.CONTENT_URI, values);
            //??? data ??????????????????
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
            values.put(Phone.NUMBER, user_phone);
            values.put(Phone.TYPE, Phone.TYPE_MOBILE);
            this.getContentResolver().insert( Data.CONTENT_URI, values);
            //??? data ?????? Email ??????
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
            values.put(Email.DATA, user_email);
            values.put(Email.TYPE, Email.TYPE_WORK);
            this.getContentResolver().insert( Data.CONTENT_URI, values);
            //??? data ??????????????? ????????????????????????????????? ??????
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Note.CONTENT_ITEM_TYPE);
            values.put(Note.DATA1,user_address);
            values.put(Note.DATA2,user_birth);
            values.put(Note.DATA3,other_text);
            this.getContentResolver().insert(Data.CONTENT_URI, values);
            //data?????????contact?????????
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE,Photo.CONTENT_ITEM_TYPE);
            values.put(Photo.PHOTO_ID, Long.valueOf(headimageId));
            this.getContentResolver().insert(Data.CONTENT_URI, values);
            Log.v("Newuserinsertpath",headimageId+"|  ???????????????????????????  "+path);

            Toast.makeText(this,"????????????????????????",Toast.LENGTH_SHORT).show();
             MainActivity.getMainActivity().reloaslist();
            clearEditext();
        }else{
            Toast.makeText(this,"????????????????????????",Toast.LENGTH_SHORT).show();
        }

    }

    public void headImageInsert(int imageId){
        Resources res = this.getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(imageId)
                + "/" + res.getResourceTypeName(imageId) + "/" + res.getResourceEntryName(imageId));

        File file = new File(uri.toString());
        path = file.getPath();
        Log.v("Newuser","???????????????????????????  "+path);
    }

    //??????????????????????????????
    public boolean get_checkInformation(){
       user_name = username.getText().toString();
       user_phone = userphone.getText().toString();
       user_address = useraddress.getText().toString();
       user_birth = userbirth.getText().toString();
       other_text = othertext.getText().toString();
       user_email = useremail.getText().toString();
        if(user_name.equals("")||user_address.equals("")||user_email.equals("")||user_birth.equals("")||user_phone.equals("")||other_text.equals("")){
            Toast.makeText(this,"???????????????????????????",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!checkEmail(user_email)){
            Toast.makeText(this,"???????????????????????????",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isMobileNO(user_phone)){
            Toast.makeText(this,"???????????????????????????",Toast.LENGTH_SHORT).show();
            return false;
        }
        //????????????
       return true;
    }
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            //LOG.error("????????????????????????", e);
            flag = false;
        }

        return flag;
    }
    /**
     * ??????????????????
     * @param mobiles
     * @return
     */
//    public static boolean isMobileNO(String mobiles){
//        boolean flag = false;
//        try{
//            Pattern p = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
//            Matcher m = p.matcher(mobiles);
//            flag = m.matches();
//        }catch(Exception e){
//            //LOG.error("????????????????????????", e);
//            flag = false;
//        }
//        return flag;
//    }
    public static boolean isMobileNO(String mobiles){
        boolean flag = false;
        try{
//            String regex = "[1][34578][0-9]{9}"; //??????????????????????????????????????????1?????????????????????3???4???5???7???8????????????????????????????????????0-9?????????????????????
            Pattern p = Pattern.compile("^(13[0-9]|14[123456789]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
//            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            //LOG.error("????????????????????????", e);
            flag = false;
        }
        return flag;
    }

    //????????????????????????
    public void clearEditext(){
        username.setText("");
        userphone.setText("");
        useraddress.setText("");
        userbirth.setText("");
        othertext.setText("");
        useremail.setText("");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x11 && resultCode==0x11){	//?????????????????????????????????
            Bundle bundle=data.getExtras();		//????????????????????????
            headimageId=bundle.getInt("imageId");	//?????????????????????ID
            userheadimg.setImageResource(headimageId);	//?????????????????????
            headImageInsert(headimageId);
        }
    }
}

package com.example.myphonenumber.activity;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.*;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myphonenumber.MainActivity;
import com.example.myphonenumber.R;
import com.example.myphonenumber.entity.myPhone;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Edituser_number extends Activity {
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
    private int imageid = R.drawable.touxiang1;
    private myPhone mp;
    Long headimgid;
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.LABEL
    };
    private  static String PHONES_SELECTION=null;
    /**the contact name **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**the phone number **/
    private static final int PHONES_NUMBER_INDEX = 1;
    /**the phone ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    /** the contact ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    private static final int PHONES_LABEL_INDEX=4;

    /**the contact message**/
    private Long contactid ;
    private AsyncQueryHandler asynccall_contact_Query;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newphone_user);
        init();
    }

    public void init(){
        //??????????????????
        TextView toptext = findViewById(R.id.tv_newusertop);
        toptext.setText("+ ???????????????");
        username = findViewById(R.id.ed_name);
        useraddress = findViewById(R.id.ed_address);
        userphone = findViewById(R.id.ed_phone);
        userbirth = findViewById(R.id.ed_birth);
        othertext = findViewById(R.id.ed_othertext);
        useremail= findViewById(R.id.ed_email);
        addnewphonebtn = findViewById(R.id.addnewphonebtn);
        addnewbackbtn = findViewById(R.id.newnumberback_arr);
        userheadimg = findViewById(R.id.newuser_headimg);

        Intent intent = getIntent();
        contactid = intent.getLongExtra("contactid",0);

//        username.setText(intent.getStringExtra("username"));
//        userphone.setText(intent.getStringExtra("usernumber"));

        get_allInformation(contactid);   //??????????????????????????????????????????
        addnewphonebtn.setText("????????????");
        addnewphonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInsert(contactid);
            }
        });
        addnewbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edituser_number.this, Numberuser_show.class);
                intent.putExtra("contactid",contactid);
                startActivity(intent);
            }
        });
        userheadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Edituser_number.this,headimgActivity.class);
                startActivityForResult(intent, 0x11);//??????intent?????????Activity
            }
        });

    }

    public void onRestart() {
        super.onRestart();
        init();
    }

    //?????????????????????
    public void testInsert(Long contactid){
        ContentValues values = new ContentValues();
        //??????????????????
        if(get_checkInformation()){
            int i =0;
            String[] mymimetype = {StructuredName.CONTENT_ITEM_TYPE,Phone.CONTENT_ITEM_TYPE,Email.CONTENT_ITEM_TYPE,Note.CONTENT_ITEM_TYPE,Note.CONTENT_ITEM_TYPE,Note.CONTENT_ITEM_TYPE,Photo.CONTENT_ITEM_TYPE};
            String[] myinsertdata = {String.valueOf(contactid),mp.getUsername(),mp.getPhonenumber(),mp.getUseremail(),mp.getUseraddress(),mp.getUserbirth(),mp.getUserothertext(),String.valueOf(imageid)};
            String[] mydataname = {StructuredName.GIVEN_NAME,Phone.NUMBER,Email.DATA,Note.DATA1,Note.DATA2,Note.DATA3,Photo.PHOTO_ID};
            for(i=0;i<mymimetype.length-1;i++){
               values.clear();
               values.put(Data.RAW_CONTACT_ID,myinsertdata[0]); //rawcontactId
               values.put(mydataname[i],myinsertdata[i+1]);     //????????????
               String  selectionClause = Data.RAW_CONTACT_ID +" = ? " +"AND " + Data.MIMETYPE +" = ? ";
               String[] selectionArgs = {myinsertdata[0],mymimetype[i]};
               Log.v("Edidusernumber",myinsertdata[i+1]);
               this.getContentResolver().update(Data.CONTENT_URI,values,selectionClause,selectionArgs);
//              Log.v("Editeruser","????????????"+is);
           }
           //????????????
            values.clear();
            values.put(Data.RAW_CONTACT_ID,myinsertdata[0]); //rawcontactId
            values.put(mydataname[i],myinsertdata[i+1]);     //????????????
            String  selectionClause = Contacts.NAME_RAW_CONTACT_ID +" = ? " ;
            String[] selectionArgs = {myinsertdata[0]};
            Log.v("Edidusernumber",myinsertdata[i+1]);
            this.getContentResolver().update(Contacts.CONTENT_URI,values,selectionClause,selectionArgs);
            //????????????
             Toast.makeText(this,"?????????????????????",Toast.LENGTH_SHORT).show();
//             MainActivity.getMainActivity().reloaslist();  //???????????????????????????????????????????????????????????????????????????
            //??????????????????????????????
            Intent intent = new Intent(Edituser_number.this, Numberuser_show.class);
            intent.putExtra("contactid",contactid);
            startActivity(intent);
            clearEditext();
        }else{
            Toast.makeText(this,"?????????????????????",Toast.LENGTH_SHORT).show();
        }

    }
    //?????????????????????????????????????????????
    public void get_allInformation(Long contactid){
        String[] USERALL_INFORMATION = new String[]{
                Data.CONTACT_ID,        //0
                Data.DATA1,  //1
                Data.DATA2,//2
                Data.DATA3//3
        };
//        int DATA_CONTACTID =  0;
//        int DATA_DATA1 = 1;
//        int DATA_DATA2 = 2;
//        int DATA_DATA3 = 3;
        List<String> userallinfm  =new ArrayList<String>();
     ContentResolver useresolver =  getContentResolver();
     Cursor usercursor = useresolver.query(Data.CONTENT_URI,USERALL_INFORMATION,null,null,Data._ID);
     if(usercursor!=null&& usercursor.getCount() > 0) {
         int ncount = 0;   //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????ncount????????????
         while(usercursor.moveToNext()){
             Long contid = usercursor.getLong(0);
             if(contid.equals(contactid)){
                 ncount++;
                 if(ncount<4){
                    String data = usercursor.getString(1);
                     if(data!=null){
                         userallinfm.add(data);
                         Log.v("userallInformation0",data);
                     }
                 }else {
                     for(int i=0;i<3;i++){
                         String data =  usercursor.getString(i+1);
                         if(data!=null){
                             userallinfm.add(data);
                             Log.v("userallInformation1",data);
                         }
                     }
                 }
             }
         }
     }
     else{
         Log.v("userallInformation","?????????????????????");
     }
     usercursor.close();
     // ?????????????????????????????????
        username.setText(userallinfm.get(0));
        userphone.setText(userallinfm.get(1));
        useremail.setText(userallinfm.get(2));
        useraddress.setText(userallinfm.get(3));
        userbirth.setText(userallinfm.get(4));
        othertext.setText(userallinfm.get(5));

    }

    //??????????????????????????????????????????
    public boolean get_checkInformation(){
        String user_name= username.getText().toString();
        String user_phone= userphone.getText().toString();
        String user_address= useraddress.getText().toString();
        String user_birth = userbirth.getText().toString();
        String other_text= othertext.getText().toString();
        String user_email= useremail.getText().toString();
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
        mp = new myPhone(contactid,user_phone,user_name,user_email,other_text,user_address,user_birth,null);
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
    public static boolean isMobileNO(String mobiles){
        boolean flag = false;
        try{
//            String regex = "[1][34578][0-9]{9}"; //??????????????????????????????????????????1?????????????????????3???4???5???7???8????????????????????????????????????0-9?????????????????????
            Pattern p = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
//            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            //LOG.error("????????????????????????", e);
            flag = false;
        }
        return flag;
    }
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


    //????????????????????????
    public void clearEditext(){
        username.setText("");
        userphone.setText("");
        useraddress.setText("");
        userbirth.setText("");
        othertext.setText("");
        useremail.setText("");

    }

    public void headImageInsert(int imageId){
        Resources res = this.getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(imageId)
                + "/" + res.getResourceTypeName(imageId) + "/" + res.getResourceEntryName(imageId));

        File file = new File(uri.toString());
        path = file.getPath();
        Log.v("editinsertpathuser","???????????????????????????  "+path);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x11 && resultCode==0x11){	//?????????????????????????????????
            Bundle bundle=data.getExtras();		//????????????????????????
            int  imageId=bundle.getInt("imageId");	//?????????????????????ID
            userheadimg.setImageResource(imageId);	//?????????????????????
            headImageInsert(imageId);    //???????????????path;
        }
    }
}

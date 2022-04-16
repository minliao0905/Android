package com.example.changeimage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import com.example.changeimage.ImgURi.headimgActivity;
import com.example.changeimage.MakeImg.ImageSplitter;
import com.example.changeimage.MakeImg.ImgGameView;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImgActivity extends AppCompatActivity {
    private int score = 0;
    private static TextView tvScore;

    public ImgGameView  Imgmview ;
    public static ImgActivity imgActivity = null;
    public String  realPathFromUri = null;

    boolean flag  = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainimg);
        init();
    }
    /**
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.v("ImgActivity","加载");
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        if(inflater!=null){
            inflater.inflate(R.menu.main, menu);
        }else{
            Log.v("ImgActivity","加载失败");
        }

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.choose_myself:
                checkPermission();
                getImage();
                break;
            case R.id.choose_moren:
                startActivityForResult(new Intent(ImgActivity.this, headimgActivity.class), 0x11);
                break;
            default:
                break;
        }
        return true;
    }
    public static ImgActivity getImgActivity() {
        return imgActivity;
    }
    public ImgActivity() {
        imgActivity = this;
    }

    public  static void setTvScore(int movecount){
        tvScore.setText( movecount +"次");
    }
    public int getScore(){
        return score;
    }
    public void init(){
        Imgmview = findViewById(R.id.imggameView);
        tvScore = (TextView) findViewById(R.id.tvScoreimg);
        ImageView allimgv = (findViewById(R.id.imgallview));

        ImageView  gifView2 =   findViewById(R.id.restartlookimg);
        ImageView gifView1 =  findViewById(R.id.restartbtnimg);

        Glide.with(this).load(R.drawable.s5).into(gifView1);
        Glide.with(this).load(R.drawable.s5).into(gifView2);

        gifView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imgmview.startGame();
            }
        });

        gifView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    allimgv.setImageBitmap(null);
                    flag = false;
                }else{
                    Bitmap bm  =   ImageSplitter.bm;
                    allimgv.setImageBitmap(bm);
                    flag = true;
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission(){
        List<String> mPermissionList = new ArrayList<String>();
         final int REQUEST_PICK_IMAGE = 11101;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                mPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                mPermissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(mPermissionList.size()!=0){
            requestPermissions(mPermissionList.toArray(new String[mPermissionList.size()]),100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    public static final int REQUEST_PICK_IMAGE = 11101;
    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
//                    REQUEST_PICK_IMAGE);
//        } else {
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/*");
//            startActivityForResult(intent, REQUEST_PICK_IMAGE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                  Log.v("asd", String.valueOf(data.getData()));
                    Uri uri = data.getData();
                        InputStream is = null;
                        try {
                            is = getContentResolver().openInputStream(uri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        Imgmview.setCarBitmap(bitmap);
                        Imgmview.startGame();
//                         realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }

        if(resultCode == 0x11){
            if(requestCode==0x11  ){
                Bundle bundle=data.getExtras();		//获取传递的数据包
                int  headimageId=bundle.getInt("imageId");	//获取选择的头像ID
                Bitmap bm = BitmapFactory.decodeResource(getResources(),headimageId);
                Imgmview.setCarBitmap(bm);
                Imgmview.startGame();
            }
        }

    }




}

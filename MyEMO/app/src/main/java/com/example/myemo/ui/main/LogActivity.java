package com.example.myemo.ui.main;

import android.app.Activity;
import android.app.ActivityOptions ;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myemo.Log.LogEditActivity;
import com.example.myemo.Log.view.styleService;
import com.example.myemo.R;
import com.example.myemo.Sql.DatabaseHelper;
import com.example.myemo.Sql.Style;
import com.example.myemo.Sql.Userlog;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LogActivity extends  Activity{
    private RecyclerView loglist ;
    private ImageButton addbtn;
    private  List<Userlog> userlogList ;
    private  List<Style> logstyle ;
    private   MyrecyclerAdapter myrecyclerAdapter  = new MyrecyclerAdapter();
    private DatabaseHelper  dbhelper;
    private int listclumn = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loglist);
        init();
        initDb();
        iniRecyclerView();
        setupWindowAnimations();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDb();
        myrecyclerAdapter.notifyDataSetChanged();
    }

    private void setupWindowAnimations() {

         Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setExitTransition(fade);
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setEnterTransition(slide);

    }
    public void init(){
        dbhelper = new DatabaseHelper(LogActivity.this,"myemodb123.db",null,1);

        Toolbar toolbar = findViewById(R.id.loglist_toolbar);
        toolbar.inflateMenu(R.menu.loglistmain);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_deleteAll:
//                        Log.v("Logli34vity", "??????????????????");
                        // ??????????????? ???????????????????????????

                        AlertDialog alertDialog = new AlertDialog.Builder(LogActivity.this)
                                .setTitle("???????????????????????????????????????")
                                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Delete_logALL();
                                    }
                                })
                                .setNegativeButton("??????", null)
                                .create();
                        alertDialog.show();
                        // ??????????????? ????????????????????? ???????????? ??????style ????????????
                        Button btnPos =  alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        Button btnNeg =  alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        btnPos.setTextColor(Color.BLUE);
                        btnNeg.setTextColor(Color.RED);
                        break;
                    case R.id.menu_change1:
                        if(listclumn == 1){
                            item.setTitle("????????????");
                            listclumn = 2;

                        }else if(listclumn == 2){
                            item.setTitle("????????????");
                            listclumn = 1;
                        }
                        iniRecyclerView();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        addbtn = findViewById(R.id.addlogbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this, LogEditActivity.class), ActivityOptions.makeSceneTransitionAnimation(LogActivity.this).toBundle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(inflater!=null){
            inflater.inflate(R.menu.loglistmain, menu);
        }else{
            Log.v("LoglistActivity","??????????????????");
        }
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_delete: Log.v("Logli34vity","??????????????????");
////                Delete_log();
//                break;
//            case R.id.menu_cancel:
////                Cancel_checked();
//                break;
//            default:
//                break;
//        }
//    return super.onOptionsItemSelected(item);
//    }
    public void Delete_logALL(){
        if(userlogList.size()!=0){
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            for(int i=0;i<userlogList.size();i++){
                int id = userlogList.get(i).getLogid();
                String sql = "delete from user_log where logid =  " + id ;
                db.execSQL(sql);
            }
            Log.v("Logli34vity","??????????????????");
            db.close();
        }else{
            Toast.makeText(LogActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
        }
        initDb();
        myrecyclerAdapter.notifyDataSetChanged();
    }
    public void Delete_log(int id){
            SQLiteDatabase db = dbhelper.getWritableDatabase();
             String sql = "delete from user_log where logid =  " +id ;
              db.execSQL(sql);
            db.close();
    }

    private void iniRecyclerView(){
        loglist= findViewById(R.id.loglist);
        loglist.setHasFixedSize(true);
        //??????????????????
        loglist.setLayoutManager(new StaggeredGridLayoutManager(listclumn,  StaggeredGridLayoutManager.VERTICAL));

        loglist.setAdapter(myrecyclerAdapter);

    }

    /*
    ???????????????
     */
    class MyrecyclerAdapter extends RecyclerView.Adapter<MyrecyclerAdapter.VH>{

        public  class VH extends RecyclerView.ViewHolder{
            public final TextView time;
            public final TextView text;
            public final LinearLayout ll;

            public VH(View v) {
                super(v);
               time = (TextView) v.findViewById(R.id.loglist_time);
               text = (TextView) v.findViewById(R.id.loglist_text);
               ll = (LinearLayout) v.findViewById(R.id.logall);
            }
        }
     /*
     ????????????
      */
        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            Log.v("1","1");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loglist_item,parent,false);
            return new VH(view);

        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
//            Log.v("2","2");
            holder.text.setText(Html.fromHtml(userlogList.get(position).getLogtext()));
            holder.time.setText(userlogList.get(position).getLogtime().toString());

            int styleid = userlogList.get(position).getStyleid();
            Style style = logstyle.get(styleid);
            if(style!=null){
                holder.ll.setBackgroundColor(Color.parseColor(style.getBackground()));
                holder.text.setTextColor(Color.parseColor(style.getTextcolor()));
                holder.time.setTextColor(Color.parseColor(style.getTextcolor()));
                holder.text.setTextSize(16);
            }
            int finalposition = position;
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LogActivity.this,LogEditActivity.class);
                    intent.putExtra("logid",userlogList.get(finalposition).getLogid());
                    startActivity(intent);
                }
            });
            holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getBaseContext(),v);
                    popupMenu.getMenuInflater().inflate(R.menu.logdemain,popupMenu.getMenu());

                    //???????????????????????????????????????
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                             Delete_log(userlogList.get(finalposition).getLogid());
//                             loglist.removeViewAt(holder.getAdapterPosition());
//                            myrecyclerAdapter.notifyItemRemoved(holder.getAdapterPosition());  //??????????????????
                                initDb();
                                myrecyclerAdapter.notifyDataSetChanged();
                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
                }

            });

        }

        @Override
        public int getItemCount() {
            return userlogList!=null? userlogList.size():0;
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
//???????????????
    public void initDb(){
        // ?????????????????????
        Log.v("logdbas","??????db");
        // ??????style info
        try{
            InputStream is = this.getResources().openRawResource(R.raw.styleinfo);
            logstyle = styleService.getInforFromXML(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatabaseHelper dbhelper = new DatabaseHelper(LogActivity.this,"myemodb123.db",null,1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query("user_log",new String[]{"logid","log","logtime","styleid"},null,null,null,null,"logtime desc");
        userlogList = new ArrayList<Userlog>();

        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
              Userlog userlog;
              int logid = cursor.getInt(0);
              String logtext = cursor.getString(1);
              String logtime=  cursor.getString(2);
              int styleid = cursor.getInt(3);
              userlog = new Userlog(logid,logtext,logtime,styleid);
              userlogList.add(userlog);
//              Log.v("userloglist",userlog.toString());
            }
        }else{
            Log.v("userloglist","????????????loglist?????????");
        }
        cursor.close();
        db.close();
    }

}

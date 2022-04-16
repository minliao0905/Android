package com.example.myemo.Log;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myemo.Log.view.styleService;
import com.example.myemo.R;
import com.example.myemo.Sql.DatabaseHelper;
import com.example.myemo.Sql.Style;
import com.example.myemo.Sql.Userlog;
import com.example.myemo.view.MyEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEditActivity extends Activity {

    private MyEditText logedt ;
    private Button logsavebtn;
    private TextView timetext;
    private ImageButton editbackbtn;
    private ImageButton changeedit;
    private DatabaseHelper dbhelper ;
    private List<Style> stylelist ;
    private Style logstyle ;
    private String time;
    private int logid=-1;
    private static final int PHOTO_SUCCESS = 1;
    private static final int CAMERA_SUCCESS = 2;
    private static  StringBuilder textSource;
    private int count =0 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loglist_edit);
        init();
    setupWindowAnimations();
    }

    private void setupWindowAnimations() {

        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

    }
    protected void init() {
        textSource = new StringBuilder();
        if(dbhelper == null){
            dbhelper =  new DatabaseHelper(LogEditActivity.this,"myemodb123.db",null,1);
        }
        logedt = findViewById(R.id.log_text);
        logsavebtn = findViewById(R.id.logsavebtn);
        editbackbtn = findViewById(R.id.edit_backbtn);
        changeedit = findViewById(R.id.changeedit);

        rl_root = findViewById(R.id.rl_root);

        // 在编辑界面获取并显示时间
        timetext = findViewById(R.id.edit_time);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;  // 需要加1
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        time = year + "-" + month + "-" + day + " " + hour + ":" + minute;
        timetext.setText(time);
        // 退回页面 显示所有日志界面
        editbackbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
        logsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initSave()) {
                    // 保存后退出当前编辑界面 返回日志界面
                    onBackPressed();
                }
            }
        });
        changeedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
                hideInput();
            }
        });
//        为编辑器添加监听 实现 部分文字加粗
        logedt.addTextChangedListener(new editTextChangedListener());
        // 获取文字风格样式的xml 数据
        try {
            InputStream is = this.getResources().openRawResource(R.raw.styleinfo);
            stylelist = styleService.getInforFromXML(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 从loglist界面跳转 获取logid 并查找数据
        Intent intent = getIntent();
        logid = intent.getIntExtra("logid", -1);
        if (logid != -1) {
            Userlog userlog = FindlogByid(logid);
            if(textSource!=null){
                textSource.append(userlog.getLogtext());
            }
            logstyle = stylelist.get(userlog.getStyleid());
            logedt.setText(Html.fromHtml(userlog.getLogtext()));
            logedt.setSelection(Html.fromHtml(userlog.getLogtext()).length());
            logedt.setBackgroundColor(Color.parseColor(logstyle.getBackground()));
            logedt.setTextColor(Color.parseColor(logstyle.getTextcolor()));

        }
        logedt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||event.getAction() == KeyEvent.ACTION_DOWN ) {
                   textSource.append("<br />");
                }
                return false;
            }
        });
        //给软键盘添加监听事件，监听软键盘的高度，添加布局，在键盘上方添加视图
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
    }
///////////////////////////////////关于输入的监听事件/////////////////////////////////////////


    class editTextChangedListener implements TextWatcher{

        //定义当前输入的字符数
        private int CharCount = 0;
        private int selectPosition=0;
        private StringBuilder tag = new StringBuilder("</b></font>");
        //s:变化后的所有字符
        public void afterTextChanged(Editable s) {
            //将光标点，移动到最后一个字
            if(textSource.toString().endsWith("<font><b></b></font>")){
                //删除多余部分
                textSource.delete(textSource.lastIndexOf("<font><b></b></font>"),textSource.length());
                logedt.setSelection(s.length());
            }
        }

        // s字符串为："+s+"开始处："+start+"，替换体的长度："+count+"后替换体长度"+after
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {

        }
        //"s字符串为："+s+"，开始处："+start+"，替换体长度："+count+"，前替换体长度"+before
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //判断当前输入的字符数，与文本框内的字符数长度是否一样，如果一样，则不进行操作
            //主要用来跳出循环，当改变文字时，onTextChanged就认为有所变化，会进入死循环，所以采用这种方式结束循环
            if(CharCount!=logedt.length())
            {
                CharCount = logedt.length();
                String s1 = s.toString().substring(start,s.length());
                //将当前字符串的长度给输入字符串变量 // 因为重新定义setText 鼠标定义的位置会改变为 0  所以需要重新定义
                //定义SpannableString，它主要的用途就是可以改变editText,TextView中部分文字的格式，以及向其中插入图片等功能
                SpannableString ss = new SpannableString(s);
                Log.v("textde",s1+" " + start +" " + s.length());

                    // 删除后退操作 当删除时start== s.length;

                    if(start==s.length()){
                        //三重匹配判断 实现textSure的删除操作

                        String stag1 = tag.toString();
                        String stag2 = "<font><b>"+tag.toString();
                        String stag3 = "</b></font><font><b>"+tag.toString();
                        int end = textSource.toString().length();
                        if(textSource.toString().endsWith("<br/>")){
                            end = end-"<br />".length();
                            textSource.delete(textSource.lastIndexOf("<br/>"),textSource.length());
                        }
                        if(textSource.toString().endsWith(stag3)){
                            end = end-stag3.length();
                        }else if(textSource.toString().endsWith(stag2)){
                            end = end-stag2.length();
                            //删除多余部分
                            textSource.delete(textSource.lastIndexOf("<font><b></b></font>"),textSource.length());
                        }else if(textSource.toString().endsWith(stag1)){
                            end = end-stag1.length();
                        }

                        if(end>0){
                            textSource.delete(end-1,end);
                        }
                        Log.v("delete textsource" ,textSource.toString());
                    }
                    else{
                        if(boldfg)
                        {
                            ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            int index = textSource.lastIndexOf("</b></font>");
                            textSource.insert(index,s1);
                            Log.v("textsource",textSource.toString());
                            logedt.setText(ss);
                            logedt.setSelection(s.length());
                        }else  if(!(start==0&&s.length()!=0)){
                            Log.v("textsource",textSource.toString());
                                textSource.append(s1);
                        } else if(textSource.length()==0){
                            textSource.append(s1);
                        }
                }
            }

        }


    }

    // 设置监听器，显示软键盘以上的视图
    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                decorView.getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;

                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        showAViewOverKeyBoard(diff);
                    }
                }  else{
                        showAViewOverKeyBoard(diff);
                }
//                Log.d("Keyboard Size", "Size: " + diff );
            }
        };
    }
/*
*
*
* 在软键盘上添加视图，实现编辑文本的功能
*
 */
    private View overkeyView = null;
    private RelativeLayout rl_root = null ;
    private  boolean boldfg = false;
    private void showAViewOverKeyBoard(int heightDifference) {
        if (heightDifference > 0) {//显示
            if (overkeyView == null) {//第一次显示的时候创建  只创建一次
                overkeyView = View.inflate(this, R.layout.overkeybord, null);
                RelativeLayout.LayoutParams loginlayoutParams = new RelativeLayout.LayoutParams(-1, -2);
                loginlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                loginlayoutParams.bottomMargin = heightDifference;
//                \\ 这里在主界面将edittext 控件设置为scrollView 包含的布局之后 页面所获取的内容在键盘之上，所以这里不需要添加额外的高度，相反要避免overkeyview 的布局 遮盖编辑框

 /*…………****************** 在软键盘上添加视图，实现编辑文本的功能******************                 */
                // 1.点击实现文本加粗按钮
               ImageButton  boldbtn = overkeyView.findViewById(R.id.boldbtn);
                boldbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 设置动态textStyle的方法，但只能单一的显示该风格，不能灵活改变
//                        logedt.setTypeface(null, Typeface.BOLD);
//                        logedt.invalidate();
//                        String textsource = "修改TextView中部分文字的<font color='#ff0000'><big>大</big><small>小</small></font>和<font color='#00ff00'>颜色</font>，展示多彩效果！";
                       if(boldfg == false){
                           boldbtn.setBackgroundResource(R.mipmap.bold2);
                           textSource.append("<font><b></b></font>");
                           boldfg = true;  //已经加粗
                           count++;
                       }else{
                           boldbtn.setBackgroundResource(R.mipmap.bold1);
                           boldfg = false;

                       }
                    }

                });
                // 2.点击添加#字符号
                ImageButton hashbtn = overkeyView.findViewById(R.id.hashbtn);
                hashbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = logedt.getSelectionStart();
                        Editable edit_text = logedt.getEditableText();
                        edit_text.append("#");
                    }
                });
//
                rl_root.addView(overkeyView,loginlayoutParams );
            }
            overkeyView.setVisibility(View.VISIBLE);
        } else {//隐藏
            if (overkeyView != null) {
                overkeyView.setVisibility(View.GONE);
            }
        }
    }
    /*…………****************** 在软键盘上添加视图，实现编辑文本的功能******************
     */

    /////////////////////////////////////关于输入的设置监听事件///////////////////////////////////////
    public Userlog FindlogByid(int log_id){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query("user_log",new String[]{"logid","log","logtime","styleid"},"logid = " +log_id,null,null,null,"logtime desc");
        Userlog userlog = null;
        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                int logid = cursor.getInt(0);
                String logtext = cursor.getString(1);
                String logtime=  cursor.getString(2);
                int styleid = cursor.getInt(3);
                userlog = new Userlog(logid,logtext,logtime,styleid);

//              Log.v("userloglist",userlog.toString());
            }
        }else{
            Log.v("userlog ","当前查找log 为空！");
        }
        cursor.close();
        db.close();
        return userlog;
    }
    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    public boolean initSave(){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SpannableString spannableString = new SpannableString(logedt.getText());
//        Editable logtext = logedt.getText();   // Editable是个接口，而且内容是可以改变的，但string类型的content是不能改变的。
//        logtext = logedt.getText().toString();
        ContentValues values = new ContentValues();
        values.put("logtime",time);
        values.put("log", textSource.toString());
        values.put("styleid",logstyle!=null? logstyle.getStyleid():0);
        if(logid!=-1){
           String whereclause = "logid = " + logid;
           db.update("user_log",values,whereclause,null);
        }else{
            db.insert("user_log",null,values);
        }
        db.close();
        return true;
    }
    public boolean initstyle(){
        return true;
    }

    @Override
    public void onBackPressed() {

//        initSave();  //当退出时自动保存
         super.onBackPressed();
        this.finish();
    }
// 当点击按钮时 弹出窗口，用户可选择样式
    public void showPopupWindow() {
        View contentView = LayoutInflater.from(LogEditActivity.this).inflate(R.layout.loglist_card, null);
        PopupWindow mPopWindow = new PopupWindow(contentView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);

        //显示PopupWindow
        View rootview = LayoutInflater.from(LogEditActivity.this).inflate(R.layout.loglist_edit, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        RecyclerView recyclerview = contentView.findViewById(R.id.logchoose);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(4,  StaggeredGridLayoutManager.VERTICAL));
        MyCardAdtapter mycardAdtapter = new MyCardAdtapter();
        recyclerview.setAdapter(mycardAdtapter);

    }

   class MyCardAdtapter  extends RecyclerView.Adapter<MyCardAdtapter.VH>{

        @NonNull
        @Override
        public  VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loglist_item,parent,false);
            View view = new Button(parent.getContext());
            return new  VH(view);
        }
        @Override
        public void onBindViewHolder(@NonNull  VH holder, int position) {
            holder.btn.setText("(●'◡'●)");
            holder.btn.setTextColor(Color.parseColor(stylelist.get( position).getTextcolor()));
            holder.btn.setBackgroundColor(Color.parseColor(stylelist.get( position).getBackground()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btn.setText("(●'◡'●)");
                    int finalposition = holder.getAdapterPosition();
                    Style finalStyle = stylelist.get(finalposition);
                    logstyle = finalStyle;
                    logedt.setBackgroundColor(Color.parseColor(finalStyle.getBackground()));
                    logedt.setTextColor(Color.parseColor(finalStyle.getTextcolor()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return stylelist.size();
        }

        public  class VH extends RecyclerView.ViewHolder{
            public final Button btn;
            public VH(View v) {
                super(v);
                btn = (Button) v;
            }
        }
    }


}

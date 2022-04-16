package com.example.myemo.ui.Fragment.Saolei;

import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myemo.ui.Fragment.Saolei.ui.service.Modelinfo;
import com.example.myemo.ui.Fragment.Saolei.ui.view.Card;
import com.example.myemo.ui.Fragment.Saolei.ui.view.Myprogressbar;
import com.example.myemo.R;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends Activity {
    private  GridView mgameview;
    //    private static ProgressBar pbar;
    private static Myprogressbar mpbar;
    myAdapter myadapter = new myAdapter();
    private static  int ROW = 20;
    private static  int COL = 10;
    private static  int LEICODE = -1;
    private static  int LEICOUNT = 1;
    private static  int textsize = 16;
    private  int[][] cardsNum = null;
    private  int[][] vflag = null;
    private static int VOPEN_count=0;
    private  LoadingDialog loadingDialog = new LoadingDialog();
    private boolean isover = false;   //标志为结束
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();   //设置数据布局

        setContentView(R.layout.saol_main);
        mpbar = (Myprogressbar) findViewById(R.id.progress_bar);
        mpbar.setMax(ROW*COL);
//        pbar = (Myprogressbar) findViewById(R.id.progress_bar);
//        pbar.setMax(ROW*COL);
        handler.sendEmptyMessage(1001);
        init();
        Button btn = findViewById(R.id.restartbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }
    //
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1001) {
                mpbar.setmBarColor(randomColor());
                mpbar.setProgress(VOPEN_count);  //已经开了的个数，为
                if (VOPEN_count > ROW*COL-LEICOUNT) {
                    handler.removeMessages(1001);
//                    Log.v("Mainavtivity1",VOPEN_count+"");
                }
                if (VOPEN_count < ROW*COL-LEICOUNT) {
//                    Log.v("Mainavtivity2",VOPEN_count+"");
                    handler.sendEmptyMessageDelayed(1001, 1000);
                }
            }
        }
    };


    public  int randomColor(){
        Random random = new Random();   //如果值太大，会偏白，太小则会偏黑，所以需要对颜色的值进行范围限定
        int red = random.nextInt(150)+50;//50-199
        int green = random.nextInt(150)+50;//50-199
        int blue = random.nextInt(150)+50;//50-199
        return Color.rgb(red, green, blue);//根据rgb混合生成一种新的颜色
    }

    public void initData(){
        Modelinfo modelinfo = StartActivity.getModelinfo();
        ROW = modelinfo.getROW();
        COL = modelinfo.getCOL();
        LEICOUNT = modelinfo.getLeicount();
        textsize = modelinfo.getTextsize();
        VOPEN_count=0;
        //数组需要重新申请内存
        cardsNum = new int[ROW][COL];
        vflag = new int[ROW][COL];
    }
    public  void init(){
        setLei();
//        pbar = findViewById(R.id.progress_bar);
        mgameview = findViewById(R.id.gamelout);
        mgameview.setNumColumns(COL);
        mgameview.setColumnWidth(GetCardWidth());
        mgameview.setBackgroundColor(Color.parseColor("#FFFAFA")); // 配置GameView的背景或颜色
        mgameview.setAdapter(myadapter);
        mgameview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position/COL;
                int y = position%COL;

                openCell(x,y,0);
                vflag[x][y] = 1;
                myadapter.notifyDataSetChanged();
                //表示踩雷
                if(cardsNum[x][y] == LEICODE||isover){
                    isover = true;    //设置为已经结束
                    loadingDialog.LDialog(MainActivity.mpbar.getContext(), 0);
                }
                //检查是否完成游戏。
                if(checkComplement()){
                    loadingDialog.LDialog(MainActivity.mpbar.getContext(), 1);
                    isover = true;
                }
                VOPEN_count ++;
                mpbar.setProgress(VOPEN_count);


//                Log.v("mainactivity",(VOPEN_count*100)/(ROW*COL)+"");
//                Toast.makeText(MainActivity.this, "当前点击position x:" + x +"y:" + y, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void startGame(){
        VOPEN_count = 0;
//        pbar.setProgress(VOPEN_count);
        mpbar.setProgress(VOPEN_count);
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                cardsNum[i][j] = 0;
                vflag[i][j] = 0;
            }
        }
        setLei();
        isover = false;
        myadapter.notifyDataSetChanged();

    }

    public boolean checkComplement(){
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                if(vflag[i][j]==0&&cardsNum[i][j]!=-1)
                    return false;
            }
        }
        return true;
    }
    private int GetCardWidth()
    {
        //屏幕信息的对象
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        //获取屏幕信息
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;
        //一行有四个卡片，每个卡片占屏幕的四分之一
        return cardWidth/(COL-1);
    }
    private void setLei(){
        Random rand = new Random();
        for(int i=0;i<LEICOUNT;i++){
            int r = rand.nextInt(ROW);
            int c= rand.nextInt(COL);
            if(cardsNum[r][c]!=LEICODE){
                cardsNum[r][c] = LEICODE;
            }else{
                i--;   //为了避免有重复，导致雷的个数不足leicount
            }
        }

        //计算周边雷的数量
        for(int i=0;i<ROW;i++) {
            for(int j=0;j<COL;j++) {
                if(cardsNum[i][j]!=LEICODE) {
                    int  c=0;
                    if(i>0&&j>0&&cardsNum[i-1][j-1]==LEICODE) c++;
                    if(i>0&&cardsNum[i-1][j]==LEICODE) c++;
                    if(i>0&&j<COL-1&&cardsNum[i-1][j+1]==LEICODE) c++;
                    if(j>0&&cardsNum[i][j-1]==LEICODE) c++;
                    if(j<COL-1&&cardsNum[i][j+1]==LEICODE) c++;
                    if(i<ROW-1&&j>0&&cardsNum[i+1][j-1]==LEICODE) c++;
                    if(i<ROW-1&&cardsNum[i+1][j]==LEICODE) c++;
                    if(i<ROW-1&&j<COL-1&&cardsNum[i+1][j+1]==LEICODE) c++;
                    cardsNum[i][j]=c;
                }
            }
        }
    }

    //空白级联打开
    private void openCell(int i,int j,int Blankcount ){

        if(vflag[i][j] == 1) return ;   //为1表示已经开了，直接退回
        if(Blankcount==5)   //部分打开，设置数字限制 两次级联打开
            return;

        Blankcount++;
        //若为0 则打开 ，查找空白，num 为 0 的card
        if(cardsNum[i][j] == 0) {
            vflag[i][j] = 1;   //设置权限为打开
            VOPEN_count ++ ;
            if(i>0&&j>0&&vflag[i-1][j-1]==0) openCell(i-1,j-1,Blankcount);
            if(i>0&&vflag[i-1][j]==0) openCell(i-1,j,Blankcount);
            if(i>0&&j<COL-1&&vflag[i-1][j+1]==0) openCell(i-1,j+1,Blankcount);
            if(j>0&&vflag[i][j-1]==0) openCell(i,j-1,Blankcount);
            if(j<COL-1&&vflag[i][j+1]==0) openCell(i,j+1,Blankcount);
            if(i<COL-1&&j>0&&vflag[i+1][j-1]==0) openCell(i+1,j-1,Blankcount);
            if(i<ROW-1&&vflag[i+1][j]==0) openCell(i+1,j,Blankcount);
            if(i<ROW-1&&j<COL-1&&vflag[i+1][j+1]==0) openCell(i+1,j+1,Blankcount);
        }
    }
    public class myAdapter extends BaseAdapter{
        private LayoutInflater layoutInflater;
        @Override
        public int getCount() {
            return ROW*COL;
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
            ViewHolder holder = null;
            int x = position/COL; //行
            int y = position%COL; //列
            //当第一次创建时为空，第二重用时直接调用。
            if(convertView == null){
                layoutInflater = LayoutInflater.from(MainActivity.this);
                convertView = layoutInflater.inflate(R.layout.saol_carditem,null);
                holder = new ViewHolder();

                holder.card = convertView.findViewById(R.id.cardid);
                holder.card.setTextsize(textsize);
                if(vflag[x][y] == 1){     //为1则表示已开
                    holder.card.setNum(cardsNum[x][y]);
                }else if(vflag[x][y] == 0){
                    holder.card.setNum(-2);
                }
//              holder.card.setNum(cardsNum[x][y]);
//              holder.btn.setText(cardsNum[x][y]+"");
//              holder.btn.setBackgroundColor(0x33ffffff);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
                holder.card.setTextsize(textsize);   //设置卡片字体大小
                if(vflag[x][y] == 1){     //为1则表示已开
                    holder.card.setNum(cardsNum[x][y]);
                }else if(vflag[x][y] == 0){
                    holder.card.setNum(-2);
                }
            }
            return convertView;
        }
        class ViewHolder{
            Card card;
        }
    }
    private class LoadingDialog {
        public   void LDialog(Context context, int type) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            View contenView = LayoutInflater.from(context).inflate(R.layout.saolfall_dialog_show, null);
            builder.setView(contenView);
//                    创建显示dialog 自定义界面显示
            final AlertDialog dialog = builder.create();
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            ImageView gifView1 = contenView.findViewById(R.id.im1);
            TextView textView = contenView.findViewById(R.id.s_tv);
            switch (type){
                case 0:
                    gifView1.setImageResource(R.drawable.saolei_df);
                    textView.setText("你爆雷了！");
                    break;
                case 1:
                    gifView1.setImageResource(R.drawable.saolei_dp);
                    textView.setText("游戏完成！");
                    break;
                default:
                    break;
            }
            Button restartbtn = contenView.findViewById(R.id.saolei_restart);
            restartbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startGame();
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager m = dialogWindow.getWindowManager();
            dialog.show();

            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            p.height = (int) (d.getHeight() * 0.35); // 高度设置为屏幕的0.35
            p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.60
            dialog.getWindow().setAttributes(p);//设置大小

            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    boolean click = false;
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dialog.dismiss();

                        click = true;
                    }
                    return click;
                }
            });

        }

    }

}

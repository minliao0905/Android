package com.example.saolei;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.saolei.ui.service.Modelinfo;
import com.example.saolei.ui.view.Card;

import java.util.Random;

public class MainActivity extends Activity {
    private GridView mgameview;
    private ProgressBar pbar;
    myAdapter myadapter = new myAdapter();
    int ROW = 20;
    int COL = 10;
    int LEICODE = -1;
    int LEICOUNT = 1;
    int textsize = 16;
    int[][] cardsNum = null;
    int[][] vflag = null;
    int VOPEN_count=0;

    private boolean isover = false;   //标志为结束
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();   //设置数据布局
        setContentView(R.layout.activity_main);
        init();
        Button btn = findViewById(R.id.restartbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }
    public void initData(){
        Modelinfo modelinfo = StartActivity.getModelinfo();
        ROW = modelinfo.getROW();
        COL = modelinfo.getCOL();
        LEICOUNT = modelinfo.getLeicount();
        textsize = modelinfo.getTextsize();
        //数组需要重新申请内存
        cardsNum = new int[ROW][COL];
        vflag = new int[ROW][COL];
    }
    public  void init(){
        setLei();
        pbar = findViewById(R.id.progress_bar);
        mgameview = findViewById(R.id.gamelout);
        mgameview.setNumColumns(COL);
        mgameview.setColumnWidth(GetCardWidth());
        mgameview.setBackgroundColor(Color.parseColor("#A3D6CC")); // 配置GameView的背景或颜色
        mgameview.setAdapter(myadapter);
        mgameview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position/COL;
                int y = position%COL;
              //表示踩雷
                if(cardsNum[x][y] == LEICODE||isover){
//                    Toast.makeText(MainActivity.this, "抱歉你踩雷了", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("你好")
                            .setMessage("哎呀！ 你爆雷了！！！游戏结束")
                            .setPositiveButton("重来",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            startGame();
                                        }
                                    });
                    dialog.setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   MainActivity.this.finish();
                 }
                 });
              dialog.show();
              isover = true;    //设置为已经结束
                }
                    openCell(x,y,0);
                    vflag[x][y] = 1;
                    VOPEN_count++;

                myadapter.notifyDataSetChanged();
                //检查是否完成游戏。
                if(checkComplement()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("你好")
                            .setMessage("恭喜你通过游戏！！！，游戏结束")
                            .setPositiveButton("重来",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            startGame();
                                        }
                                    });
                    dialog.setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    });
                    dialog.show();
                }
                VOPEN_count ++;
                pbar.setProgress((VOPEN_count*1000)/(ROW*COL));
//                Log.v("mainactivity",(VOPEN_count*100)/(ROW*COL)+"");
//                Toast.makeText(MainActivity.this, "当前点击position x:" + x +"y:" + y, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void startGame(){
        VOPEN_count = 0;
        pbar.setProgress(VOPEN_count*1000/(ROW*COL));
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
              convertView = layoutInflater.inflate(R.layout.activity_carditem,null);
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

}
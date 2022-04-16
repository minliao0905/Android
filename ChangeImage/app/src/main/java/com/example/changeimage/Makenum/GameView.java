package com.example.changeimage.Makenum;
/*
问题一：使用random创建随机数随机摆放数字，可是random随机数的重复率太高了，导致加载失败，显示白屏
 int num = random.nextInt(15);
                while(cardFlag[num]!=0){
                    num = random.nextInt(15);
                    Log.v("random",num+"");
                }

问题二：绑定定位，实现滑动板块功能

 */


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.Random;

import com.example.changeimage.Dialog.LoadingDialog;
import com.example.changeimage.MainActivity;
import com.example.changeimage.StartActivity;

public class GameView extends GridLayout {
    private int   Row = StartActivity.num[0];
    private int  Col = StartActivity.num[1];
    private Card[][] cardsMap  = new Card[Row][Col]; // 用一个二维数组来记录下方阵
    private int[] cardFlag = null;  //0是为空，不然为数字。
    private int[]  EmptyCard = new int[2];
    private int movecount=0;
    // 为了能让这个GameView从xml文件中能够访问到，要添加构造方法（能传入相关属性的构造方法），为了保险起见，最好把它的三个构造方法都添加上
    public GameView(Context context, AttributeSet attrs, int defStyle) { // 构造函数
        super(context, attrs, defStyle);
        initGameView(); // 初始化
    }

    public GameView(Context context) { // 构造函数
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) { // 构造函数
        super(context, attrs);
        initGameView();
    }
    private void initGameView() { // 初始化

        setColumnCount(Row); // 指明GridLayout布局是4列的
        setRowCount(Col);
        setBackgroundColor(Color.parseColor("#A3D6CC")); // 配置GameView的背景或颜色
        setCards(GetCardWidth(),GetCardWidth());

        setOnTouchListener(new View.OnTouchListener() { // 设置交互方式
            // 监听上下左右滑动的这几个动作，再由这几个动作去执行特定的代码，去实现游戏的逻辑
            private float startX, startY, offsetX, offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
//                        System.out.println(startX+":"+startY+" "+ getposition(startX,startY)[0]+" "+getposition(startX,startY)[1]);
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) { // 加此判断是为了解决当用户向斜方向滑动时程序应如何判断的问题

                            if (offsetX < -5) {
                                SwiperLeft(); movecount++;
//                                 System.out.println("left");
                            } else if (offsetX > 5) {
                                SwiperRight();   movecount++;
//                                 System.out.println("right");
                            }

                        } else { // 判断向上向下

                            if (offsetY < -5) {
                                SwiperUp();   movecount++;
//                                 System.out.println("up");
                            } else if (offsetY > 5) {
                                SwiperDown();   movecount++;
//                                 System.out.println("down");
                            }
                        }
                        MainActivity.setTvScore(movecount);
                        checkComplete();  //检查是否完成游戏
                        break;
                }

                return true; // 此处必须返回true,如返回false，则只会监听到MotionEvent.ACTION_DOWN这个事件，返回此事件没有成功，所以后面的事件也不会发生
            }
        });

    }
    // 只有第一次创建的时候才会执行一次 只可能会执行一次
    // 手机横放的时候不会执行，因为布局宽高不会发生改变，在AndroidManifest文件中配置了横放手机布局不变的参数
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { // 动态计算卡片宽高
        super.onSizeChanged(w, h, oldw, oldh);
//        startGame();
        // 因为第一次创建游戏时，onSizeChanged()会被调用，且仅被调用一次，所以在这里开启游戏很合适
//        int cardWidth = (Math.min(w, h) - 10) / 4;
//        int cardHeight = cardWidth;
    }

    public void startGame() { // 开启游戏 若重新开始游戏的话就要先清理，清理完成后添加随机数
        cardFlag = new int[Row*Col];
        movecount = 0;
        MainActivity.setTvScore(movecount);
        setCardsnums();
//        set22();
        for (int x = 0; x < Row; x++) { // 对所有值进行清理
            for (int y = 0; y < Col; y++) {
                {
                    int num = cardFlag[x*Col+y];
                        if(num==0){
                            EmptyCard[0] = x;
                            EmptyCard[1] = y;
                            cardsMap[x][y].setNum(0);
                        }else{
                            cardsMap[x][y].setNum(num);
                        }
                }
            }
        }
    }
    private void setCardsnums(){
        cardFlag = new int[Row*Col];
        long seed1 = System.nanoTime();
        Random random = new Random(seed1);
        int[] cardnums = new int[Row*Col];
        for(int i=0;i<Row*Col;i++){
            int num = random.nextInt(Row*Col);
            while(cardnums[num]!=0){
                    num = random.nextInt(Row*Col);
                }
            cardnums[num]++;
            cardFlag[i]= num;
//            Log.v("random",num+"");
        }
    }
    private void set22(){
        cardFlag = new int[Row*Col];
        for(int i=0;i<Row*Col-1;i++){

            cardFlag[i]= i+1;
//            Log.v("random",num+"");
        }
    }
    private void setCards(int cardWidth, int cardHeight) {
//数字随机摆放,注意需要避免重复的数字
        setCardsnums();
//        set22();
        Card card;
        for (int x = 0; x < Row; x++) {
             for (int y= 0; y < Col; y++) {
                 int num = cardFlag[x*Col+y];
                    card = new Card(getContext());
                    if(num==0){
                        card.setNum(0);
                        EmptyCard[0] = x;
                        EmptyCard[1] = y;
                    }else{
                        card.setNum(num);
                    }
                    addView(card, cardWidth, cardHeight); // card是一个继承自FrameLayout的View
                    // 在initGameView()中指明这个GridLayout是四列的方阵
                    cardsMap[x][y] = card;
            }
        }
    }


    private void SwiperLeft( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];

        if(y<Col-1){
            cardsMap[x][y].setNum(cardsMap[x][y+1].getNum());
            cardsMap[x][y+1].setNum(0);
            EmptyCard[0] = x;
            EmptyCard[1] = y+1;
        }
    }

    private void SwiperRight( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];

        if(y>0){
            cardsMap[x][y].setNum(cardsMap[x][y-1].getNum());
            cardsMap[x][y-1].setNum(0);
            EmptyCard[0] = x;
            EmptyCard[1] = y-1;
        }
    }

    private void SwiperUp( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];
//        System.out.println("empty lefter"+x+" " +y);
        if(x<Row-1){
            cardsMap[x][y].setNum(cardsMap[x+1][y].getNum());
            cardsMap[x+1][y].setNum(0);
            EmptyCard[0] = x+1;
            EmptyCard[1] = y ;
        }
    }

    private void SwiperDown( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];

        if(x>0){
            cardsMap[x][y].setNum(cardsMap[x-1][y].getNum());
            cardsMap[x-1][y].setNum(0);
            EmptyCard[0] = x-1;
            EmptyCard[1] = y ;
        }
    }

  private boolean checkComplete(){
        for(int x = 0 ;x<Row; x++){
            for(int y = 0 ;y<Col;y++){
                if((x!=Row-1)||(y!=Col-1)){
                    if(cardsMap[x][y].getNum() != (x*Col+y+1)){
                        Log.v("masd"+x + " "+ y,cardsMap[x][y].getNum()+"");
                        return false;
                    }
                }
            }
      }
     LoadingDialog.LDialog(MainActivity.getMainActivity());
//        startGame();
        return true;
  }

  private int GetCardWidth()  {
        //屏幕信息的对象
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        //获取屏幕信息
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;
        //一行有四个卡片，每个卡片占屏幕的四分之一
        return ( cardWidth - 10 ) / Col;

    }

    public int getMovecount(){
        return movecount;
    }

//获取卡片定位方法
   private  class Myposition{
        private  float Startx = 0;
        private  float Starty = 10;


      public Myposition(float startx ,float starty,int cardwidth) {
          Startx = startx;
          Starty = starty;

      }
      public   int[] getposition(float x,float y ){
          int[] postion = new int[2];
          x = x -   Startx;
          y = y -  Starty;
          int Cardwidth = GetCardWidth();
//        除数不能为零，请务必检查代码是否有机会出现除数为零的情况。尴尬~~所以报错
          postion[0] = (int)x/Cardwidth;
          postion[1] = (int)y/Cardwidth;
          return postion ;
      }
      public   float getStartx(){
          return Startx;
      }
      public   float getStarty(){
          return Starty;
      }
  }

}

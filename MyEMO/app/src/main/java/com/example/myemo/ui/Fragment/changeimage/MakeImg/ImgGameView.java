package com.example.myemo.ui.Fragment.changeimage.MakeImg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myemo.R;
import com.example.myemo.ui.Fragment.changeimage.Dialog.LoadingDialog;
import com.example.myemo.ui.Fragment.changeimage.ImgActivity;
import com.example.myemo.ui.Fragment.changeimage.StartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImgGameView  extends GridLayout {
    private int    Row = StartActivity.num[0];
    private int    Col = StartActivity.num[1];


    private ImgCard[][] cardsMap = new ImgCard[Row][Col]; // 用一个二维数组来记录下方阵
    private int[] cardFlag = null;
    private int[]  EmptyCard = new int[2];
    private int movecount=0;
    private List<ImagePiece> BmList= null;
    private     Bitmap bitmap = null;
    public ImgGameView(Context context, AttributeSet attrs, int defStyle) { // 构造函数
        super(context, attrs, defStyle);
        initGameView(); // 初始化
    }

    public ImgGameView(Context context) { // 构造函数
        super(context);
        initGameView();
    }

    public ImgGameView(Context context, AttributeSet attrs) { // 构造函数
        super(context, attrs);
        initGameView();
    }

    private void initGameView() { // 初始化

        setColumnCount(Row); // 指明GridLayout布局是4列的
        setRowCount(Col);
        getBmList( );
        setBackgroundColor(Color.parseColor("#EEEEE0")); // 配置GameView的背景或颜色
        setCards(GetCardWidth(), GetCardWidth());

        setOnTouchListener(new OnTouchListener() { // 设置交互方式
            // 监听上下左右滑动的这几个动作，再由这几个动作去执行特定的代码，去实现游戏的逻辑
            private float startX, startY, offsetX, offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
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

                        }  else { // 判断向上向下
                            if (offsetY < -5) {
                                SwiperUp();   movecount++;
//                                 System.out.println("up");
                            } else if (offsetY > 5) {
                                SwiperDown();   movecount++;
//                                 System.out.println("down");
                            }
                        }
                        // 若为点击碎片事件
                        if(Math.abs(offsetX) ==0 && Math.abs(offsetY)== 0){
                            //获取坐标
//                            Log.v("当前坐标","x:"+startX + " y:"+startY);
//                            Log.v("获取位置","index " + getClickposition(startX,startY));
                            int[] index = getClickposition(startX,startY);
                            int numindex = cardsMap[index[0]-1][index[1]-1].getIndex()+1;
                            int numx = numindex/Col+1;
                            int numy = numindex%Col;
                            if(numy==0){
                                numy = Col;  //最后一个 除数商为0；
                                numx--;
                            }
                            Toast.makeText(getContext(),"当前碎片应位于 (x:"+numx+",y:"+numy+")",Toast.LENGTH_SHORT).show();
                        }else{
                            ImgActivity.setTvScore(movecount);
                            checkComplete();  //检查是否完成游戏
                        }
                        break;
                }
                return true; // 此处必须返回true,如返回false，则只会监听到MotionEvent.ACTION_DOWN这个事件，返回此事件没有成功，所以后面的事件也不会发生
            }
        });
        //添加长按监听事件，可以点击查看当前的图片的坐标， 方便辨别相同的图片的位置。
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                 // 获取坐标

                return true;
            }
        });



    }

    public void getBmList(){
        BmList = new ArrayList<ImagePiece>();
        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.q9);
          Log.v("adsad","weinull");
        }

        BmList = ImageSplitter.split(bitmap,Row,Col);

    }
    public  void setCarBitmap(Bitmap bm){
        bitmap = bm;
        Log.v("adsadhfyt","weinull");
    }
   private void setCards(int cardWidth,int cardHeight){
//       set22();
       setCardsnums();
       ImgCard imgcard;
       for (int x = 0; x < Row; x++) {
           for (int y= 0; y < Col; y++) {
               int num = cardFlag[x*Col+y];
               imgcard = new ImgCard(getContext());
               if(num==0){

                   EmptyCard[0] = x;
                   EmptyCard[1] = y;
               }
               ImagePiece imgpc = BmList.get(num);
               imgcard.setBm(imgpc.getBitmap(),imgpc.getIndex());

               addView(imgcard, cardWidth, cardHeight); // card是一个继承自FrameLayout的View  // 在initGameView()中指明这个GridLayout是四列的方阵
               cardsMap[x][y] = imgcard;
           }
       }
   }
    private void set22(){
        cardFlag = new int[Row*Col];    //dangqianyi 0 wei kong buxianshitupian
        for(int i=0;i<Row*Col;i++){

            cardFlag[i]= i;
//            Log.v("random",num+"");
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

    public void startGame() { // 开启游戏 若重新开始游戏的话就要先清理，清理完成后添加随机数
        cardFlag = new int[Row*Col];
        movecount = 0;
        getBmList();
        setCardsnums();
        ImgActivity.setTvScore(movecount);
//        set22();
        for (int x = 0; x < Row; x++) { // 对所有值进行清理
            for (int y = 0; y < Col; y++) {
                {
                    int num = cardFlag[x*Col+y];
                    if(num==0){
                        EmptyCard[0] = x;
                        EmptyCard[1] = y;

                    }
                    ImagePiece imgpc = BmList.get(num);
                    cardsMap[x][y].setBm(imgpc.getBitmap(),imgpc.getIndex());
                    cardsMap[x][y].setbackgAlpha(255);   //重新设置一下透明度
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { // 动态计算卡片宽高
        super.onSizeChanged(w, h, oldw, oldh);
//        startGame();
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
    private void exchang(int index2,int i,int j)
    {
        int x = EmptyCard[0];
        int y = EmptyCard[1];

        cardsMap[x+i][y+j].setBm(null,0);
        ImagePiece imgpc = BmList.get(index2);
        cardsMap[x][y].setBm(imgpc.getBitmap(),imgpc.getIndex());
        EmptyCard[0] = x+i;
        EmptyCard[1] = y+j;
//        Log.v("maisnd",0+" "+ index2);
    }
    private void SwiperLeft( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];

        if(y<Col-1){

            int num2 = cardsMap[x][y+1].getIndex();
          exchang( num2,0,1);
        }
    }

    private void SwiperRight( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];

        if(y>0){

            int num2 = cardsMap[x][y-1].getIndex();
            exchang( num2,0,-1);
        }
    }

    private void SwiperUp( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];
        if(x<Row-1){

            int num2 =cardsMap[x+1][y].getIndex();
            exchang( num2,1,0);
        }
    }

    private void SwiperDown( ){
        int x = EmptyCard[0];
        int y = EmptyCard[1];
        if(x>0){

            int num2 =cardsMap[x-1][y].getIndex();
            exchang( num2,-1,0);
        }
    }
    //检查结果并设置正确碎片的透明度为80；
    private boolean checkComplete(){
        boolean rs = true;
        for(int x = 0 ;x<Row; x++){
            for(int y = 0 ;y<Col;y++){
                if((x!=0)||(y!=0)){
                    if(cardsMap[x][y].getIndex() != (x*Col+y )){
//                        Log.v("imgmasd"+x + " "+ y,cardsMap[x][y].getIndex()+"");
                        cardsMap[x][y].setbackgAlpha(255);
                        rs = false;
                    }
                    if(cardsMap[x][y].getIndex()== (x*Col +y)){
                        cardsMap[x][y].setbackgAlpha(180);
                    }
                }
            }
        }
        if(rs){
            LoadingDialog.LDialog(ImgActivity.getImgActivity());
        }

        return rs;
    }

    private int[] getClickposition(float x,float y){
        int[] rs = new int[2] ;
        int col = (int) (x/GetCardWidth());
        int row = (int) ((y-10)/GetCardWidth());

//        Log.v("weizhi" ,"row:" + row + "col "+col);
      rs[0] = row+1;
      rs[1] = col+1;
        return rs;
    }

}

package com.example.changeimage.MakeImg;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.changeimage.ImgActivity;
import com.example.changeimage.R;

import java.util.ArrayList;
import java.util.List;

public class ImageSplitter
{
    /**
     * 将图片切成 , piece *piece
     *
     * @param bitmap
     * @param Row,Col
     * @return
     */
    public static Bitmap bm = null;
    public Bitmap getallImg(){
        return bm;
    }
    public static List<ImagePiece> split(Bitmap bitmap, int Row,int Col)
    {

        List<ImagePiece> pieces = new ArrayList<ImagePiece>(Row * Col);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Log.e("TAG", "bitmap Width = " + width + " , height = " + height);
        int after_width = Math.min(width,height);
        int startx=0,starty=0;
        if(after_width == width){
            starty = (height- width)/2;
            startx = 0;
        }else if(after_width == height){
            startx = (width - height)/2;
            starty = 0;
        }
        //x he y buyaogaofanle!!!!11
        int pieceWidth = after_width / Col;
//        int pieceHeight = height / Row;
        bitmap = Bitmap.createBitmap(bitmap,startx,starty,after_width,after_width);
        bm = bitmap;
     Log.v("asd",startx+" "+starty+" "+after_width);
        for (int i = 0; i < Row; i++)
        {
            for (int j = 0; j < Col; j++)
            {
                ImagePiece imagePiece = new ImagePiece();

                int xValue = j * pieceWidth;
                int yValue = i * pieceWidth;
                imagePiece.index = j + i *Col;
                imagePiece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,  pieceWidth, pieceWidth);
                pieces.add(imagePiece);
            }
        }
        return pieces;
    }

}
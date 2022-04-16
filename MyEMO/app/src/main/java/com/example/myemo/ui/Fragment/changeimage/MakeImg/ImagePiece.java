package com.example.myemo.ui.Fragment.changeimage.MakeImg;

import android.graphics.Bitmap;

public class ImagePiece {
    public int index = 0;
    public Bitmap bitmap = null;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ImagePiece() {   }
}

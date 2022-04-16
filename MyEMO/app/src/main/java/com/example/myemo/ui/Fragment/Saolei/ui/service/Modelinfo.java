package com.example.myemo.ui.Fragment.Saolei.ui.service;

public class Modelinfo {
    int id;
    int ROW ;
    int COL;
    int textsize;
    int leicount;
    public Modelinfo(int ROW,int COL,int textsize,int leicount) {
        this.COL = COL;
        this.ROW = ROW;
        this.textsize = textsize;
        this.leicount = leicount;
    }
    public Modelinfo(){

    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public int getCOL() {
        return COL;
    }

    public void setCOL(int COL) {
        this.COL = COL;
    }

    public int getLeicount() {
        return leicount;
    }

    public void setLeicount(int leicount) {
        this.leicount = leicount;
    }

    public int getROW() {
        return ROW;
    }

    public void setROW(int ROW) {
        this.ROW = ROW;
    }

    public int getTextsize() {
        return textsize;
    }

    public void setTextsize(int textsize) {
        this.textsize = textsize;
    }

    public String toString(){
        return ROW +" "+ COL +" "+ leicount +" "+ textsize;
    }
}

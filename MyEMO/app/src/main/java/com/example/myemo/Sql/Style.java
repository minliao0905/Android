package com.example.myemo.Sql;

public class Style {
    private Integer styleid;
    private String background;
    private String textcolor;

    public Style(Integer styleid, String background, String textcolor) {
        this.styleid = styleid;
        this.background = background;
        this.textcolor = textcolor;
    }
public Style(){

}
    public Integer getStyleid() {
        return styleid;
    }

    public void setStyleid(Integer styleid) {
        this.styleid = styleid;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.textcolor = textcolor;
    }

    @Override
    public String toString() {
        return "Style{" +
                "styleid=" + styleid +
                ", background='" + background + '\'' +
                ", textcolor='" + textcolor + '\'' +
                '}';
    }
}

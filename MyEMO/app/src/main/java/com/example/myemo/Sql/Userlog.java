package com.example.myemo.Sql;


import java.util.Date;

public class Userlog {
    private Integer logid;
    private  String logtext;
    private String  logtime;
    private Integer styleid;

    public Userlog(Integer logid, String logtext, String logtime, Integer styleid) {
        this.logid = logid;
        this.logtext = logtext;
        this.logtime = logtime;
        this.styleid = styleid;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getLogtext() {
        return logtext;
    }

    public void setLogtext(String logtext) {
        this.logtext = logtext;
    }

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    public Integer getStyleid() {
        return styleid;
    }

    public void setStyleid(Integer styleid) {
        this.styleid = styleid;
    }

    @Override
    public String toString() {
        return "Userlog{" +
                "logid=" + logid +
                ", logtext='" + logtext + '\'' +
                ", logtime=" + logtime +
                ", styleid=" + styleid +
                '}';
    }
}

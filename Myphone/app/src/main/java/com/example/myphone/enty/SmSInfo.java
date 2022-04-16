package com.example.myphonenumber.entity;
//短信类
public class SmSInfo {
    private int _id;
    private String address;
    private int type;
    private String body;
    private long date;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public SmSInfo(int _id, String address, int type, String body, long date) {
        this.address = address;
        this._id = _id;
        this.type = type;
        this.body = body;
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "SmSInfo{" +
                "_id=" + _id +
                ", address='" + address + '\'' +
                ", type=" + type +
                ", body='" + body + '\'' +
                ", date=" + date +
                '}';
    }
}

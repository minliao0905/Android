package com.example.myphonenumber.entity;
//contacts 表
//        _id ：表的 ID，关键字。
//        display_name: 联系人名称
//        photo_id:头像的 ID，如果没有设置联系人头像，该字段就为空
//        times_contacted:通话记录的次数
//        last_time_contacted: 最后的通话时间
//        lookup :是一个持久化的储存 因为用户可能会改名字，但不能改 lookup 值
public class Contact {
    private int _id;
    private String dispaly_name;
    private int photo_id;
    private long last_time;
    private int lookup;


    public Contact(int id, String dispaly_name, int photo_id, int last_time, int lookup) {
        this._id = id;
        this.dispaly_name = dispaly_name;
        this.photo_id = photo_id;
        this.last_time = last_time;
        this.lookup = lookup;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDispaly_name() {
        return dispaly_name;
    }

    public void setDispaly_name(String dispaly_name) {
        this.dispaly_name = dispaly_name;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
    }

    public int getLookup() {
        return lookup;
    }

    public void setLookup(int lookup) {
        this.lookup = lookup;
    }
}

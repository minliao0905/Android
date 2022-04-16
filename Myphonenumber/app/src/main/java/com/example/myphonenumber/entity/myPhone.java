package com.example.myphonenumber.entity;

import android.net.Uri;

public class myPhone {

    private  Long contactId;
    private  String phonenumber;
    private  String username;
    private  String userimg;
    private  String useremail;
    private  String userothertext;
    private  String userbirth;
    private  String useraddress;
    public myPhone(){

    }
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getUserbirth() {
        return userbirth;
    }

    public void setUserbirth(String userbirth) {
        this.userbirth = userbirth;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserothertext() {
        return userothertext;
    }

    public void setUserothertext(String userothertext) {
        this.userothertext = userothertext;
    }

    public myPhone(Long contactId,String phonenumber, String username, String useremail, String userothertext, String useraddress, String userbirth, String userimg) {
        this.phonenumber = phonenumber;
        this.useraddress = useraddress;
        this.userothertext = userothertext;
        this.userimg = userimg;
        this.userbirth = userbirth;
        this.useremail = useremail;
        this.username = username;
        this.contactId = contactId;
    }
    public String toString(){
        return contactId + " " +phonenumber +" " + username + " " + useremail + " " + userothertext +" " +useraddress + " " + userbirth  + " " + userbirth +" " + userimg ;
    }
}

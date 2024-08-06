package com.example.myapplication;

public class Users {
    String profilpic="",mail="",username="",password="",userid="",lastmessage="",status="";
    public Users(String id, String namee, String emaill, String pass, String cpass, String imageuri, String status){
        this.profilpic=imageuri;
        this.username=namee;
        this.mail=emaill;
        this.password=pass;
        this.status=status;
        this.userid=id;
    }

    Users(){}

    public Users(String id, String namee, String emaill, String pass, String imageuri, String status) {
        this.profilpic=imageuri;
        this.username=namee;
        this.mail=emaill;
        this.password=pass;
        this.status=status;
        this.userid=id;
    }


    public String getProfilpic() {
        return profilpic;
    }

    public void setProfilpic(String profilpic) {
        this.profilpic = profilpic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

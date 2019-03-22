package com.example.programlamadiliogren;

public class Yorum {
    private String yorum;
    private String user;
    private String icerik;
    public Yorum(String s) {
    }

    public Yorum(String yorum, String user,String icerik) {
        this.yorum = yorum;
        this.user = user;
        this.icerik=icerik;

    }

    public Yorum() {
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

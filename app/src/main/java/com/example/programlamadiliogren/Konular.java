package com.example.programlamadiliogren;

public class Konular {
    private String konuAdi;
    private String dilAdi;
    private String image;
    public Konular() {
    }

    public Konular(String konuAdi,String image) {
        this.konuAdi = konuAdi;
        this.image=image;
    }

    public Konular(String konuAdi, String dilAdi,String image) {
        this.konuAdi = konuAdi;
        this.dilAdi = dilAdi;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public String getDilAdi() {
        return dilAdi;
    }

    public void setDilAdi(String dilAdi) {
        this.dilAdi = dilAdi;
    }
}

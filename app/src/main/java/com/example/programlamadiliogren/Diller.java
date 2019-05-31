package com.example.programlamadiliogren;

public class Diller {
    private String dilAdi;
private String image;
    public Diller() {
    }

    public Diller(String dilAdi,String image) {
        this.dilAdi = dilAdi;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDilAdi() {
        return dilAdi;
    }

    public void setDilAdi(String dilAdi) {
        this.dilAdi = dilAdi;
    }
}

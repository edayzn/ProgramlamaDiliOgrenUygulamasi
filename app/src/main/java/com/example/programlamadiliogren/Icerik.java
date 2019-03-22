package com.example.programlamadiliogren;

public class Icerik {
private String icerik;
private String baslikAdi;

    public Icerik() {
    }

    public Icerik(String icerik, String baslikAdi) {
        this.icerik = icerik;
        this.baslikAdi = baslikAdi;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getBaslikAdi() {
        return baslikAdi;
    }

    public void setBaslikAdi(String baslikAdi) {
        this.baslikAdi = baslikAdi;
    }


}

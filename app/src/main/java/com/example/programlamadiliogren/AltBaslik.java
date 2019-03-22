package com.example.programlamadiliogren;

public class AltBaslik {
    private String baslikAdi;
    private String konuAdi;

    public AltBaslik() {
    }

    public AltBaslik(String baslikAdi, String konuAdi) {
        this.baslikAdi = baslikAdi;
        this.konuAdi = konuAdi;
    }

    public String getBaslikAdi() {
        return baslikAdi;
    }

    public void setBaslikAdi(String baslikAdi) {
        this.baslikAdi = baslikAdi;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }
}

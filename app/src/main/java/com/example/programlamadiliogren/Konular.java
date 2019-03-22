package com.example.programlamadiliogren;

public class Konular {
    private String konuAdi;
    private String dilAdi;

    public Konular() {
    }

    public Konular(String konuAdi, String dilAdi) {
        this.konuAdi = konuAdi;
        this.dilAdi = dilAdi;
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

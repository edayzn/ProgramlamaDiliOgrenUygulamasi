package com.example.programlamadiliogren;

public class SoruCevap {
    private String soru;
    private String cevap;
    private String secenek1;
    private String secenek2;
    private String secenek3;
    private String icerik;
    public SoruCevap() {
    }

    public SoruCevap(String soru, String cevap, String secenek1, String secenek2, String secenek3,String icerik) {
        this.soru = soru;
        this.cevap = cevap;
        this.secenek1 = secenek1;
        this.secenek2 = secenek2;
        this.secenek3 = secenek3;
        this.icerik=icerik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }

    public String getSecenek1() {
        return secenek1;
    }

    public void setSecenek1(String secenek1) {
        this.secenek1 = secenek1;
    }

    public String getSecenek2() {
        return secenek2;
    }

    public void setSecenek2(String secenek2) {
        this.secenek2 = secenek2;
    }

    public String getSecenek3() {
        return secenek3;
    }

    public void setSecenek3(String secenek3) {
        this.secenek3 = secenek3;
    }
}

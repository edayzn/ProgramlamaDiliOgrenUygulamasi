package com.example.programlamadiliogren;

public class SoruCevap {
    private String soru;
    private String secenek1;
    private String secenek2;
    private String secenek3;
    private String cevap;
    private String baslikAdi;
    public SoruCevap() {
    }

    public SoruCevap(String soru, String secenek1, String secenek2, String secenek3, String cevap) {
        this.soru = soru;
        this.secenek1 = secenek1;
        this.secenek2 = secenek2;
        this.secenek3 = secenek3;
        this.cevap = cevap;
    }

    public SoruCevap(String soru, String secenek1, String secenek2, String secenek3, String cevap, String baslikAdi) {
        this.soru = soru;
        this.secenek1 = secenek1;
        this.secenek2 = secenek2;
        this.secenek3 = secenek3;
        this.cevap = cevap;
        this. baslikAdi= baslikAdi;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
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

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }

    public String getBaslikAdi() {
        return baslikAdi;
    }

    public void setBaslikAdi(String baslikAdi) {
        this.baslikAdi = baslikAdi;
    }

    public boolean isCorrectAnswer(String selectedAnswerText){

        return selectedAnswerText.matches(cevap);

    }
}

package com.example.programlamadiliogren;

public class TestSonuc {
    private String baslikAdi;
    private String user;
    private boolean sonuc;

    public TestSonuc() {
    }

    public TestSonuc(String baslikAdi, String user, boolean sonuc) {
        this.baslikAdi = baslikAdi;
        this.user = user;
        this.sonuc = sonuc;
    }

    public String getBaslikAdi() {
        return baslikAdi;
    }

    public void setBaslikAdi(String baslikAdi) {
        this.baslikAdi = baslikAdi;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isSonuc() {
        return sonuc;
    }

    public void setSonuc(boolean sonuc) {
        this.sonuc = sonuc;
    }
}

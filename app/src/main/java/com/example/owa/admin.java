package com.example.owa;

public class admin {
    String adname, ademail, adpass;

    public admin() {
    }

    public admin(String adname, String ademail, String adpass) {
        this.adname = adname;
        this.ademail = ademail;
        this.adpass = adpass;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getAdemail() {
        return ademail;
    }

    public void setAdemail(String ademail) {
        this.ademail = ademail;
    }

    public String getAdpass() {
        return adpass;
    }

    public void setAdpass(String adpass) {
        this.adpass = adpass;
    }
}



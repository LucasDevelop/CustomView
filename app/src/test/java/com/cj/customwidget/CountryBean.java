package com.cj.customwidget;

public class CountryBean {
    public String zh;
    public String tw;
    public String en;
    public String locale;
    public String country;
    public float code;
    public String iso;

    @Override
    public String toString() {
        return "CountryBean{" +
                "zh='" + zh + '\'' +
                ", tw='" + tw + '\'' +
                ", en='" + en + '\'' +
                ", locale='" + locale + '\'' +
                ", country='" + country + '\'' +
                ", code=" + code +
                ", iso=" + iso +
                '}';
    }
}

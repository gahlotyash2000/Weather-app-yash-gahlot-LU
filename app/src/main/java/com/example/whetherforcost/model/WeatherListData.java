package com.example.whetherforcost.model;

public class WeatherListData {
    private String time, cloadState, tempp, icon, humidity, cludStatus;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCludStatus() {
        return cludStatus;
    }

    public void setCludStatus(String cludStatus) {
        this.cludStatus = cludStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCloadState() {
        return cloadState;
    }

    public void setCloadState(String cloadState) {
        this.cloadState = cloadState;
    }

    public String getTempp() {
        return tempp;
    }

    public void setTempp(String tempp) {
        this.tempp = tempp;
    }
}

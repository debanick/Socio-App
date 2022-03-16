package com.example.socio;

public class ProductsModel {
    String evnt,nam,det,date,time,loc,phn;

    public ProductsModel() {

    }

    public ProductsModel(String evnt, String nam, String det, String date, String time, String loc,String phn) {
        this.evnt = evnt;
        this.nam = nam;
        this.det = det;
        this.date = date;
        this.time = time;
        this.loc = loc;
        this.phn=phn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvnt() {
        return evnt;
    }

    public void setEvent(String event) {
        this.evnt = event;
    }

    public String getDet() {
        return det;
    }

    public void setDesc(String desc) {
        this.det = desc;
    }

    public String getLoc() {
        return loc;
    }

    public void setLocation(String location) {
        this.loc = location;
    }

    public String getNam() {
        return nam;
    }

    public void setname(String name) {
        this.nam = name;
    }

    public String getTime() {
        return time;
    }

    public void setphn(String phn) {
        this.phn = phn;
    }

    public String getPhn() {
        return phn;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

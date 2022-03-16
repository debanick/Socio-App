package com.example.socio;

public class events {
    String evnt,nam,det,date,time,loc,phn;
    public events(){

    }

    public events(String evnt, String det, String nam, String date, String time, String loc,String phn) {
        this.evnt = evnt;
        this.nam = nam;
        this.det = det;
        this.date = date;
        this.time = time;
        this.loc = loc;
        this.phn =phn;
    }

    public String getEvnt() {
        return evnt;
    }

    public String getNam() {
        return nam;
    }

    public String getDet() {
        return det;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLoc() {
        return loc;
    }

    public String getPhn(){ return phn; }
}

package com.theatre.theatre_system.models;

import java.sql.Date;
import java.sql.Time;

public class Repertoire {
    private int repertoireId;
    private int performanceId;
    private Date showDate;
    private Time shoeTime;
    private boolean isPremiere;
    private String period;
    private float price;

    public Repertoire(int performanceId, Date showDate, Time shoeTime, boolean isPremiere, String period, float price) {
        this.performanceId = performanceId;
        this.showDate = showDate;
        this.shoeTime = shoeTime;
        this.isPremiere = isPremiere;
        this.period = period;
        this.price = price;
    }

    public Repertoire(int repertoireId, int performanceId, Date showDate, Time shoeTime, boolean isPremiere, String period, float price) {
        this.repertoireId = repertoireId;
        this.performanceId = performanceId;
        this.showDate = showDate;
        this.shoeTime = shoeTime;
        this.isPremiere = isPremiere;
        this.period = period;
        this.price = price;
    }

    public int getRepertoireId() {
        return repertoireId;
    }

    public void setRepertoireId(int repertoireId) {
        this.repertoireId = repertoireId;
    }

    public int getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(int performanceId) {
        this.performanceId = performanceId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Time getShoeTime() {
        return shoeTime;
    }

    public void setShoeTime(Time shoeTime) {
        this.shoeTime = shoeTime;
    }

    public boolean isPremiere() {
        return isPremiere;
    }

    public void setPremiere(boolean premiere) {
        isPremiere = premiere;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

package com.theatre.theatre_system.models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Repertoire {
    private int repertoireId;
    private int performanceId;
    private LocalDate showDate;
    private LocalTime showTime;
    private boolean isPremiere;
    private String period;
    private float price;

    public Repertoire(int performanceId, LocalDate showDate, LocalTime showTime, boolean isPremiere, String period, float price) {
        this.performanceId = performanceId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.isPremiere = isPremiere;
        this.period = period;
        this.price = price;
    }

    public Repertoire(int repertoireId, int performanceId, LocalDate showDate, LocalTime showTime, boolean isPremiere, String period, float price) {
        this.repertoireId = repertoireId;
        this.performanceId = performanceId;
        this.showDate = showDate;
        this.showTime = showTime;
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

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
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
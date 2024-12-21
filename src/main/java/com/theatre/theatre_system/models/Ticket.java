package com.theatre.theatre_system.models;

import java.sql.Date;

public class Ticket {
    private int ticketId;
    private int repertoireId;
    private String seat;
    private float price;
    private String status;
    private Date saleDate;

    public Ticket(int repertoireId, String seat, float price, String status, Date saleDate) {
        this.repertoireId = repertoireId;
        this.seat = seat;
        this.price = price;
        this.status = status;
        this.saleDate = saleDate;
    }

    public Ticket(int ticketId, int repertoireId, String seat, float price, String status, Date saleDate) {
        this.ticketId = ticketId;
        this.repertoireId = repertoireId;
        this.seat = seat;
        this.price = price;
        this.status = status;
        this.saleDate = saleDate;
    }

    public enum Status {
        AVAILABLE,
        SOLD,
        BOOKED
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getRepertoireId() {
        return repertoireId;
    }

    public void setRepertoireId(int repertoireId) {
        this.repertoireId = repertoireId;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
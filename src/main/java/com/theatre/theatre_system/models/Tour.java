package com.theatre.theatre_system.models;

import java.sql.Date;

public class Tour {
    private int tourId;
    private int employeeId;
    private Date startDate;
    private Date endDate;
    private String location;

    public Tour(int employeeId, Date startDate, Date endDate, String location) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public Tour(Date startDate, Date endDate, String location, int employeeId, int tourId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.employeeId = employeeId;
        this.tourId = tourId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

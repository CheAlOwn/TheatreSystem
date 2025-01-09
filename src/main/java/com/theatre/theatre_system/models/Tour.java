package com.theatre.theatre_system.models;

import java.time.LocalDate;

public class Tour {
    private int tourId;
    private int employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;

    public Tour(int employeeId, LocalDate startDate, LocalDate endDate, String location) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public Tour(int employeeId, int tourId, LocalDate startDate, LocalDate endDate, String location) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
package com.theatre.theatre_system.models;

public class Musician {
    private int musicianId;
    private int employeeId;
    private String instrument;

    public Musician(int employeeId, String instrument) {
        this.employeeId = employeeId;
        this.instrument = instrument;
    }

    public Musician(int musicianId, int employeeId, String instrument) {
        this.musicianId = musicianId;
        this.employeeId = employeeId;
        this.instrument = instrument;
    }

    public int getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(int musicianId) {
        this.musicianId = musicianId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
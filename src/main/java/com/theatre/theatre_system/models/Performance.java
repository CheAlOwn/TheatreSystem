package com.theatre.theatre_system.models;

public class Performance {
    private int performanceId;
    private String name;
    private String genre;
    private String author;
    private int directorId;
    private int setDesignerId;
    private int conductorId;

    public Performance(String name, String genre, String author, int directorId, int setDesignerId, int conductorId) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.directorId = directorId;
        this.setDesignerId = setDesignerId;
        this.conductorId = conductorId;
    }

    public Performance(int performanceId, String name, String genre, String author, int directorId, int setDesignerId, int conductorId) {
        this.performanceId = performanceId;
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.directorId = directorId;
        this.setDesignerId = setDesignerId;
        this.conductorId = conductorId;
    }

    public int getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(int performanceId) {
        this.performanceId = performanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public int getSetDesignerId() {
        return setDesignerId;
    }

    public void setSetDesignerId(int setDesignerId) {
        this.setDesignerId = setDesignerId;
    }

    public int getConductorId() {
        return conductorId;
    }

    public void setConductorId(int conductorId) {
        this.conductorId = conductorId;
    }
}

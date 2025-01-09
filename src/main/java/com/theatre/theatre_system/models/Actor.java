package com.theatre.theatre_system.models;

public class Actor {
    private int actorId;
    private int employeeId;
    private float height;
    private String timbre;

    public Actor(int employeeId, float height, String timbre) {
        this.employeeId = employeeId;
        this.height = height;
        this.timbre = timbre;
    }

    public Actor(int actorId, int employeeId, float height, String timbre) {
        this.actorId = actorId;
        this.employeeId = employeeId;
        this.height = height;
        this.timbre = timbre;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getTimbre() {
        return timbre;
    }

    public void setTimbre(String timbre) {
        this.timbre = timbre;
    }
}
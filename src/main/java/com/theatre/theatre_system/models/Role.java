package com.theatre.theatre_system.models;

public class Role {
    private int roleId;
    private String roleName;
    private int performanceId;
    private int actorId;
    private int understudyId;

    public Role(String roleName, int performanceId, int actorId, int understudyId) {
        this.roleName = roleName;
        this.performanceId = performanceId;
        this.actorId = actorId;
        this.understudyId = understudyId;
    }

    public Role(int roleId, String roleName, int performanceId, int actorId, int understudyId) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.performanceId = performanceId;
        this.actorId = actorId;
        this.understudyId = understudyId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(int performanceId) {
        this.performanceId = performanceId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getUnderstudyId() {
        return understudyId;
    }

    public void setUnderstudyId(int understudyId) {
        this.understudyId = understudyId;
    }
}
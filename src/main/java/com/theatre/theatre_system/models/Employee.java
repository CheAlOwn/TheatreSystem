package com.theatre.theatre_system.models;

import java.sql.Date;
import java.time.LocalDate;

public class Employee {
    private int employeeId;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthday;
    private String gender;
    private int hireYear;
    private String category;
    private String post;
    private float salary;
    private String phone;
    private String address;
    private int experience;
    private int childrenCount;

    public Employee(String lastName, String firstName, String middleName, LocalDate birthday, String gender, int hireYear, String category, String post, float salary, String phone, String address, int experience, int childrenCount) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.gender = gender;
        this.hireYear = hireYear;
        this.category = category;
        this.post = post;
        this.salary = salary;
        this.phone = phone;
        this.address = address;
        this.experience = experience;
        this.childrenCount = childrenCount;
    }

    public Employee(int employeeId, String lastName, String firstName, String middleName, LocalDate birthday, String gender, int hireYear, String category, String post, float salary, String phone, String address, int experience, int childrenCount) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.gender = gender;
        this.hireYear = hireYear;
        this.category = category;
        this.post = post;
        this.salary = salary;
        this.phone = phone;
        this.address = address;
        this.experience = experience;
        this.childrenCount = childrenCount;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender() {
        this.gender = gender;
    }

    public int getHireYear() {
        return hireYear;
    }

    public void setHireYear(int hireYear) {
        this.hireYear = hireYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}

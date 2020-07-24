package com.glushkov.entity;

import java.time.LocalDate;


public class User {

    private int id;
    private String firstName;
    private String secondName;
    private double salary;
    private LocalDate dateOfBirth;

    public User() {
    }

    public User(int id, String firstName, String secondName, double salary, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "[User id:" + id + ", First name: " + firstName + ", Second name: " + secondName + "," +
                " Salary: " + salary + ", dateOfBirth: " + dateOfBirth + "]";
    }
}




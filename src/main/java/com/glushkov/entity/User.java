package com.glushkov.entity;

import com.glushkov.dao.jdbc.generator.Column;
import com.glushkov.dao.jdbc.generator.Id;
import com.glushkov.dao.jdbc.generator.Table;

import java.time.LocalDate;

@Table(name = "users")
public class User {
    @Id
    @Column
    private int id;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private double salary;
    @Column
    private LocalDate dateOfBirth;

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
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", salary=" + salary +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}




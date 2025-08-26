package com.oocl.training.dao;

public class Employee {
    private int id;
    private String name;
    private String gender;
    private int salary;
    private int age;

    public Employee(int id, String name, String gender, int salary, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.age = age;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

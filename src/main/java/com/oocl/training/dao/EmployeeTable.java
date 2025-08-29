package com.oocl.training.dao;

import com.oocl.training.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public interface EmployeeTable {
    public Employee newEmployee(Employee employee);

    public Employee updateEmployee(int employeeId,Employee employee);

    public Employee getEmployee(int employeeId);

    public List<Employee> getEmployee();

    public List<Employee> getEmployee(String gender);

    public void remove(int employeeId) ;
    public void drop();
    //public List<Employee> getByPage(Integer)
}

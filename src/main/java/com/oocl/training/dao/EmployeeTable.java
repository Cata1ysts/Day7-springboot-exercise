package com.oocl.training.dao;

import com.oocl.training.model.Employee;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeeTable {
    public Map<Integer, Employee> employeeTable;
    private int employeeIndex;
    public EmployeeTable() {
        this.employeeTable = new HashMap<>(Map.of(
                1, new Employee(1, "John Smith", "male",32,  5000,1),
                2, new Employee(2, "Jane Johnson", "female",28,  6000,1),
                3, new Employee(3, "David Williams","male", 35,  5500,2),
                4, new Employee(4, "Emily Brown","female", 23,  4500,1),
                5, new Employee(5, "Michael Jones", "male",40,  7000,2)));
        this.employeeIndex=5;
    }


    public Employee newEmployee(Employee employee){
        employeeIndex++;
        employee.setId(employeeIndex);
        employeeTable.put(employeeIndex,employee);
        return employeeTable.get(employeeIndex);
    }

    public Employee updateEmployee(int employeeId,Employee employee){
        return employeeTable.put(employee.getId(),employee);
    }

    public Employee getEmployee(int employeeId){
        return employeeTable.getOrDefault(employeeId,null);
    }

    public List<Employee> getEmployee(){
        return employeeTable.values().stream().toList();
    }

    public List<Employee> getEmployee(String gender){
        return employeeTable.values().stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    public void remove(int employeeId) {
        employeeTable.remove(employeeId);
    }

}

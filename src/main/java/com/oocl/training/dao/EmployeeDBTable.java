package com.oocl.training.dao;

import com.oocl.training.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class EmployeeDBTable implements EmployeeTable {

    private JpaEmployeeTable jpaEmployeeTable;
    public EmployeeDBTable(JpaEmployeeTable jpaEmployeeTable){
        this.jpaEmployeeTable=jpaEmployeeTable;
    }

    @Override
    public Employee newEmployee(Employee employee) {
        employee.setId(0);
        return jpaEmployeeTable.save(employee);
    }

    @Override
    public Employee updateEmployee(int employeeId, Employee employee) {
        employee.setId(employeeId);
        return jpaEmployeeTable.save(employee);
    }

    @Override
    public Employee getEmployee(int employeeId) {
        return jpaEmployeeTable.findById(employeeId).orElse(null);
    }

    @Override
    public List<Employee> getEmployee() {
        return jpaEmployeeTable.findAll();
    }

    @Override
    public List<Employee> getEmployee(String gender) {
        return jpaEmployeeTable.getEmployeesByGender(gender);
    }

    @Override
    public void remove(int employeeId) {
        jpaEmployeeTable.deleteById(employeeId);

    }

    @Override
    public void drop() {
        jpaEmployeeTable.deleteAll();

    }

    @Override
    public List<Employee> getEmployeesByCompanyId(int companyId) {
        return jpaEmployeeTable.getEmployeesByCompanyId(companyId);
    }
}

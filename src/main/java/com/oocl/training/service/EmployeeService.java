package com.oocl.training.service;

import com.oocl.training.Util.EmployeeEntity;
import com.oocl.training.dao.CompanyTable;
import com.oocl.training.dao.EmployeeTable;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeTable employeeTable;
    public EmployeeService(CompanyTable companyTable,EmployeeTable employeeTable) {
        this.employeeTable = employeeTable;
    }
    public Employee addEmployee(Employee employee){
        checkEmployeeValidity(employee);
        return employeeTable.newEmployee(employee);

    }

    private static void checkEmployeeValidity(Employee employee) {
        if(employee.getAge()<18 || employee.getAge()>65) throw new IllegalArgumentException("员工年龄不符合");
        if(employee.getAge()>30 && employee.getSalary()<20000)  throw new IllegalArgumentException("员工工资太低");
    }

    public Employee getEmployeeById(int id) {
        Employee employee = employeeTable.getEmployee(id);
        checkEmployeeValidity(employee);
        return employee;
    }

    public List<Employee> getEmployeeByGender(String gender){
        if (gender != null && !gender.isEmpty()) {
            return employeeTable.getEmployee(gender).stream()
                    .filter(employee -> employee.getActive())
                    .toList();
        }
        return employeeTable.getEmployee();
    }

    public Employee updateEmployee(int id, Employee employee){
        checkEmployeeValidity(employee);
        Employee employee_old = employeeTable.getEmployee(id);
        checkEmployeeExsisted(employee_old);
        employee.setId(employee_old.getId());
        return employeeTable.updateEmployee(id,employee);
    }

    private static void checkEmployeeExsisted(Employee employee_old) {
        if(employee_old == null) throw new IllegalArgumentException("员工不存在");
        if(employee_old.getActive()==false) throw new IllegalArgumentException("员工已离职");
    }

    public void deleteEmployeeById(int id) {
        //employeeTable.remove(id);
        Employee employee = employeeTable.getEmployee(id);
        checkEmployeeExsisted(employee);
        employee.setActive(false);
        employeeTable.updateEmployee(id,employee);

    }


    public List<Employee> getEmployeeByPage(int page, int size) {
        List<Employee> employees = employeeTable.getEmployee();
        int start = (page - 1) * size;
        int end = Math.min(start + size, employees.size());
        List<Employee> pageEmployees = employees.subList(start, end).stream()
                .filter(employee -> employee.getActive())
                .toList();
        return pageEmployees;
    }
}


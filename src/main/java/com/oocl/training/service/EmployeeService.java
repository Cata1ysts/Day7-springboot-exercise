package com.oocl.training.service;

import com.oocl.training.Util.DataBaseQueryException;
import com.oocl.training.Util.EmployeeInfoException;
import com.oocl.training.dao.CompanyMemoryTable;
import com.oocl.training.dao.EmployeeMemoryTable;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeMemoryTable employeeMemoryTable;
    public EmployeeService(CompanyMemoryTable companyMemoryTable, EmployeeMemoryTable employeeMemoryTable) {
        this.employeeMemoryTable = employeeMemoryTable;
    }
    public Employee addEmployee(Employee employee){
        checkEmployeeValidity(employee);
        return employeeMemoryTable.newEmployee(employee);

    }

    private static void checkEmployeeValidity(Employee employee) {
        if(employee.getAge()<18 || employee.getAge()>65) throw new EmployeeInfoException("员工年龄不符合");
        if(employee.getAge()>30 && employee.getSalary()<20000)  throw new EmployeeInfoException("员工工资太低");
    }

    public Employee getEmployeeById(int id) {
        Employee employee = employeeMemoryTable.getEmployee(id);
        checkEmployeeValidity(employee);
        return employee;
    }

    public List<Employee> getEmployeeByGender(String gender){
        if (gender != null && !gender.isEmpty()) {
            return employeeMemoryTable.getEmployee(gender).stream()
                    .filter(employee -> employee.getActive())
                    .filter(employee -> employee.getGender().equals(gender))
                    .toList();
        }
        return employeeMemoryTable.getEmployee();
    }

    public Employee updateEmployee(int id, Employee employee){
        checkEmployeeValidity(employee);
        Employee employee_old = employeeMemoryTable.getEmployee(id);
        checkEmployeeExsisted(employee_old);
        employee.setId(employee_old.getId());
        return employeeMemoryTable.updateEmployee(id,employee);
    }

    private static void checkEmployeeExsisted(Employee employee_old) {
        if(employee_old == null) throw new DataBaseQueryException("员工不存在");
        if(employee_old.getActive()==false) throw new EmployeeInfoException("员工已离职");
    }

    public void deleteEmployeeById(int id) {
        //employeeMemoryTable.remove(id);
        Employee employee = employeeMemoryTable.getEmployee(id);
        checkEmployeeExsisted(employee);
        employee.setActive(false);
        employeeMemoryTable.updateEmployee(id,employee);

    }


    public List<Employee> getEmployeeByPage(int page, int size) {
        List<Employee> employees = employeeMemoryTable.getEmployee();
        int start = (page - 1) * size;
        int end = Math.min(start + size, employees.size());
        List<Employee> pageEmployees = employees.subList(start, end).stream()
                .filter(employee -> employee.getActive())
                .toList();
        return pageEmployees;
    }
}


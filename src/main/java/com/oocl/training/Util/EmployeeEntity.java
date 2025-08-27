package com.oocl.training.Util;

import com.oocl.training.model.Employee;

public class EmployeeEntity {
    private Employee employee;
    private int companyId;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employeeInfo) {
        this.employee = employeeInfo;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public EmployeeEntity(Employee employee, int companyId) {
        this.employee = employee;
        this.companyId = companyId;
    }
}

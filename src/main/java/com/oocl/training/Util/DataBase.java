package com.oocl.training.Util;

import com.oocl.training.dao.Company;
import com.oocl.training.dao.Employee;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataBase {
    public Map<Integer, Company> companyTable;
    public Map<Integer, Employee> employeeTable;
    public Map<Integer,Integer> companyemployeeTable;

    public DataBase() {
        this.companyTable = new HashMap<>();
        this.employeeTable = new HashMap<>();
        this.companyemployeeTable = new HashMap<>();
    }
}

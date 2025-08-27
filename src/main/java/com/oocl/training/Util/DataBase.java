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
    private int employeeIndex;
    private int companyIndex;

    public DataBase() {
        this.companyTable = new HashMap<>();
        this.employeeTable = new HashMap<>();
        this.companyemployeeTable = new HashMap<>();
        this.employeeIndex=0;
        this.companyIndex=0;
    }

    public int companyIndexInc(){return ++companyIndex;}
    public int employeeIndexInc(){return ++employeeIndex;}
}

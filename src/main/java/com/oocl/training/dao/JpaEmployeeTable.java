package com.oocl.training.dao;

import com.oocl.training.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEmployeeTable extends JpaRepository<Employee,Integer> {


    List<Employee> getEmployeesByGender(String gender);
    List<Employee> getEmployeesByCompanyId(int companyId);
}

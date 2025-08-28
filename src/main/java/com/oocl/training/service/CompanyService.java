package com.oocl.training.service;

import com.oocl.training.dao.CompanyMemoryTable;
import com.oocl.training.dao.EmployeeMemoryTable;
import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyMemoryTable companyMemoryTable;
    private final EmployeeMemoryTable employeeMemoryTable;
    public CompanyService(CompanyMemoryTable companyMemoryTable, EmployeeMemoryTable employeeMemoryTable) {
        this.companyMemoryTable = companyMemoryTable;
        this.employeeMemoryTable = employeeMemoryTable;
    }

    public List<Company> getAllCompanies() {
        return companyMemoryTable.getCompanies();
    }

    public Company getCompanyById(int id) {
        return companyMemoryTable.getCompany(id);
    }

    public List<Employee> getEmployeesByCompany(int companyId) {
        return  employeeMemoryTable.getEmployee().stream()
                .filter(employee -> employee.getCompanyId() == companyId && employee.getActive())
                .toList();

    }

    public Company addCompany(Company company) {
        return companyMemoryTable.newCompany(company);
    }

    public Company updateCompanyById(int id, String name) {
        Company company_old = companyMemoryTable.getCompany(id);
        company_old.setName(name);
        return companyMemoryTable.updateCompany(id,company_old);
    }

    public Company updateCompanyById(int id,Company company) {
        Company company_old = companyMemoryTable.getCompany(id);
        company.setId(company_old.getId());
        return companyMemoryTable.updateCompany(id,company);
    }

    public void deleteCompanyById(int id) {
          for(int employeeId : employeeMemoryTable.employeeTable.keySet()){
              Employee employee = employeeMemoryTable.getEmployee(employeeId);
              if(employee.getCompanyId()==id && employee.getActive()){
                  employee.setActive(false);
                  employeeMemoryTable.updateEmployee(employeeId,employee);
              }
          }
          companyMemoryTable.remove(id);
    }

    public List<Company> getCompanyByPage(int page, int size) {
        List<Company> companies = companyMemoryTable.getCompanies();
        int start = (page - 1) * size;
        int end = Math.min(start + size, companies.size());
        List<Company> pageCompanies = companies.subList(start, end);
        return pageCompanies;
    }
}

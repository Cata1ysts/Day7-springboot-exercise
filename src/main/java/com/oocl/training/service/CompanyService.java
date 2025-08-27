package com.oocl.training.service;

import com.oocl.training.dao.CompanyTable;
import com.oocl.training.dao.EmployeeTable;
import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class CompanyService {
    private final CompanyTable companyTable;
    private final EmployeeTable employeeTable;
    public CompanyService(CompanyTable companyTable,EmployeeTable employeeTable) {
        this.companyTable = companyTable;
        this.employeeTable = employeeTable;
    }

    public List<Company> getAllCompanies() {
        return companyTable.getCompanies();
    }

    public Company getCompanyById(int id) {
        return companyTable.getCompany(id);
    }

    public List<Employee> getEmployeesByCompany(int companyId) {
        return  employeeTable.getEmployee().stream()
                .filter(employee -> employee.getCompanyId() == companyId && employee.getActive())
                .toList();

    }

    public Company addCompany(Company company) {
        return companyTable.newCompany(company);
    }

    public Company updateCompanyById(int id, String name) {
        Company company_old = companyTable.getCompany(id);
        company_old.setName(name);
        return companyTable.updateCompany(id,company_old);
    }

    public Company updateCompanyById(int id,Company company) {
        Company company_old = companyTable.getCompany(id);
        company.setId(company_old.getId());
        return companyTable.updateCompany(id,company);
    }

    public void deleteCompanyById(int id) {
          for(int employeeId : employeeTable.employeeTable.keySet()){
              Employee employee = employeeTable.getEmployee(employeeId);
              if(employee.getCompanyId()==id && employee.getActive()){
                  employee.setActive(false);
                  employeeTable.updateEmployee(employeeId,employee);
              }
          }
          companyTable.remove(id);
    }

    public List<Company> getCompanyByPage(int page, int size) {
        List<Company> companies = companyTable.getCompanies();
        int start = (page - 1) * size;
        int end = Math.min(start + size, companies.size());
        List<Company> pageCompanies = companies.subList(start, end);
        return pageCompanies;
    }
}

package com.oocl.training.dao;

import com.oocl.training.model.Company;

import java.util.List;

public class CompanyDBTable implements CompanyTable {

    private JpaCompanyTable jpaCompanyTable;
    public CompanyDBTable(JpaCompanyTable jpaCompanyTable){
        this.jpaCompanyTable=jpaCompanyTable;
    }

    @Override
    public Company newCompany(Company company) {
        return jpaCompanyTable.save(company);
    }

    @Override
    public Company updateCompany(int companyId, Company company) {
        company.setId(companyId);
        jpaCompanyTable.save(company);
        return company;
    }

    @Override
    public Company getCompany(int companyId) {
        return jpaCompanyTable.findById(companyId).orElse(null);
    }

    @Override
    public List<Company> getCompanies() {
        return jpaCompanyTable.findAll();
    }

    @Override
    public void remove(int companyId) {
        jpaCompanyTable.deleteById(companyId);
    }

    @Override
    public void drop() {
        jpaCompanyTable.deleteAll();
    }
}

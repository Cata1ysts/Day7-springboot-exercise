package com.oocl.training.dao;

import com.oocl.training.model.Company;

import java.util.List;

public interface CompanyTable {
    public Company updateCompany(int companyId, Company company);
    public Company getCompany(int companyId);
    public List<Company> getCompanies();
    public void remove(int companyId);
    public void drop();
}

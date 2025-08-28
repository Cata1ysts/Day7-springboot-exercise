package com.oocl.training.dao;

import com.oocl.training.model.Company;
import com.oocl.training.model.Company;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CompanyTable {
    public Map<Integer, Company> companyTable;
    private int companyIndex;
    public CompanyTable() {
//        this.companyTable = new HashMap<>(Map.of(
//                1, new Company(1, "Acme Corporation"),
//                2, new Company(2, "TechCom Solutions"),
//                3, new Company(3, "Global Innovators"),
//                4, new Company(4, "Stellar Enterprises"),
//                5, new Company(5, "Nexus Industries")
//        ));
        this.companyTable = new HashMap<>();
        this.companyIndex=0;
    }


    public Company newCompany(Company company){
        companyIndex++;
        company.setId(companyIndex);
        companyTable.put(companyIndex,company);
        return companyTable.get(companyIndex);
    }

    public Company updateCompany(int companyId,Company company){
        return companyTable.put(company.getId(),company);
    }

    public Company getCompany(int companyId){
        return companyTable.getOrDefault(companyId,null);
    }

    public List<Company> getCompanies(){
        return companyTable.values().stream().toList();
    }
    public void remove(int companyId) {
        companyTable.remove(companyId);
    }
    public void drop(){
        companyTable.clear();
        companyIndex=0;
    }
}

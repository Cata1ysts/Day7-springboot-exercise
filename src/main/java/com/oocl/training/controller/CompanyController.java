package com.oocl.training.controller;


import com.oocl.training.Util.DataBase;
import com.oocl.training.dao.Company;
import com.oocl.training.dao.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies/")
public class CompanyController {
    private DataBase db=new DataBase();

    @GetMapping
    public List<Company> getAllCompanies(){
        return new ArrayList<>(db.companyTable.values());

    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id){
        return db.companyTable.get(id);

    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int id){
        List<Integer> employees = db.companyemployeeTable.entrySet().stream()
                .filter(entry -> entry.getValue().equals(id))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return employees.stream()
                .map(db.employeeTable::get) // 根据 ID 获取 Employee
                .filter(employee -> employee != null) // 过滤掉不存在的 Employee
                .collect(Collectors.toList());


    }


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestBody Company company){
        int idx = db.companyIndexInc();
        company.setId(idx);
        db.companyTable.put(idx,company);
    }

    @PatchMapping("/{id}")
    public void updateCompanyNameById(@PathVariable int id, @RequestBody String name){
        Company company = db.companyTable.get(id);
        company.setName(name);
        db.companyTable.put(id,company);
    }

    @PutMapping("/{id}")
    public void updateCompanyById(@PathVariable int id, @RequestBody Company company){
        db.companyTable.put(id,company);
    }


    @DeleteMapping("/{id}")
    public void updateCompanyNameById(@PathVariable int id){
        db.companyTable.remove(id);
    }

    @GetMapping("/page")
    public List<Company> getCompaniesByPage(
            @RequestParam int page,
            @RequestParam int size) {
        List<Company> employees = new ArrayList<>(db.companyTable.values());
        int start = (page - 1) * size;
        int end = Math.min(start + size, employees.size());
        List<Company> pageConpanies = employees.subList(start, end);
        return pageConpanies;
    }

}

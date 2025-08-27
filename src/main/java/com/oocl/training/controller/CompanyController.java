package com.oocl.training.controller;


import com.oocl.training.model.Company;
import com.oocl.training.model.Employee;
import com.oocl.training.service.CompanyService;
import com.oocl.training.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies/")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();

    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.getCompanyById(id);

    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable int companyId) {
        return companyService.getEmployeesByCompany(companyId);


    }


    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PatchMapping("/{id}")
    public Company updateCompanyNameById(@PathVariable int id, @RequestBody String name) {
        return companyService.updateCompanyById(id, name);
    }

    @PutMapping("/{id}")
    public Company updateCompanyById(@PathVariable int id, @RequestBody Company company) {
        return companyService.updateCompanyById(id, company);
    }


    @DeleteMapping("/{id}")
    public void deleteCompanyNameById(@PathVariable int id) {
        companyService.deleteCompanyById(id);
    }

    @GetMapping("/page")
    public List<Company> getCompaniesByPage(
            @RequestParam int page,
            @RequestParam int size) {
        return companyService.getCompanyByPage(page, size);
    }

}

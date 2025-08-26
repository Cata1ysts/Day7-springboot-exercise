package com.oocl.training.controller;

import com.oocl.training.Util.DataBase;
import com.oocl.training.dao.Employee;
import com.oocl.training.dao.Employee;
import com.oocl.training.dao.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees/")
public class EmployeeController {
    private DataBase db = new DataBase();


    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return db.employeeTable.get(id);

    }

    @GetMapping("/")
    public List<Employee> getEmployeesByGender(@RequestParam(required = false) String gender) {
        if (gender != null && !gender.isEmpty()) {
            return db.employeeTable.values().stream()
                    .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                    .collect(Collectors.toList());
        }
        return db.employeeTable.values().stream().toList();
    }


    @PostMapping("/{companyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody Employee employee,@PathVariable int companyId) {
        int idx = db.employeeTable.size() + 1;
        employee.setId(idx);
        db.companyemployeeTable.put(companyId,idx);
        db.employeeTable.put(idx, employee);
    }


    @PutMapping("/{id}")
    public void updateEmployeeById(@PathVariable int id, @RequestParam Employee employee) {
        db.employeeTable.put(id, employee);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployeeNameById(@PathVariable int id) {
        db.employeeTable.remove(id);
    }

    @GetMapping("/page")
    public Page<Employee> getEmployeesByPage(
            @RequestParam int page,
            @RequestParam int size) {
        List<Employee> employees = new ArrayList<>(db.employeeTable.values());
        int start = (page - 1) * size;
        int end = Math.min(start + size, employees.size());
        List<Employee> pageEmployees = employees.subList(start, end);
        return new Page<>(page, size, employees.size(), pageEmployees);
    }
}

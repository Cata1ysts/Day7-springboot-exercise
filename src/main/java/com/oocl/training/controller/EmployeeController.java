package com.oocl.training.controller;

import com.oocl.training.Util.EmployeeEntity;
import com.oocl.training.model.Employee;
import com.oocl.training.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);

    }

    @GetMapping("/")
    public List<Employee> getEmployeesByGender(@RequestParam(required = false) String gender) {
        return employeeService.getEmployeeByGender(gender);
    }

    @PostMapping("/")
    //@ResponseStatus(HttpStatus.OK)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
}

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable int id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeNameById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/page")
    public List<Employee> getEmployeesByPage(
            @RequestParam int page,
            @RequestParam int size) {
        return employeeService.getEmployeeByPage(page,size);
    }
}

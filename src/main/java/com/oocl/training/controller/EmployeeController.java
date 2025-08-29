package com.oocl.training.controller;

import com.oocl.training.dto.EmployeeRequest;
import com.oocl.training.dto.EmployeeResponse;
import com.oocl.training.dto.mapper.EmployeeMapper;
import com.oocl.training.model.Employee;
import com.oocl.training.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    public EmployeeController(EmployeeService employeeService,EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }



    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable int id) {
        return employeeMapper.toResponse(employeeService.getEmployeeById(id));

    }

    @GetMapping("/")
    public List<EmployeeResponse> getEmployeesByGender(@RequestParam(required = false) String gender) {
        return employeeMapper.toResponse(employeeService.getEmployeeByGender(gender));
    }

    @PostMapping("/")
    //@ResponseStatus(HttpStatus.OK)
    public EmployeeResponse addEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toResponse(employeeService.addEmployee(employee));
}

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployeeById(@PathVariable int id, @Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        return employeeMapper.toResponse(employeeService.updateEmployee(id, employee));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeNameById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/page")
    public List<EmployeeResponse> getEmployeesByPage(
            @RequestParam int page,
            @RequestParam int size) {
        return employeeMapper.toResponse(employeeService.getEmployeeByPage(page,size));
    }
}

package com.oocl.training.dto.mapper;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.oocl.training.dto.EmployeeResponse;
import com.oocl.training.model.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class EmployeeMapper {
    public EmployeeResponse toResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee,employeeResponse);
        return employeeResponse;
    }

    public List<EmployeeResponse> toResponse(List<Employee> employees){
        return employees.stream().map(this::toResponse).toList();
    }
}

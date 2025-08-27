package com.oocl.training.service;

import com.oocl.training.Util.EmployeeEntity;
import com.oocl.training.dao.EmployeeTable;
import com.oocl.training.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeTable employeeTable;
    @Test
    void should_create_employee_successfully(){
        //Given
        Employee employee = new Employee(0, "1", "male", 10000, 20,1);
        Employee expection = new Employee(1, "1", "male", 10000, 20,1);
        Mockito.when(employeeTable.newEmployee(Mockito.any(Employee.class)))
                .thenReturn(employee);
        //When
        Employee result = employeeService.addEmployee(employee);
        assertEquals(expection.getAge(),result.getAge());
    }

    @Test
    void should_throw_exception_when_age_lt_18(){
        //Given
        Employee employee = new Employee(0, "1", "male", 10000, 10,1);
        Mockito.when(employeeTable.newEmployee(Mockito.any(Employee.class)))
                .thenReturn(employee);
        //When
        assertThrowsExactly(IllegalArgumentException.class,()->employeeService.addEmployee(employee));
    }

    @Test
    void should_throw_exception_when_age_gt_65(){
        //Given
        Employee employee = new Employee(0, "1", "male", 10000, 100,1);
        Mockito.when(employeeTable.newEmployee(Mockito.any(Employee.class)))
                .thenReturn(employee);
        //When
        assertThrowsExactly(IllegalArgumentException.class,()->employeeService.addEmployee(employee));
    }

    @Test
    void should_throw_exception_when_age_gt_30_and_salary_lt_20000(){
        //Given
        Employee employee = new Employee(0, "1", "male", 10000, 31,1);
        Mockito.when(employeeTable.newEmployee(Mockito.any(Employee.class)))
                .thenReturn(employee);
        //When
        assertThrowsExactly(IllegalArgumentException.class,()->employeeService.addEmployee(employee));
    }

    @Test
    void should_delete_successfully(){
        // Given
        int reqId = 1;
        Employee reqEmployee = new Employee(reqId, "John Smith", "male", 10000, 50,1);
        Mockito.when(employeeTable.updateEmployee(reqId,reqEmployee)).thenReturn(reqEmployee);
        Mockito.when(employeeTable.getEmployee(reqId)).thenReturn(reqEmployee);

        // When
        employeeService.deleteEmployeeById(reqId);

        // Then
        assertFalse(reqEmployee.getActive());
        Mockito.verify(employeeTable, Mockito.times(1)).getEmployee(reqId);
        Mockito.verify(employeeTable, Mockito.times(1)).updateEmployee(reqId, reqEmployee);
    }
    @Test
    void should_select_employee_by_id_successfully(){
        //Given
        int eid=1;
        Employee employee = new Employee(1, "1", "male", 10000, 20,1);
        Employee expection = new Employee(1, "1", "male", 10000, 20,1);
        Mockito.when(employeeTable.getEmployee(eid)).thenReturn(employee);
        //When
        Employee result = employeeService.getEmployeeById(eid);
        assertEquals(expection.getActive(),result.getActive());
        assertEquals(expection.getId(),result.getId());
        assertEquals(expection.getGender(),result.getGender());
        assertEquals(expection.getSalary(),result.getSalary());
    }

    @Test
    void should_select_employee_by_gender_successfully(){
        //Given
        String gender="male";
        List<Employee> employees_get = List.of(new Employee(1, "1", "male", 10000, 20,1),
                new Employee(1, "2", "female", 10000, 20,1),
                new Employee(1, "3", "male", 10000, 20,1));
        List<Employee> expection = List.of(new Employee(1, "1", "male", 10000, 20, 1),
                new Employee(1, "3", "male", 10000, 20,1));
        Mockito.when(employeeTable.getEmployee(gender)).thenReturn(employees_get);
        //When
        List<Employee> result = employeeService.getEmployeeByGender(gender);
        assertEquals(expection.size(),result.size());
        for(int i=0;i<result.size();i++){
            assertEquals(expection.get(i).getSalary(),result.get(i).getSalary());
            assertEquals(expection.get(i).getId(),result.get(i).getId());
            assertEquals(expection.get(i).getGender(),result.get(i).getGender());
            assertEquals(expection.get(i).getAge(),result.get(i).getAge());
        }
    }

}
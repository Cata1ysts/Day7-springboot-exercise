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
        Employee employee_created = new Employee(1, "1", "male", 10000, 20,1);
        Employee expection = new Employee(1, "1", "male", 10000, 20,1);
        Mockito.when(employeeTable.newEmployee(Mockito.any(Employee.class)))
                .thenReturn(employee_created);
        //When
        Employee result = employeeService.addEmployee(employee);
        compareEmployee(expection,result);
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
        compareEmployee(expection,result);
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
            compareEmployee(expection.get(i),result.get(i));
        }
    }
    @Test
    void should_update_employee_by_id_successfully(){
        //Given
        int eid=1;
        List<Employee> employees_get = List.of(new Employee(1, "1", "male", 10000, 20,1),
                new Employee(2, "2", "female", 10000, 20,1),
                new Employee(3, "3", "male", 10000, 20,1));
        Employee expection = new Employee(1, "1", "female", 10000, 20,1);
        Employee testdata = new Employee(1, "1", "female", 10000, 20,1);
        Mockito.when(employeeTable.getEmployee(eid)).thenReturn(employees_get.get(0));
        Mockito.when(employeeTable.updateEmployee(eid,testdata)).thenReturn(testdata);
        //When
        Employee result = employeeService.updateEmployee(eid,testdata);
        compareEmployee(expection, result);
    }

    @Test
    void should_return_page_successfully(){
        //Given
        int page =2;
        int size = 2;
        List<Employee> employees_get = List.of(new Employee(1, "1", "male", 10000, 20,1),
                new Employee(2, "2", "female", 10000, 20,1),
                new Employee(3, "3", "male", 10000, 20,1),
                new Employee(4, "1", "male", 10000, 20,1),
                new Employee(5, "2", "female", 10000, 20,1),
                new Employee(6, "3", "male", 10000, 20,1));


        List<Employee> expection = List.of(
                new Employee(3, "3", "male", 10000, 20,1),
                new Employee(4, "1", "male", 10000, 20,1)
        );
        Employee testdata = new Employee(1, "1", "female", 10000, 20,1);
        Mockito.when(employeeTable.getEmployee()).thenReturn(employees_get);
        //When
        List<Employee> result = employeeService.getEmployeeByPage(page,size);
        assertEquals(expection.size(),result.size());
        for(int i=0;i<result.size();i++){
            compareEmployee(expection.get(i),result.get(i));
        }
    }


    private static void compareEmployee(Employee expection, Employee result) {
        assertEquals(expection.getActive(), result.getActive());
        assertEquals(expection.getId(), result.getId());
        assertEquals(expection.getGender(), result.getGender());
        assertEquals(expection.getSalary(), result.getSalary());
        assertEquals(expection.getCompanyId(), result.getCompanyId());
        assertEquals(expection.getName(), result.getName());
        assertEquals(expection.getActive(), result.getActive());
    }

}
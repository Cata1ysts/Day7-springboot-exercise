package com.oocl.training.Integration;


import com.oocl.training.dao.EmployeeTable;
import com.oocl.training.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc client;
    @Autowired
    private EmployeeTable employeeTable;

    @BeforeEach
    public void setUp(){
        employeeTable.drop();
        employeeTable.newEmployee(new Employee(1,"1","male",10000,20,1));
        employeeTable.newEmployee(new Employee(2,"2","female",1000,20,1));
        employeeTable.newEmployee(new Employee(3,"1","female",1000,20,1));
        employeeTable.newEmployee(new Employee(4,"2","male",1000,20,1));
    }

    @Test
    public void should_return_employees_when_get_all() throws Exception {
        //given
        List<Employee> expection = employeeTable.getEmployee();
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees/"));
        //then
        for(int i=0;i<expection.size();i++){
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.[%s].id",i).value(expection.get(i).getId()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].gender",i)).value(expection.get(i).getGender()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].age",i)).value(expection.get(i).getAge()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].companyId",i)).value(expection.get(i).getCompanyId()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].salary",i)).value(expection.get(i).getSalary()));
        }
    }

    @Test
    public void should_return_employees_when_get_id_eq_2() throws Exception {
        //given
        Employee expection = employeeTable.getEmployee(2);
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees/2"));
        //then

        compareEmployee(expection,perform);
    }

    private static void compareEmployee(Employee expection,ResultActions perform) throws Exception {
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expection.getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.gender")).value(expection.getGender()));
        perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.age")).value(expection.getAge()));
        perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.companyId")).value(expection.getCompanyId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.salary")).value(expection.getSalary()));
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

    @Test
    public void should_return_male_employees_when_get_employee_by_gender() throws Exception {
        //given
        List<Employee> expection = employeeTable.getEmployee("male");
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees/?gender=male"));
        //then
        for(int i=0;i<expection.size();i++){
            perform.andExpect(MockMvcResultMatchers.jsonPath("$.[%s].id",i).value(expection.get(i).getId()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].gender",i)).value(expection.get(i).getGender()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].age",i)).value(expection.get(i).getAge()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].companyId",i)).value(expection.get(i).getCompanyId()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].salary",i)).value(expection.get(i).getSalary()));
        }
    }

    @Test
    public void should_return_updated_employees_when_update_employee() throws Exception {
        //given
        Employee expection = employeeTable.getEmployee(3);
        expection.setSalary(12345);
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.put("/employees/3",expection));
        Employee result = employeeTable.getEmployee(3);
        //then
        compareEmployee(expection,result);
    }


    @Test
    public void should_return_no_employees_when_delete_employee() throws Exception {
        //given
        int eid =3;
        Employee exception = employeeTable.getEmployee(eid);
        exception.setActive(false);
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.delete(String.format("/employees/%d",eid)));
        Employee after_delete = employeeTable.getEmployee(eid);
        //then
        compareEmployee(exception,after_delete);

    }

}

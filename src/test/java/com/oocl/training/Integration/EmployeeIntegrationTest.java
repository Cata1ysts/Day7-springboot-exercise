package com.oocl.training.Integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.training.dao.EmployeeDBTable;
import com.oocl.training.model.Employee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc client;
    @Autowired
    private  EmployeeDBTable employeeDBTable;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setUp(){
        employeeDBTable.drop();
        employeeDBTable.newEmployee(new Employee(1,"1","male",10000,20,1));
        employeeDBTable.newEmployee(new Employee(2,"2","female",1000,20,1));
        employeeDBTable.newEmployee(new Employee(3,"1","female",1000,20,1));
        employeeDBTable.newEmployee(new Employee(4,"2","male",1000,20,1));
    }

    @Test
    public void should_return_employees_when_get_all() throws Exception {
        //given
        List<Employee> expection = employeeDBTable.getEmployee();
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees/"));
        //then
        compareEmployee(expection,perform);
    }

    @Test
    public void should_return_employees_when_get_by_id() throws Exception {
        //given
        Employee expection = employeeDBTable.newEmployee(new Employee(0,"delete","male",100000,30,1));
        int eid = expection.getId();
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get(String.format("/employees/%d",eid)));
        //then

        compareEmployee(expection,perform);
    }

    private static void compareEmployee(Employee expection,ResultActions perform) throws Exception {
        //perform.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expection.getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.gender")).value(expection.getGender()));
        perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.age")).value(expection.getAge()));
        //perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.companyId")).value(expection.getCompanyId()));
        //perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.salary")).value(expection.getSalary()));
    }

    private static void compareEmployee(Employee expection, Employee result) {
        assertEquals(expection.getActive(), result.getActive());
        //assertEquals(expection.getId(), result.getId());
        assertEquals(expection.getGender(), result.getGender());
        //assertEquals(expection.getSalary(), result.getSalary());
        //assertEquals(expection.getCompanyId(), result.getCompanyId());
        assertEquals(expection.getName(), result.getName());
        assertEquals(expection.getActive(), result.getActive());
    }

    @Test
    public void should_return_male_employees_when_get_employee_by_gender() throws Exception {
        //given
        List<Employee> expection = employeeDBTable.getEmployee("male").stream().
                filter(employee -> (employee.getActive())).toList();
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees/?gender=male"));
        //then
        compareEmployee(expection, perform);
    }

    private static void compareEmployee(List<Employee> expection, ResultActions perform) throws Exception {
        for(int i = 0; i< expection.size(); i++){
            //perform.andExpect(MockMvcResultMatchers.jsonPath("$.[%s].id",i).value(expection.get(i).getId()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].gender",i)).value(expection.get(i).getGender()));
            perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].age",i)).value(expection.get(i).getAge()));
            //perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].companyId",i)).value(expection.get(i).getCompanyId()));
            //perform.andExpect(MockMvcResultMatchers.jsonPath(String.format("$.[%d].salary",i)).value(expection.get(i).getSalary()));
        }
    }

    @Test
    public void should_return_updated_employees_when_update_employee() throws Exception {

        //given
        Employee expection = employeeDBTable.newEmployee(new Employee(0,"update","male",100000,30,1));
        int eid = expection.getId();
        expection.setAge(24);
        String employeeJson = objectMapper.writeValueAsString(expection);
        //when

        // When
        ResultActions perform = client.perform(MockMvcRequestBuilders.put(String.format("/employees/%d",eid))
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));
        //then
        compareEmployee(expection,perform);
    }



    @Test
    public void should_return_employees_when_delete_employee() throws Exception {
        //given
        Employee expection = employeeDBTable.newEmployee(new Employee(0,"delete","male",100000,30,1));
        int eid = expection.getId();
        expection.setActive(false);
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.delete(String.format("/employees/%d",eid)));
        Employee after_delete = employeeDBTable.getEmployee(eid);
        //then
        compareEmployee(expection,after_delete);

    }

    @Test
    public void should_return_page_when_get_employee_by_page() throws Exception {
        //given
        List<Employee> expection = List.of(
                new Employee(3,"1","female",1000,20,1),
                new Employee(4,"2","male",1000,20,1)
        );
        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get(String.format("/employees/page?page=2&size=2")));
        //then
        compareEmployee(expection,perform);

    }

    @Test
    public void should_return_new_employee_when_post() throws Exception {
        //given
        Employee new_employee = new Employee(0, "test", "male", 10000, 20, 1);
        String employeeJson = objectMapper.writeValueAsString(new_employee);

        //when
        ResultActions perform = client.perform(MockMvcRequestBuilders.post("/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));
        //then
        compareEmployee(new_employee,perform);

    }


}

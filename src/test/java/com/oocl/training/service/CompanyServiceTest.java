package com.oocl.training.service;

import com.oocl.training.dao.CompanyDBTable;
import com.oocl.training.dao.CompanyDBTable;
import com.oocl.training.dao.EmployeeDBTable;
import com.oocl.training.model.Company;
import com.oocl.training.model.Company;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
class CompanyServiceTest {
    @InjectMocks
    private CompanyService companyService;
    @Mock
    private EmployeeDBTable employeeTable;
    @Mock
    private CompanyDBTable companyTable;
    @Test
    void should_create_company_successfully(){
        //Given
        Company company_new = new Company(1, "TEST");
        Company company_created = company_new;
        Company expection = new Company(1, "TEST");
        Mockito.when(companyTable.newCompany(any(Company.class)))
                .thenReturn(company_created);
        //When
        Company result = companyTable.newCompany(company_new);
        compareCompany(expection,result);
    }
    

    @Test
    void should_select_company_by_id_successfully(){
        //Given
        int eid=1;
        Company company = new Company(1, "test1");
        Company expection = new Company(1, "test1");
        Mockito.when(companyTable.getCompany(eid)).thenReturn(company);
        //When
        Company result = companyService.getCompanyById(eid);
        compareCompany(expection,result);
    }


    @Test
    void should_select_all_company_successfully(){
        //Given
        List<Company> companys_get = List.of(new Company(1, "1"),
                new Company(2, "2"),
                new Company(3, "3"));
        List<Company> expection = List.of(new Company(1, "1"),
                new Company(2, "2"),
                new Company(3, "3"));
        Mockito.when(companyTable.getCompanies()).thenReturn(companys_get);
        //When
        List<Company> result = companyService.getAllCompanies();
        assertEquals(expection.size(),result.size());
        for(int i=0;i<result.size();i++){
            compareCompany(expection.get(i),result.get(i));
        }
    }
    @Test
    void should_update_company_by_id_successfully(){
        //Given
        int eid=1;
        List<Company> companys_get = List.of(new Company(1, "1"),
                new Company(2, "2"),
                new Company(3, "3"));
        Company expection = new Company(1, "100");
        Company testdata = new Company(1, "100");
        Mockito.when(companyTable.getCompany(eid)).thenReturn(companys_get.get(0));
        Mockito.when(companyTable.updateCompany(eid,testdata)).thenReturn(testdata);
        //When
        Company result = companyService.updateCompanyById(eid,testdata);
        compareCompany(expection, result);
    }

    @Test
    void should_update_company_name_by_id_successfully(){
        //Given
        int eid=1;
        String name = "100";
        List<Company> companys_get = List.of(new Company(1, "1"),
                new Company(2, "2"),
                new Company(3, "3"));
        Company expection = new Company(1, "100");
        Company testdata = new Company(1, "100");
        Mockito.when(companyTable.getCompany(eid)).thenReturn(companys_get.get(eid-1));
       // Mockito.when(companyTable.getCompany(eid)).thenReturn(testdata);
        Mockito.when(companyTable.updateCompany(eq(eid),any(Company.class))).thenReturn(testdata);
        //When
        Company result = companyService.updateCompanyById(eid,name);
        compareCompany(expection, result);
    }

    @Test
    void should_return_page_successfully(){
        //Given
        int page =2;
        int size = 2;
        List<Company> companys_get = List.of(new Company(1, "1"),
                new Company(2, "2"),
                new Company(3, "3"),
                new Company(4, "4"),
                new Company(5, "5"),
                new Company(6, "6"));


        List<Company> expection = List.of(
                new Company(3, "3"),
                new Company(4, "4")
        );
        Company testdata = new Company(1, "1");
        Mockito.when(companyTable.getCompanies()).thenReturn(companys_get);
        //When
        List<Company> result = companyService.getCompanyByPage(page,size);
        assertEquals(expection.size(),result.size());
        for(int i=0;i<result.size();i++){
            compareCompany(expection.get(i),result.get(i));
        }
    }


    private static void compareCompany(Company expection, Company result) {
        assertEquals(expection.getId(), result.getId());
        assertEquals(expection.getName(), result.getName());
    }

}
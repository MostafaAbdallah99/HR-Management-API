//package gov.iti.hr.restcontrollers.resources.employees;
//
//import gov.iti.hr.models.EmployeeDTO;
//import gov.iti.hr.persistence.entities.enums.Gender;
//import jakarta.ws.rs.client.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class EmployeesResourceServiceTest {
//
//    @Test
//    void addEmployee() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/employees");
//        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setFirstName("Test");
//        employeeDTO.setLastName("Test");
//        employeeDTO.setEmail("k@gmail.com");
//        employeeDTO.setPhoneNumber("+201010101010");
//        employeeDTO.setHireDate(LocalDate.parse("2021-09-01"));
//        employeeDTO.setJobName("TestJob");
//        employeeDTO.setSalary(BigDecimal.valueOf(1000.0));
//        employeeDTO.setDepartmentName("awfawf");
//        employeeDTO.setManagerEmail("mostafaabdalla000@gmail.com");
//        employeeDTO.setGender(Gender.MALE);
//        employeeDTO.setVacationBalance(21);
//
//
//        Entity<EmployeeDTO> employeeEntity = Entity.entity(employeeDTO, MediaType.APPLICATION_JSON);
//        Response response = builder.post(employeeEntity);
//
//        //Assert
//        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//    @Test
//    void testGetAllEmployees() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/employees");
//        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        Response response = builder.get();
//
//        //Assert
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//    @Test
//    void testGetEmployeeById() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/employees/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 1);
//        Invocation.Builder builder = resolvedTarget.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        Response response = builder.get();
//
//        //Assert
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//
//        client.close();
//    }
//
//
//    @Test
//    void updateEmployee() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/employees/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 1);
//        Invocation.Builder builder = resolvedTarget.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setEmployeeId(1);
//        employeeDTO.setFirstName("Test");
//        employeeDTO.setLastName("Test");
//        employeeDTO.setEmail("22k@gmail.com");
//        employeeDTO.setPhoneNumber("+201210101010");
//        employeeDTO.setHireDate(LocalDate.parse("2021-09-01"));
//        employeeDTO.setJobName("TestJob");
//        employeeDTO.setSalary(BigDecimal.valueOf(15000.0));
//        employeeDTO.setDepartmentName("awfawf");
//        employeeDTO.setManagerEmail("mostafaabdalla000@gmail.com");
//        employeeDTO.setGender(Gender.MALE);
//        employeeDTO.setVacationBalance(21);
//
//
//        Entity<EmployeeDTO> employeeEntity = Entity.entity(employeeDTO, MediaType.APPLICATION_JSON);
//        Response response = builder.put(employeeEntity);
//
//        //Assert
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//    @Test
//    void deleteEmployee() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/employees/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 1);
//        Invocation.Builder builder = resolvedTarget.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        Response response = builder.delete();
//
//        //Assert
//        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//        client.close();
//    }
//}

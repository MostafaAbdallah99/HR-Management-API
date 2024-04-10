//package gov.iti.hr.restcontrollers.resources.departments;
//
//import gov.iti.hr.models.DepartmentDTO;
//import jakarta.ws.rs.client.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class DepartmentResourceServiceTest {
//
//    @Test
//    void testGetAllDepartments() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/departments");
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
//    void testGetDepartmentById() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/departments/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 3);
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
//    @Test
//    void addDepartment() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/departments");
//        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        DepartmentDTO departmentDTO = new DepartmentDTO(null, "Test Department", null, null);
//        Entity<DepartmentDTO> departmentEntity = Entity.entity(departmentDTO, MediaType.APPLICATION_JSON);
//        Response response = builder.post(departmentEntity);
//
//        //Assert
//        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//    @Test
//    void updateDepartment() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/departments/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 9);
//        Invocation.Builder builder = resolvedTarget.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        DepartmentDTO departmentDTO = new DepartmentDTO(9, "Test Department55", 11, null);
//        Entity<DepartmentDTO> departmentEntity = Entity.entity(departmentDTO, MediaType.APPLICATION_JSON);
//        Response response = builder.put(departmentEntity);
//
//        //Assert
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//    @Test
//    void deleteDepartment() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/departments/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 9);
//        Invocation.Builder builder = resolvedTarget.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        Response response = builder.delete();
//
//        //Assert
//        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//}

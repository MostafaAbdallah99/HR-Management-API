//package gov.iti.hr.restcontrollers.resources.jobs;
//
//import gov.iti.hr.models.JobDTO;
//import jakarta.ws.rs.client.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class JobResourceServiceTest {
//
//    @Test
//    void testGetAllJobs() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/jobs");
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
//    void testGetJobById() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/jobs/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 2);
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
//    void addJob() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/jobs");
//        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        JobDTO jobDTO = new JobDTO(null, "TestJob", 1000, 3000);
//        Entity<JobDTO> jobEntity = Entity.entity(jobDTO, MediaType.APPLICATION_JSON);
//        Response response = builder.post(jobEntity);
//
//        //Assert
//        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//    @Test
//    void updateJob() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/jobs/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 5);
//        Invocation.Builder builder = resolvedTarget.request(MediaType.APPLICATION_JSON);
//
//        // Act
//        JobDTO jobDTO = new JobDTO(4, "Test r", 1000, 3000);
//        Entity<JobDTO> jobEntity = Entity.entity(jobDTO, MediaType.APPLICATION_JSON);
//        Response response = builder.put(jobEntity);
//
//        //Assert
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        client.close();
//    }
//
//    @Test
//    void deleteJob() {
//        // Arrange
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:9090/HR_Management_API/rest/jobs/{id}");
//        WebTarget resolvedTarget = target.resolveTemplate("id", 5);
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

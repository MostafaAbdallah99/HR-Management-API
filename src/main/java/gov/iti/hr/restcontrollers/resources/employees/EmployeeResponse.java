package gov.iti.hr.restcontrollers.resources.employees;

import gov.iti.hr.restcontrollers.adapter.JsonbSingleLinkAdapter;
import gov.iti.hr.restcontrollers.resources.departments.DepartmentResponse;
import gov.iti.hr.restcontrollers.resources.jobs.JobResponse;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "employee")
@XmlType(propOrder = {"employeeId", "firstName", "lastName", "email", "phoneNumber", "hireDate", "jobTitle", "salary", "vacationBalance", "managerName", "departmentName", "links"})
@JsonbPropertyOrder({"employeeId", "firstName", "lastName", "email", "phoneNumber", "hireDate", "jobTitle", "salary", "vacationBalance", "managerName", "departmentName", "links"})
@Setter
@ToString
@NoArgsConstructor
public class EmployeeResponse implements Serializable {
    @Getter
    private Integer employeeId;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String email;
    @Getter
    private String phoneNumber;
    @Getter
    private String hireDate;
    @Getter
    private JobResponse jobResponse;
    @Getter
    private BigDecimal salary;
    @Getter
    private Integer vacationBalance;

    @Getter
    private DepartmentResponse departmentResponse;
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;





}

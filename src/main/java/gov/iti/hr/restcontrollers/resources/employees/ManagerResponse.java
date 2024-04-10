package gov.iti.hr.restcontrollers.resources.employees;


import gov.iti.hr.adapter.JsonbSingleLinkAdapter;
import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.adapter.JaxbLinkAdapter;
import gov.iti.hr.restcontrollers.resources.departments.DepartmentResponse;
import gov.iti.hr.restcontrollers.resources.jobs.JobResponse;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "employee")
@XmlType(propOrder = {"employeeId", "firstName", "lastName", "email", "phoneNumber", "hireDate", "jobTitle", "salary", "vacationBalance", "departmentName", "link"})
@JsonbPropertyOrder({"employeeId", "firstName", "lastName", "email", "phoneNumber", "hireDate", "jobTitle", "salary", "vacationBalance", "departmentName", "link"})
@Setter
@ToString
@NoArgsConstructor
public class ManagerResponse implements Serializable {
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
    private LocalDate hireDate;
    @Getter
    private BigDecimal salary;
    @Getter
    private Integer vacationBalance;
    @Getter
    private JobResponse jobResponse;
    @Getter
    private DepartmentResponse departmentResponse;
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;


    public ManagerResponse(ManagerDTO managerDTO) {
        if (managerDTO == null) {
            return;
        }
        this.employeeId = managerDTO.getEmployeeId();
        this.firstName = managerDTO.getFirstName();
        this.lastName = managerDTO.getLastName();
        this.email = managerDTO.getEmail();
        this.phoneNumber = managerDTO.getPhoneNumber();
        this.hireDate = managerDTO.getHireDate();
        this.salary = managerDTO.getSalary();
        this.vacationBalance = managerDTO.getVacationBalance();
        this.jobResponse = new JobResponse(managerDTO.getJob());
        this.link = null;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public Link getLink() {
        return link;
    }

}

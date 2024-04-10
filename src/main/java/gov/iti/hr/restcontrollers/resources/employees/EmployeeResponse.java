package gov.iti.hr.restcontrollers.resources.employees;

import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.adapter.JaxbLinkAdapter;
import gov.iti.hr.adapter.JsonbSingleLinkAdapter;
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
    private LocalDate hireDate;
    @Getter
    private JobResponse jobResponse;
    @Getter
    private BigDecimal salary;
    @Getter
    private Integer vacationBalance;
    @Getter
    private DepartmentResponse departmentResponse;
    @Getter
    private String managerName;
    @Getter
    private String departmentName;
    @Getter
    private String jobTitle;
    @Getter
    private ManagerResponse managerResponse;
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;

    public EmployeeResponse(EmployeeDTO employeeDTO) {
        this.employeeId = employeeDTO.getEmployeeId();
        this.firstName = employeeDTO.getFirstName();
        this.lastName = employeeDTO.getLastName();
        this.email = employeeDTO.getEmail();
        this.phoneNumber = employeeDTO.getPhoneNumber();
        this.hireDate = employeeDTO.getHireDate();
        this.jobTitle = employeeDTO.getJobName();
        this.departmentName = employeeDTO.getDepartmentName();
        this.managerName = employeeDTO.getManagerEmail();
        this.salary = employeeDTO.getSalary();
        this.vacationBalance = employeeDTO.getVacationBalance();
        this.link = null;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public Link getLink() {
        return link;
    }

}

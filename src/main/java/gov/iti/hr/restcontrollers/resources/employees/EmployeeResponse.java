package gov.iti.hr.restcontrollers.resources.employees;

import gov.iti.hr.models.EmployeeDTO;
import gov.iti.hr.restcontrollers.adapter.JaxbLinkAdapter;
import gov.iti.hr.restcontrollers.adapter.JsonbSingleLinkAdapter;
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
    private ManagerResponse managerResponse;
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;

    public EmployeeResponse(EmployeeDTO employeeDTO) {
        this.employeeId = employeeDTO.employeeId();
        this.firstName = employeeDTO.firstName();
        this.lastName = employeeDTO.lastName();
        this.email = employeeDTO.email();
        this.phoneNumber = employeeDTO.phoneNumber();
        this.hireDate = employeeDTO.hireDate();
        this.jobResponse = new JobResponse(employeeDTO.job());
        this.salary = employeeDTO.salary();
        this.vacationBalance = employeeDTO.vacationBalance();
        this.departmentResponse = new DepartmentResponse(employeeDTO.department());
        this.managerResponse = new ManagerResponse(employeeDTO.manager());
        this.link = null;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public Link getLink() {
        return link;
    }

}

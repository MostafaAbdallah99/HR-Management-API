package gov.iti.hr.restcontrollers.resources.employees;

import gov.iti.hr.models.ManagerDTO;
import gov.iti.hr.restcontrollers.adapter.JaxbLinkAdapter;
import gov.iti.hr.restcontrollers.adapter.JsonbSingleLinkAdapter;
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
import java.util.Date;

@XmlRootElement(name = "employee")
@XmlType(propOrder = {"employeeId", "firstName", "lastName", "email", "phoneNumber", "hireDate", "jobTitle", "salary", "vacationBalance", "departmentName", "links"})
@JsonbPropertyOrder({"employeeId", "firstName", "lastName", "email", "phoneNumber", "hireDate", "jobTitle", "salary", "vacationBalance", "departmentName", "links"})
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
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;


    public ManagerResponse(ManagerDTO managerDTO) {
        this.employeeId = managerDTO.managerId();
        this.firstName = managerDTO.firstName();
        this.lastName = managerDTO.lastName();
        this.email = managerDTO.email();
        this.phoneNumber = managerDTO.phoneNumber();
        this.hireDate = managerDTO.hireDate();
        this.salary = managerDTO.salary();
        this.vacationBalance = managerDTO.vacationBalance();
        this.link = null;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public Link getLink() {
        return link;
    }

}

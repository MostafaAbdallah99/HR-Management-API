package gov.iti.hr.restcontrollers.resources.departments;

import gov.iti.hr.adapter.JsonbSingleLinkAdapter;
import gov.iti.hr.models.DepartmentDTO;
import gov.iti.hr.adapter.JaxbLinkAdapter;
import gov.iti.hr.restcontrollers.resources.employees.ManagerResponse;
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

@XmlRootElement(name = "department")
@XmlType(propOrder = {"departmentId", "departmentName", "managerName", "link"})
@JsonbPropertyOrder({"departmentId", "departmentName", "managerName", "link"})
@Setter
@NoArgsConstructor
@ToString
public class DepartmentResponse {
    @Getter
    private Integer departmentId;
    @Getter
    private String departmentName;
    @Getter
    private Integer managerId;
    @Getter
    private ManagerResponse manager;
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;

    public DepartmentResponse(DepartmentDTO departmentDTO) {
        this.departmentId = departmentDTO.getDepartmentId();
        this.departmentName = departmentDTO.getDepartmentName();
        this.managerId = departmentDTO.getManagerId();
        this.link = null;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public Link getLink() {
        return link;
    }
}

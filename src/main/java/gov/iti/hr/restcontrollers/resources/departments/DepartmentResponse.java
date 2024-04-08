package gov.iti.hr.restcontrollers.resources.departments;

import gov.iti.hr.models.DepartmentDTO;
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

@XmlRootElement(name = "department")
@XmlType(propOrder = {"departmentId", "departmentName", "managerName"})
@JsonbPropertyOrder({"departmentId", "departmentName", "managerName"})
@Setter
@NoArgsConstructor
@ToString
public class DepartmentResponse {
    @Getter
    private Integer departmentId;
    @Getter
    private String departmentName;
    @Getter
    private String managerName;
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;

    public DepartmentResponse(DepartmentDTO departmentDTO) {
        this.departmentId = departmentDTO.departmentId();
        this.departmentName = departmentDTO.departmentName();
        //this.managerName = departmentDTO.managerName();
        this.link = null;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public Link getLink() {
        return link;
    }
}

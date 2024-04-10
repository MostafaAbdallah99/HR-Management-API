package gov.iti.hr.restcontrollers.resources.departments;

import gov.iti.hr.adapter.JaxbLinkAdapter;
import gov.iti.hr.adapter.JsonbLinksAdapter;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "departments")
@XmlType(propOrder = {"departmentResponses", "links"})
@JsonbPropertyOrder({"departmentResponses", "links"})
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class DepartmentPaginationResponse {
    @Getter
    private List<DepartmentResponse> departmentResponses;
    @JsonbTypeAdapter(JsonbLinksAdapter.class)
    private List<Link> links;

    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public List<Link> getLinks() {
        return links;
    }
}

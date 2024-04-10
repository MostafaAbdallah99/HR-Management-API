package gov.iti.hr.restcontrollers.resources.jobs;

import gov.iti.hr.models.JobDTO;
import gov.iti.hr.adapter.JaxbLinkAdapter;
import gov.iti.hr.adapter.JsonbSingleLinkAdapter;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@XmlRootElement(name = "job")
@XmlType(propOrder = {"jobId", "jobTitle", "minSalary", "maxSalary", "links"})
@JsonbPropertyOrder({"jobId", "jobTitle", "minSalary", "maxSalary", "links"})
@Setter
@ToString
@NoArgsConstructor
public class JobResponse implements Serializable {
    @Getter
    private Integer jobId;
    @Getter
    private String jobTitle;
    @Getter
    private Integer minSalary;
    @Getter
    private Integer maxSalary;
    @JsonbTypeAdapter(JsonbSingleLinkAdapter.class)
    private Link link;

    public JobResponse(JobDTO jobDTO) {
        this.jobId = jobDTO.getJobId();
        this.jobTitle = jobDTO.getJobTitle();
        this.minSalary = jobDTO.getMinSalary();
        this.maxSalary = jobDTO.getMaxSalary();
        this.link = null;
    }

    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(JaxbLinkAdapter.class)
    public Link getLink() {
        return link;
    }
}

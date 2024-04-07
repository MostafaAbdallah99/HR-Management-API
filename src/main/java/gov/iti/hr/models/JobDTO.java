package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job")
public record JobDTO (
        Integer jobId,
        @NotEmpty(message = "You must provide Job Title") String jobTitle,
        @Min(message = "You must provide zero or positive minimum salary", value = 0)
        @NotNull(message = "You must provide minimum salary")
        Integer minSalary,
        @Min(message = "You must provide zero or positive maximum salary", value = 0)
        @NotNull(message = "You must provide maximum salary")
        Integer maxSalary
) implements DTO {
        @Override
        public Integer getDTOId() {
                return jobId();
        }
}

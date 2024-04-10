package gov.iti.hr.models;

import gov.iti.hr.models.dto.DTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "job")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobDTO implements DTO {

        private Integer jobId;

        @NotEmpty(message = "You must provide Job Title")
        private String jobTitle;

        @Min(message = "You must provide zero or positive minimum salary", value = 0)
        @NotNull(message = "You must provide minimum salary")
        private Integer minSalary;

        @Min(message = "You must provide zero or positive maximum salary", value = 0)
        @NotNull(message = "You must provide maximum salary")
        private Integer maxSalary;


        @Override
        public Integer getDTOId() {
                return getJobId();
        }
}
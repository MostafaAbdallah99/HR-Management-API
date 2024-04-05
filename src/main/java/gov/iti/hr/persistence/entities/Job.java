package gov.iti.hr.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "jobs")
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private Integer jobId;

    @Column(name = "job_title", nullable = false, unique = true, length = 35)
    private String jobTitle;

    @Column(name = "min_salary", nullable = false)
    private Integer minSalary;

    @Column(name = "max_salary", nullable = false)
    private Integer maxSalary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return jobId != null && jobId.equals(job.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, jobTitle, minSalary, maxSalary);
    }
}

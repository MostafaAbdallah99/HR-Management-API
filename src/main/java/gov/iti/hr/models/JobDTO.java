package gov.iti.hr.models;

public record JobDTO (
        Integer jobId,
        String jobTitle,
        Integer minSalary,
        Integer maxSalary
){}

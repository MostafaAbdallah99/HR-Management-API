package gov.iti.hr.mappers;

import gov.iti.hr.models.JobDTO;
import gov.iti.hr.persistence.entities.Job;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);
    JobDTO jobToJobDTO(Job job);
    Job jobDTOToJob(JobDTO jobDTO);
}

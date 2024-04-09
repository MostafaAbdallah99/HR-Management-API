package gov.iti.hr.persistence.repository.repositories;

import gov.iti.hr.persistence.entities.EmployeeVacation;
import gov.iti.hr.persistence.repository.generic.GenericRepositoryImpl;
import gov.iti.hr.persistence.repository.interfaces.EmployeeVacationRepository;

public class EmployeeVacationRepositoryImpl extends GenericRepositoryImpl<EmployeeVacation, Integer> implements EmployeeVacationRepository {
    public EmployeeVacationRepositoryImpl() {
        super(EmployeeVacation.class);
    }


}

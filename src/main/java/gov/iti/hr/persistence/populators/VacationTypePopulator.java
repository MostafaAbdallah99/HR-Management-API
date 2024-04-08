package gov.iti.hr.persistence.populators;


import gov.iti.hr.persistence.entities.VacationTypes;
import gov.iti.hr.persistence.entities.enums.VacationType;
import gov.iti.hr.persistence.repository.TransactionManager;

public class VacationTypePopulator {
    public static void main(String[] args) {
        TransactionManager.doInTransactionWithoutResult(entityManager -> {
            VacationTypes annualLeave = new VacationTypes();
            annualLeave.setVacationTypeName(VacationType.ANNUAL_LEAVE);
            entityManager.persist(annualLeave);

            VacationTypes sickLeave = new VacationTypes();
            sickLeave.setVacationTypeName(VacationType.SICK_LEAVE);
            entityManager.persist(sickLeave);

            VacationTypes unpaidLeave = new VacationTypes();
            unpaidLeave.setVacationTypeName(VacationType.UNPAID_LEAVE);
            entityManager.persist(unpaidLeave);
        });
    }
}

package gov.iti.hr.persistence.entities.enums;

import lombok.Getter;

@Getter
public enum VacationStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String vacationStatus;

    VacationStatus(String vacationStatus) {
        this.vacationStatus = vacationStatus;
    }

}

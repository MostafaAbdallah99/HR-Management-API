package gov.iti.hr.persistence.entities.enums;

import lombok.Getter;

@Getter
public enum VacationStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String status;

    VacationStatus(String status) {
        this.status = status;
    }

}

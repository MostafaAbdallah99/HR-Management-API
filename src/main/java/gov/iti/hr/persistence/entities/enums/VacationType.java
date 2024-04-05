package gov.iti.hr.persistence.entities.enums;

import lombok.Getter;

@Getter
public enum VacationType {
    ANNUAL_LEAVE("Annual Leave"),
    SICK_LEAVE("Sick Leave"),
    UNPAID_LEAVE("Unpaid Leave");

    private final String vacationType;

    VacationType(String vacationType) {
        this.vacationType = vacationType;
    }

}

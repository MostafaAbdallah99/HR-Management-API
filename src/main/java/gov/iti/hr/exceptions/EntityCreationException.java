package gov.iti.hr.exceptions;

import gov.iti.hr.exceptions.base.BaseException;

public class EntityCreationException extends BaseException {
    public EntityCreationException(String message) {
        super(message, 500);
    }
}
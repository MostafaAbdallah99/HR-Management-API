package gov.iti.hr.exceptions;

import gov.iti.hr.exceptions.base.BaseException;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(message, 404);
    }
}
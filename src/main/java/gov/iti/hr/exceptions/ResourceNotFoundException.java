package gov.iti.hr.exceptions;

import gov.iti.hr.exceptions.generic.BaseException;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(message, 404);
    }
}
package gov.iti.hr.exceptions;

import gov.iti.hr.exceptions.generic.BaseException;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, 400);
    }
}

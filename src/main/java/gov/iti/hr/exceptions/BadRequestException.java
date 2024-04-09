package gov.iti.hr.exceptions;

import gov.iti.hr.exceptions.base.BaseException;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, 400);
    }
}

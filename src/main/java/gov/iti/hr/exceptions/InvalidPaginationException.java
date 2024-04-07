package gov.iti.hr.exceptions;

import gov.iti.hr.exceptions.generic.BaseException;

public class InvalidPaginationException extends BaseException {
    public InvalidPaginationException(String message) {
        super(message, 400);
    }
}

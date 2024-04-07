package gov.iti.hr.exceptions.generic;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final int errorCode;

    protected BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
package gov.iti.hr.models.validation;

import gov.iti.hr.exceptions.EntityCreationException;
import gov.iti.hr.exceptions.BadRequestException;
import gov.iti.hr.models.dto.DTO;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class BeanValidator {

    private static final Validator validator;

    private BeanValidator() {
    }

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void validateBean(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public static void validateID(DTO dto) {
        if(dto.getDTOId() != null) {
            throw new EntityCreationException("ID should not be provided for creation");
        }
    }

    public static void validatePaginationParameters(PaginationBean paginationBean) {
        boolean isLimitNegative = paginationBean.getLimit() < 0;
        boolean isOffsetNegative = paginationBean.getOffset() < 0;

        if (isLimitNegative && isOffsetNegative) {
            throw new BadRequestException("Both limit and offset cannot be negative");
        } else if (isLimitNegative) {
            throw new BadRequestException("Limit cannot be negative");
        } else if (isOffsetNegative) {
            throw new BadRequestException("Offset cannot be negative");
        }
    }
}
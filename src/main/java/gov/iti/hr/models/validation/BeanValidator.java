package gov.iti.hr.models.validation;

import gov.iti.hr.exceptions.EntityCreationException;
import gov.iti.hr.exceptions.InvalidPaginationException;
import gov.iti.hr.models.dto.DTO;
import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.HttpMethod;

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

    public static void validateID(DTO dto, String httpRequestMethod) {
        if(HttpMethod.POST.equals(httpRequestMethod) && dto.getDTOId() != null) {
            throw new EntityCreationException("ID should not be provided when creating a new entity");
        }

        if(HttpMethod.PUT.equals(httpRequestMethod) && dto.getDTOId() == null) {
            throw new EntityCreationException("ID should be provided when updating an entity");
        }
    }

    public static void validatePaginationParameters(PaginationBean paginationBean) {
        boolean isLimitNegative = paginationBean.getLimit() < 0;
        boolean isOffsetNegative = paginationBean.getOffset() < 0;

        if (isLimitNegative && isOffsetNegative) {
            throw new InvalidPaginationException("Both limit and offset cannot be negative");
        } else if (isLimitNegative) {
            throw new InvalidPaginationException("Limit cannot be negative");
        } else if (isOffsetNegative) {
            throw new InvalidPaginationException("Offset cannot be negative");
        }
    }
}
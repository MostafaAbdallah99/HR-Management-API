package gov.iti.hr.models.validation;

import gov.iti.hr.exceptions.EntityCreationException;
import gov.iti.hr.models.dto.DTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.HttpMethod;

import java.util.Set;

public class BeanValidator {

    private static final Validator validator;

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
        if(HttpMethod.POST.equals(httpRequestMethod) && dto.ID() != null) {
            throw new EntityCreationException("ID should not be provided when creating a new entity");
        }

        if(HttpMethod.PUT.equals(httpRequestMethod) && dto.ID() == null) {
            throw new EntityCreationException("ID should be provided when updating an entity");
        }
    }
}
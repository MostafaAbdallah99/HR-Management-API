package gov.iti.hr.exceptions.exceptionmappers;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import jakarta.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException constraintViolationException) {
        Map<String, Object> responseMap = new HashMap<>();
        String errorMessage = constraintViolationException.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        responseMap.put("status", Response.Status.BAD_REQUEST.getStatusCode());
        responseMap.put("message", errorMessage);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(responseMap)
                .build();
    }
}
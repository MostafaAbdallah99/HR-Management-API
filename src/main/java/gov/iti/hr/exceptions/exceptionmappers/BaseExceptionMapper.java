package gov.iti.hr.exceptions.exceptionmappers;

import gov.iti.hr.exceptions.generic.BaseException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class BaseExceptionMapper implements ExceptionMapper<BaseException> {
    @Override
    public Response toResponse(BaseException baseException) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", baseException.getErrorCode());
        responseMap.put("message", baseException.getMessage());
        return Response.status(baseException.getErrorCode())
                .entity(responseMap)
                .build();
    }
}
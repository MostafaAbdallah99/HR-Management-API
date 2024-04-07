package gov.iti.hr.exceptions.generic;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class RestBaseExceptionMapper implements ExceptionMapper<BaseException> {

    @Override
    public Response toResponse(BaseException exception) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", exception.getErrorCode());
        responseMap.put("message", exception.getMessage());

        return Response.status(exception.getErrorCode())
                .entity(responseMap)
                .build();
    }
}
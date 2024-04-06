package gov.iti.hr.exceptions.generic;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RestBaseExceptionMapper implements ExceptionMapper<BaseException> {

    @Override
    public Response toResponse(BaseException exception) {
        return Response.status(exception.getErrorCode())
                .entity(exception.getMessage())
                .build();
    }
}
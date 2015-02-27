package org.geoint.jaxrs.exception.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.geoint.jaxrs.exception.http.HttpErrorEntity;
import org.geoint.jaxrs.exception.log.JaxrsResponseLogger;

/**
 * Logs a WebApplicationException for traceability before it's returned to the
 * client.
 *
 * Since WebApplicationException exceptions are created by the application, they
 * are assumed to be "safe", and not contain sensitive information. Applications
 * may add more specific exception mappers if there turns out to not be the
 * case.
 */
public class WebApplicationExceptionMapper
        implements ExceptionMapper<WebApplicationException> {

    private static final JaxrsResponseLogger logger 
            = JaxrsResponseLogger.instance();

    @Override
    public Response toResponse(WebApplicationException e) {
        Response response = e.getResponse();
//TODO ensure the response has the correct header and body
        if (response.getEntity() == null 
                || !response.getEntity().getClass().equals(HttpErrorEntity.class)) {
            
        }
        logger.log(response, e);
        return response;
    }

}

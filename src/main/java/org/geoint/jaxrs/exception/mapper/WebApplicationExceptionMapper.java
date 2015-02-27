package org.geoint.jaxrs.exception.mapper;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
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
@Provider
public class WebApplicationExceptionMapper
        implements ExceptionMapper<WebApplicationException> {

    //JAX-RS injected information
    @Inject
    UriInfo uriInfo;
    @Inject
    SecurityContext securityContext;
    @Inject
    HttpHeaders headers;

    private static final JaxrsResponseLogger logger
            = JaxrsResponseLogger.instance();

    @Override
    public Response toResponse(WebApplicationException e) {
        Response response = e.getResponse();

        if (response.getEntity() == null
                || !response.getEntity().getClass().equals(HttpErrorEntity.class)) {
            final Response oldResponse = e.getResponse();
            response = Response.status(oldResponse.getStatus())
                    .
        }
        logger.log(response, e);
        return response;
    }

}

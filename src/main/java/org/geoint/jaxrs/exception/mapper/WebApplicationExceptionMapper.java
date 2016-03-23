package org.geoint.jaxrs.exception.mapper;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.geoint.jaxrs.exception.http.HttpErrorEntity;
import org.geoint.jaxrs.exception.log.JaxrsExceptionLogRecord;
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

    @Inject
    UriInfo uriInfo;
    @Inject
    SecurityContext securityContext;
    @Inject
    HttpSession session;
    @Inject
    HttpHeaders headers;
    @Inject
    HttpServletRequest request;

    private static final JaxrsResponseLogger logger
            = JaxrsResponseLogger.instance();

    @Override
    public Response toResponse(WebApplicationException e) {

        JaxrsExceptionLogRecord record
                = new JaxrsExceptionLogRecord(Level.WARNING, "There was a problem while attempting to process a REST request",
                        e.getResponse().getStatus(),
                        securityContext.getUserPrincipal().getName(),
                        session.getId(),
                        uriInfo.getAbsolutePath().toString(),
                        request.getMethod(),
                        e);

        Response response = e.getResponse()
                .status(e.getResponse().getStatus())
                .entity(String.format("[%s] %s", record.getUuid(), record.getMessage()))
                .build();

        logger.log(record);

        return response;
    }

}

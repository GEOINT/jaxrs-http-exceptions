package org.geoint.jaxrs.exception.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
 * Catch-all ExceptionMapper for all Throwables.
 *
 * The status code and message returned to the client is configurable by either
 * defining a file path to an XML configuration file or by including an XML
 * configuration file named "jaxrs-exception.xml" on the classpath.
 */
@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    private static final int DEFAULT_STATUS_CODE = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    private static final String DEFAULT_MESSAGE = "We're currently experiencing "
            + "problems satisfying requests, please try again later.";
    private static final HttpErrorEntity DEFAULT_ENTITY;
    private static final JaxrsResponseLogger logger
            = JaxrsResponseLogger.instance();

    static {
        DEFAULT_ENTITY = new HttpErrorEntity(DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
    }

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

    @Override
    public Response toResponse(Throwable e) {

        JaxrsExceptionLogRecord record
                = new JaxrsExceptionLogRecord(Level.WARNING, "An unknown error occurred. Please try again later.",
                        DEFAULT_STATUS_CODE,
                        securityContext.getUserPrincipal().getName(),
                        session.getId(),
                        uriInfo.getAbsolutePath().toString(),
                        request.getMethod(),
                        e);

        final Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(String.format("[%s] %s", record.getUuid(), record.getMessage()))
                .build();

        logger.log(record);
        return response;
    }

}

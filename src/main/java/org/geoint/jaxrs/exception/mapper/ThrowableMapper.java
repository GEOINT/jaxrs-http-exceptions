package org.geoint.jaxrs.exception.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.inject.Inject;
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

    private static final int DEFAULT_STATUS_CODE = 500;
    private static final String DEFAULT_MESSAGE = "We're currently experiencing "
            + "problems satisfying requests, please try again later.";
    private static final HttpErrorEntity DEFAULT_ENTITY;
    private static final Map<Class<? extends Throwable>, Integer> throwableStatus;
    private static final JaxrsResponseLogger logger
            = JaxrsResponseLogger.instance();

    static {
        //load throwable config from file
        throwableStatus = new HashMap<>();
        //TODO

        DEFAULT_ENTITY = new HttpErrorEntity(DEFAULT_STATUS_CODE, DEFAULT_MESSAGE);
    }

    //JAX-RS injected information
    @Inject
    UriInfo uriInfo;
    @Inject
    SecurityContext securityContext;
    @Inject
    HttpHeaders headers;
    
    @Override
    public Response toResponse(Throwable e) {
        final HttpErrorEntity entity
                = throwableEntity.getOrDefault(e.getClass(), DEFAULT_ENTITY);

        final Response response = Response.status(entity.getStatusCode())
                .entity(entity)
                .build();

        JaxrsExceptionLogRecord record
                = new JaxrsExceptionLogRecord(Level.WARNING, message,
                        response.getStatus(), userId, sessionId, resourceUrl,
                        httpMethod);
        logger.log(response, e);

        return response;
    }

}

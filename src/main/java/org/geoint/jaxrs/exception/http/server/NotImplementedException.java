package org.geoint.jaxrs.exception.http.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * HTTP 501 Not Implemented exception
 */
public class NotImplementedException extends WebApplicationException {

    public NotImplementedException(String message) {
        super(Response.status(Response.Status.fromStatusCode(501))
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}

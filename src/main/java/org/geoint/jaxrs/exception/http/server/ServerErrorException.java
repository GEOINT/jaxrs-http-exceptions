
package org.geoint.jaxrs.exception.http.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * HTTP 500
 */
public class ServerErrorException extends WebApplicationException {

    public ServerErrorException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}

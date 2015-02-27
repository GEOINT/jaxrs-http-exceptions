
package org.geoint.jaxrs.exception.http.client;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * HTTP 404 
 */
public class NotFoundException extends WebApplicationException {

    public NotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }

}

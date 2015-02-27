package org.geoint.jaxrs.exception.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAX-RS WebApplicationException that logs (using java.util.logging.*)
 * exception details (ie stack traces) along with a unique identifier.
 *
 * This unique identifier is also provided, along with consumer-friendly error
 * details, in the body of the response.
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class HttpErrorEntity {

    @XmlAttribute(name = "status", required = true)
    private int statusCode;
    @XmlAttribute(name = "id", required = true)
    private String errorId;
    @XmlElement(name = "message")
    private String message;
    @XmlElementWrapper(name = "details", nillable = true)
    @XmlElement(name = "detail")
    private List<String> details = new ArrayList<>();

    public HttpErrorEntity() {
    }

    public HttpErrorEntity(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.errorId = UUID.randomUUID().toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(errorId).append("] ")
                .append(statusCode).append(" - ")
                .append(message);
        if (!details.isEmpty()) {
            sb.append("\n").append(String.join("\n", details));
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.errorId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HttpErrorEntity other = (HttpErrorEntity) obj;
        if (!Objects.equals(this.errorId, other.errorId)) {
            return false;
        }
        return true;
    }

}

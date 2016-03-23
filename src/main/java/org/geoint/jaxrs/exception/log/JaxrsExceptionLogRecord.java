package org.geoint.jaxrs.exception.log;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Log record containing additional JAX-RS and request details.
 */
public class JaxrsExceptionLogRecord extends LogRecord {
    
    private final static long serialVersionUID = 1L;
    private int statusCode;
    private String uuid;
    private String userId;
    private String sessionId;
    private String resourceUrl;
    private String httpMethod;
    
    public JaxrsExceptionLogRecord(Level level, String msg, int statusCode,
            String userId, String sessionId, String resourceUrl,
            String httpMethod, Throwable causedBy) {
        super(level, msg);
        this.uuid = UUID.randomUUID().toString();
        this.statusCode = statusCode;
        this.userId = userId;
        this.sessionId = sessionId;
        this.resourceUrl = resourceUrl;
        this.httpMethod = httpMethod;
        this.setThrown(causedBy);
    }
    
    public String getResourceUrl() {
        return resourceUrl;
    }
    
    public String getHttpMethod() {
        return httpMethod;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }
    
    public boolean isRedirect() {
        return statusCode >= 300 && statusCode < 400;
    }
    
    public boolean isServerError() {
        return statusCode >= 500 && statusCode < 500;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    @Override
    public String toString() {
        return String.format("REST error '%s'; status: %d. %s",
                uuid, statusCode, super.getMessage());
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.uuid);
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
        final JaxrsExceptionLogRecord other = (JaxrsExceptionLogRecord) obj;
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }
    
}

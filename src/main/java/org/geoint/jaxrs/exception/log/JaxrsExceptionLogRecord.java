package org.geoint.jaxrs.exception.log;

import java.util.Objects;
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

    public JaxrsExceptionLogRecord(Level level, int statusCode, String uuid,
            String userId, String sessionId, String msg) {
        super(level, msg);
        this.statusCode = statusCode;
        this.uuid = uuid;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "REST ERROR " + uuid + "; Status: " + statusCode + "; "
                + super.toString();
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

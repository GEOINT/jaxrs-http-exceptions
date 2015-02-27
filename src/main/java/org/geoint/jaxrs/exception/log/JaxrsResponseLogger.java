package org.geoint.jaxrs.exception.log;

import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 * Logs a {@link Response} such that it is traceable between client and server
 * by unique identifier.
 *
 * All exceptions that would have been sent to the client are logged with one of
 * three loggers:
 * <table>
 * <tr>
 * <th>HTTP Status Code</th>
 * <th>Logger</th>
 * </tr>
 * <tr>
 * <td>3xx</td>
 * <td>rest.exception.3xx</td>
 * </tr>
 * <tr>
 * <td>4xx</td>
 * <td>rest.exception.4xx</td>
 * </tr>
 * <tr>
 * <td>5xx</td>
 * <td>rest.exception.5xx</td>
 * </tr>
 * </table>
 *
 * All LogRecords that do not have status code (ie anything other than a
 * {@link JaxrsExceptionLogRecord}) will be logged under the parent logger
 * <i>rest.exception</i>.
 */
@SuppressWarnings("ClassWithMultipleLoggers")
public class JaxrsResponseLogger extends Logger {

    private static final Level DEFAULT_CLIENT_LEVEL = Level.WARNING;
    private static final Level DEFAULT_REDIRECT_LEVEL = Level.FINER;
    private static final Level DEFAULT_SERVER_LEVEL = Level.SEVERE;
    private static final String PARENT_NAME = "rest.exception";
    private static final String REDIRECT_NAME = "rest.exception.3xx";
    private static final String CLIENT_NAME = "rest.exception.4xx";
    private static final String SERVER_NAME = "rest.exception.5xx";
    private static final Logger parentLogger = Logger.getLogger(PARENT_NAME);
    private static final Logger redirectLogger;
    private static final Logger clientLogger;
    private static final Logger serverLogger;

    static {
        redirectLogger = Logger.getLogger(REDIRECT_NAME);
        redirectLogger.setLevel(DEFAULT_REDIRECT_LEVEL);

        clientLogger = Logger.getLogger(CLIENT_NAME);
        clientLogger.setLevel(DEFAULT_CLIENT_LEVEL);

        serverLogger = Logger.getLogger(SERVER_NAME);
        serverLogger.setLevel(DEFAULT_SERVER_LEVEL);

        LogManager.getLogManager().addLogger(
                new JaxrsResponseLogger(PARENT_NAME, null));
    }

    private JaxrsResponseLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static JaxrsResponseLogger instance() {
        return (JaxrsResponseLogger) LogManager.getLogManager().getLogger(PARENT_NAME);
    }

    public void log(JaxrsExceptionLogRecord record) {
        if (record.isClientError()) {
            clientLogger.log(record);
        } else if (record.isRedirect()) {
            redirectLogger.log(record);
        } else {
            serverLogger.log(record);
        }
    }

    public void log(Response response, Throwable ex) {
        //TODO create JaxrsExceptionLogRecord

    }

    @Override
    public void setFilter(Filter newFilter) throws SecurityException {
        parentLogger.setFilter(newFilter);
    }

    @Override
    public Filter getFilter() {
        return parentLogger.getFilter();
    }

    @Override
    public void setLevel(Level newLevel) throws SecurityException {
        parentLogger.setLevel(newLevel);
    }

    @Override
    public Level getLevel() {
        return parentLogger.getLevel();
    }

    @Override
    public boolean isLoggable(Level level) {
        return parentLogger.isLoggable(level);
    }

    @Override
    public String getName() {
        return parentLogger.getName();
    }

    @Override
    public void addHandler(Handler handler) throws SecurityException {
        parentLogger.addHandler(handler);
    }

    @Override
    public void removeHandler(Handler handler) throws SecurityException {
        parentLogger.removeHandler(handler);
    }

    @Override
    public Handler[] getHandlers() {
        return parentLogger.getHandlers();
    }

    @Override
    public void setParent(Logger parent) {
        parentLogger.setParent(parent);
    }

    @Override
    public void log(LogRecord record) {
        //we should only get here if this logger is used generically (not 
        //with a JaxrsExceptionLogRecord).  In this case, we'll log to the 
        //parent logger, not to a specific http logger
        parentLogger.log(record);
    }

    /**
     * Set the default log Level used for HTTP 4xx status codes.
     *
     * @param level
     */
    public static void setClientLevel(Level level) {
        clientLogger.setLevel(level);
    }

    /**
     * Default log level used for HTTP 4xx status codes.
     *
     * @return HTTP 4xx log level
     */
    public static Level getClientLevel() {
        return clientLogger.getLevel();
    }

    /**
     * Default log level used for HTTP 3xx status codes.
     *
     * @return HTTP 3xx log level
     */
    public static Level getRedirectLevel() {
        return redirectLogger.getLevel();
    }

    /**
     * Set default log level used for HTTP 3xx status codes.
     *
     * @param level
     */
    public static void setRedirectLevel(Level level) {
        redirectLogger.setLevel(level);
    }

    /**
     * Default log level for HTTP 5xx status codes.
     *
     * @return HTTP 5xx log level
     */
    public static Level getServerLevel() {
        return serverLogger.getLevel();
    }

    /**
     * Set default log level used for HTTP 5xx status codes.
     *
     * @param level
     */
    public static void setServerLevel(Level level) {
        serverLogger.setLevel(level);
    }
}

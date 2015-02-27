/**
 * Exceptions that model after the HTTP domain language, allowing developers to
 * develop easier for their environment (REST/HTTP).
 *
 * Developers can easily return errors with specific HTTP status codes simply by
 * throwing the correct exception. Developers are encouraged to use these
 * exceptions ONLY in JAX-RS resources and not in general application code (if
 * only for clarity).
 *
 * Using these exceptions will also encourage clear and concise client error
 * messages, free from stack traces dumps, and proper logging on the server-side
 * to ensure traceability.
 */
package org.geoint.jaxrs.exception.http;

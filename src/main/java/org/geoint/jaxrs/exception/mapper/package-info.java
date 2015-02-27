/**
 * JAX-RS exception mappers for standard java exceptions.
 * 
 * Some of these exceptions are "catch-all" (ie {@link Throwable}, 
 * {@link RuntimeException}, etc) in the event that there are application, 
 * framework, or resource exceptions/errors thrown that are simply not caught 
 * by the developer.
 * 
 */
package org.geoint.jaxrs.exception.mapper;

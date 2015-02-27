/**
 * I agonized over if 3xx codes should be an "exceptional" situation, but
 * finally I decided to proceed. I believe there are business cases where an
 * "exceptional" situation would lead to a 3xx redirect. Further, leveraging the
 * traceability benefits provided by this library was hard to pass (ie ability
 * to easily monitor who, and how frequently, requests are made that should have
 * gone through a proxy [305], for example).
 * 
 * If you don't like the idea of 3xx being an exceptional situation...don't 
 * use them =)
 */
package org.geoint.jaxrs.exception.http.redirect;

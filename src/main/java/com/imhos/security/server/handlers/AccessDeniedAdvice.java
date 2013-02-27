package com.imhos.security.server.handlers;

import com.imhos.security.shared.model.AccessDeniedGWTException;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.access.AccessDeniedException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class AccessDeniedAdvice implements ThrowsAdvice {

    public AccessDeniedAdvice() {
    }

    public void afterThrowing(AccessDeniedException ex) throws Throwable {
        throw new AccessDeniedGWTException(ex.getMessage());
    }
}

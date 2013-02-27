package com.imhos.security.shared.model;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class AccessDeniedGWTException extends Exception {

    private AccessDeniedGWTException accessDeniedGWTException;

    public AccessDeniedGWTException() {
    }

    public AccessDeniedGWTException(String message) {
        super(message);
    }

    public AccessDeniedGWTException(Throwable cause, String message) {
        super(message, cause);
    }

    public AccessDeniedGWTException(Throwable cause) {
        super(cause);
    }

}

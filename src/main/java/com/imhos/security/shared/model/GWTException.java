package com.imhos.security.shared.model;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class GWTException extends RuntimeException {

    public GWTException() {
    }

    public GWTException(String message) {
        super(message);
    }

    public GWTException(String message, Throwable cause) {
        super(message, cause);
    }

    public GWTException(Throwable cause) {
        super(cause);
    }

}

package com.imhos.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 09.02.13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class HttpSessionChecker  implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent event) {
        System.out.printf("Session ID %s created at %s%n", event.getSession().getId(), new Date());
    }
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.printf("Session ID %s destroyed at %s%n", event.getSession().getId(), new Date());
    }
}

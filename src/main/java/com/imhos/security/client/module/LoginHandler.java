package com.imhos.security.client.module;

import com.imhos.security.shared.model.UserDetails;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 12.02.13 18:09
 */
public interface LoginHandler<E> {

    void handleLoginSuccess(UserDetails userDetails);

    void handleLoginError(E error);
}

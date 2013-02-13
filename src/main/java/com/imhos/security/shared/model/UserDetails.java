package com.imhos.security.shared.model;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 12.02.13 12:25
 */
public interface UserDetails {

    String getUsername();

    List<String> getAuthorities();


}

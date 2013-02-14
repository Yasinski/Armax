package com.imhos.security.client.module;

import com.imhos.security.shared.model.AuthenticationError;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 12.02.13 19:35
 */
public class AuthenticationErrorDeserializerImpl implements Deserializer<AuthenticationError> {
    @Override
    public AuthenticationError deserialize(String json) {
        return AuthenticationError.values()[Integer.valueOf(json)];
    }
}

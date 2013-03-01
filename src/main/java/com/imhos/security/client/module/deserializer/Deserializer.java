package com.imhos.security.client.module.deserializer;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 12.02.13 17:56
 */
public interface Deserializer<E> {

    public E deserialize(String json);
}

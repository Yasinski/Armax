package com.imhos.security.server.serializer;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 13.02.13
 * Time: 6:58
 * To change this template use File | Settings | File Templates.
 */
public interface Serializer<T> {

    public String serialize(T toSerialize);
}

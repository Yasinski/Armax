package com.imhos.security.client.module.deserializer;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.imhos.security.shared.model.UserDetails;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 12.02.13 18:00
 */
public class UserDetailsDeserializerGwtImpl implements Deserializer<UserDetails> {
    @Override
    public UserDetails deserialize(String json) {
        UserDetailsBeanFactory beanFactory = GWT.create(UserDetailsBeanFactory.class);
        UserDetails person = AutoBeanCodex.decode(beanFactory, UserDetails.class, json).as();
        return person;

    }

    interface UserDetailsBeanFactory extends AutoBeanFactory {
        AutoBean<UserDetails> createBean();
    }
}

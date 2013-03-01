package com.imhos.security.client.module.deserializer;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.imhos.security.shared.model.UserDetails;
import com.imhos.security.shared.model.UserDetailsDTO;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 12.02.13 17:59
 */
public class UserDetailsDeserializerImpl implements Deserializer<UserDetails> {

    @Override
    public UserDetails deserialize(String json) {
        JSONValue parsed = JSONParser.parseStrict(json);
        JSONObject jsonObj = parsed.isObject();
        UserDetailsDTO user = new UserDetailsDTO();
        if (jsonObj != null) {
            user.setUsername(jsonObj.get(UserDetailsDTO.USERNAME_FIELD).toString());
            JSONArray array = jsonObj.get(UserDetailsDTO.AUTHORITIES_FIELD).isArray();
            for (int i = 0; i < array.size(); i++) {
                JSONValue obj = array.get(i);
                user.getAuthorities().add(obj.toString());
            }
        }
        return user;
    }
}

package com.imhos.security.server.serializer;

import com.google.gson.Gson;
import com.imhos.security.server.model.UserConnection;
import com.imhos.security.shared.model.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import third.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Panstvo
 * Date: 13.02.13
 * Time: 6:50
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationSerializer implements Serializer<Authentication> {

    @Override
    public String serialize(Authentication authentication) {

        UserDetailsDTO user = new UserDetailsDTO();
        if (authentication.getPrincipal() instanceof User) {
            user.setUsername(((User) authentication.getPrincipal()).getDisplayName());
        } else if (authentication.getPrincipal() instanceof UserConnection) {
            user.setUsername(((UserConnection) authentication.getPrincipal()).getDisplayName());
        } else {
            user.setUsername(authentication.getPrincipal().toString());
        }
        List<String> authorities = new ArrayList<String>();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            authorities.add(grantedAuthority.toString());
        }
        user.setAuthorities(authorities);
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}

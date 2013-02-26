package com.imhos.security.server.serializer;

import com.google.gson.Gson;
import com.imhos.security.shared.model.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
        user.setUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        List<String> authorities = new ArrayList<String>();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            authorities.add(grantedAuthority.toString());
        }
        user.setAuthorities(authorities);
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}

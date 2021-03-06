package com.imhos.security.server.model;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import third.model.Role;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User implements UserDetails {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column
    //    todo: should be long???????
    private String id;

    @Column
    private String displayName;
    @Column
    private String username;  // equals to email
    @Column
    private String email;

    @Column
    private String password;


    @CollectionOfElements(fetch = FetchType.EAGER)
    @JoinTable
    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities = new HashSet<Role>();

    @Column
    private boolean enabled = true;

    @Column
    private boolean profileSubmittedByUser;

    @OneToMany(mappedBy = "user")
    private Set<UserConnection> userConnections = new HashSet<UserConnection>();

    public User(String username, String password, Set<Role> authorities, boolean enabled) {
        this.username = username;
        this.enabled = enabled;
        this.password = password;
        this.authorities = authorities;
    }

    public Set<UserConnection> getUserConnections() {
        return userConnections;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String displayName, String username, String email, String password, Set<Role> authorities, boolean profileSubmittedByUser) {
        this.displayName = displayName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.profileSubmittedByUser = profileSubmittedByUser;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void setAuthorities(Role... authorities) {
        this.authorities = new HashSet<Role>(Arrays.asList(authorities));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isProfileSubmittedByUser() {
        return profileSubmittedByUser;
    }

    public void setProfileSubmittedByUser(boolean profileSubmittedByUser) {
        this.profileSubmittedByUser = profileSubmittedByUser;
    }

}

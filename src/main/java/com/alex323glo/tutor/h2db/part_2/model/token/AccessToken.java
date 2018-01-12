package com.alex323glo.tutor.h2db.part_2.model.token;

import com.alex323glo.tutor.h2db.part_2.model.user.UserType;

import java.io.Serializable;

/**
 * Access Token model.
 *
 * @author alex323glo
 * @version 1.0
 */
public class AccessToken implements Serializable {

    private String token;
    private String username;
    private UserType type;

    public AccessToken(String token, String username) {
        setToken(token);

        this.username = username;
        type = UserType.USER;
    }

    public AccessToken(String token, String username, UserType type) {
        this.token = token;
        this.username = username;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token == null) {
            throw new NullPointerException("token is null");
        }
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AccessToken that = (AccessToken) object;

        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}

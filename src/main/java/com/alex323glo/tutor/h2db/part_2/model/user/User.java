package com.alex323glo.tutor.h2db.part_2.model.user;

/**
 * User model.
 * 
 * @author alex323glo 
 * @version 1.0
 */
public class User {
    
    private String username;
    private String password;
    private UserType userType;

    public User(String username, String password) {        
        setUsername(username);
        
        this.password = password;
        userType = UserType.USER;
    }

    public User(String username, String password, UserType userType) {
        setUsername(username);
        
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new NullPointerException("username is null");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return userType == user.userType;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}

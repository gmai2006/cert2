package com.tomcat.hosting.security;

import com.tomcat.hosting.dao.User;

public class UserManagerImpl implements UserManager {

    private User currentUser;

    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

}

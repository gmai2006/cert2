package com.tomcat.hosting.security;

import com.tomcat.hosting.dao.User;

public interface UserManager {

    void setCurrentUser(User user);

    User getCurrentUser();

}

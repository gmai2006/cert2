package com.tomcat.hosting.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;

public class RoleValidationInterceptor implements MethodInterceptor {

    @Inject
    private UserManager userManager;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        int requiredRole = 
            invocation.getMethod().getAnnotation(RequiresRole.class).value();

        if( userManager.getCurrentUser() == null ||
            !(userManager.getCurrentUser().getUserRole() == requiredRole)) {

            throw new IllegalStateException("User requires role " + requiredRole);
        }

        return invocation.proceed();
    }
}
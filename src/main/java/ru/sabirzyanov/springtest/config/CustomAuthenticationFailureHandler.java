package ru.sabirzyanov.springtest.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
       /* System.out.println("SecurityConfig.failureLogin()#httpservletrequest\n"
                + ReflectionToStringBuilder.toString(httpServletRequest, ToStringStyle.SIMPLE_STYLE)
                + "\n----------------"
                + "\ngetQueryString: " + httpServletRequest.getQueryString()
                + "\ngetRequestURI: " + httpServletRequest.getRequestURI()
                + "\ngetServletPath: " + httpServletRequest.getServletPath()
                + "\ngetRequestURL: " + httpServletRequest.getRequestURL()
                + "\n\n"
        );

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.sendRedirect("login");*/
        if(e.getClass().isAssignableFrom(BadCredentialsException.class)) {
            setDefaultFailureUrl("/url1");
        } else {
            setDefaultFailureUrl("/url2");
        }
        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
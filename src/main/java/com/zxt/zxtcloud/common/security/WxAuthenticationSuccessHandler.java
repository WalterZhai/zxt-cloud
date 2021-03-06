package com.zxt.zxtcloud.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Component("wxAuthenticationSuccessHandler")
public class WxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String state = request.getParameter("state");
        String[] params = state.split(";");

        response.sendRedirect("/"+params[1]);
    }
}

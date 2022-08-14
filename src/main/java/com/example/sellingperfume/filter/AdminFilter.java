package com.example.sellingperfume.filter;

import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.CreateTokenInformationUser;
import com.example.sellingperfume.services.impl.UserServicesImpl;
import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Autowired
    private CreateTokenInformationUser createTokenInformationUser;
    @Autowired
    private UserServicesImpl userServicesImpl;
    private static Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("TokenInfoUser") != null && session.getAttribute("isLogin").equals("true")) {
            String tokenInfoUser = session.getAttribute("TokenInfoUser").toString();
            try {
                String splitToken = createTokenInformationUser.decryptTokenUser(tokenInfoUser);
                String userName = splitToken.split(",")[1];
                String showSystem = splitToken.split(",")[3];
                if (userName != null && !userName.isEmpty()) {
                    UserEntity userEntity = userServicesImpl.FindUserByUsername(userName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            res.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

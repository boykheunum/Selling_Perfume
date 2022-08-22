package com.example.sellingperfume.filter;

import com.example.sellingperfume.Common.TokenCommon;
import com.example.sellingperfume.entity.AuthorityEntity;
import com.example.sellingperfume.entity.UserEntity;
import com.example.sellingperfume.services.impl.AuthorityServicesImpl;
import com.example.sellingperfume.services.impl.UserServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Autowired
    private UserServicesImpl userServicesImpl;
    @Autowired
    private AuthorityServicesImpl authorityServicesImpl;

    private TokenCommon tokenCommon;

    private final String TEXT_TOKEN_USER = "tokenInfoUser";
    private final String TEXT_USER_ROLE = "USER_ROLE";
    private final String TEXT_ADMIN_ROLE = "ADMIN_ROLE";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String localUrl = req.getRequestURL().toString();
        tokenCommon = new TokenCommon();
        if (session.getAttribute(TEXT_TOKEN_USER) != null && session.getAttribute("isLogin").toString().equals("true")) {
            String tokenInfoUser = session.getAttribute(TEXT_TOKEN_USER).toString();
            try {
                logger.info("check split token: "+tokenCommon.decryptTokenUser(tokenInfoUser).toString());
                String splitToken = tokenCommon.decryptTokenUser(tokenInfoUser);
                String userName = splitToken.split(",")[1];
                if (userName != null && !userName.isEmpty()) {
                    UserEntity userEntity = userServicesImpl.FindUserByUsername(userName);
                    if(userEntity != null) {
                        if(userEntity.getRole().equals(TEXT_ADMIN_ROLE)){
                            String splitUrl[] = localUrl.split("/");
                            String system = splitUrl[4];
                            AuthorityEntity authorityEntity = authorityServicesImpl.getAthorityByName(system);
                        }else {
                            res.sendRedirect("homepage");
                        }
                    }
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

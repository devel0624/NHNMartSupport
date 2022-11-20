package com.nhnacademy.mart.support.interceptor;

import com.nhnacademy.mart.support.domain.LoginCookie;
import com.nhnacademy.mart.support.exception.LoginSessionNotExist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Cookie cookie = LoginCookie.getCookie(request);

            if(Objects.isNull(cookie)){
                throw new NullPointerException("Cookie is Null");
            }

        }catch (NullPointerException | LoginSessionNotExist e){
            log.info("",e);
            response.sendRedirect("/login");
        }

        return true;
    }
}


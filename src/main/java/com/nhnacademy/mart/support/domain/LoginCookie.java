package com.nhnacademy.mart.support.domain;

import com.nhnacademy.mart.support.exception.LoginSessionNotExist;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoginCookie {
    public static String getLoginCookie(HttpServletRequest request) {
        Cookie cookie = getCookie(request);

        if(Objects.isNull(cookie)){
            throw new LoginSessionNotExist();
        }

        return cookie.getValue();
    }

    public static Cookie getCookie(HttpServletRequest request) {

        Cookie[] cookie = request.getCookies();

        if(Objects.isNull(cookie)){
            throw new NullPointerException("Cookie is Null");
        }

        if(Arrays.stream(cookie).noneMatch(x -> x.getName().equals("LoginSession"))){
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(x -> x.getName().equals("LoginSession"))
                .collect(Collectors.toList())
                .get(0);
    }
}

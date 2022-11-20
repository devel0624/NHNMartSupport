package com.nhnacademy.mart.support.domain;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoginCookie {

    private LoginCookie(){
    }
    public static String getLoginCookie(HttpServletRequest request) {
        Cookie cookie = getCookie(request);

        if(Objects.isNull(cookie)){
            return null;
        }

        return cookie.getValue();
    }

    public static Cookie getCookie(HttpServletRequest request) {

        Cookie[] cookie = request.getCookies();

        if(Objects.isNull(cookie)){
            return null;
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

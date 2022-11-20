package com.nhnacademy.mart.support.controller;

import com.nhnacademy.mart.support.domain.LoginCookie;
import com.nhnacademy.mart.support.domain.LoginRequest;
import com.nhnacademy.mart.support.domain.User;
import com.nhnacademy.mart.support.exception.InvalidPassword;
import com.nhnacademy.mart.support.exception.UserNotFoundException;
import com.nhnacademy.mart.support.exception.ValidationFailedException;
import com.nhnacademy.mart.support.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class LoginController {

    UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginForm(){
        log.info("loginForm");
        return "thymeleaf/loginForm";
    }

    @PostMapping ("/login")
    public ModelAndView login(HttpServletResponse response,
                              HttpServletRequest request,
                              @Valid @ModelAttribute LoginRequest loginRequest,
                              BindingResult bindingResult,
                              ModelAndView modelAndView){

        if(Objects.nonNull(LoginCookie.getCookie(request))){
            modelAndView.setViewName("redirect:/user");
        }else {
            if(bindingResult.hasErrors()){
                throw new ValidationFailedException(bindingResult);
            }

            String id = loginRequest.getId();
            String password = loginRequest.getPassword();

            modelAndView.setViewName("thymeleaf/loginForm");

            if(userRepository.exists(id)) {
                User user = userRepository.getUser(id);

                if(user.getPwd().equals(password)){
                    Cookie cookie = new Cookie("LoginSession",user.getId());
                    response.addCookie(cookie);

                    modelAndView.setViewName("redirect:/user");
                }else {
                    throw new InvalidPassword();
                }
            }else{
                throw new UserNotFoundException();
            }
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        Cookie cookie = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("LoginSession")).collect(Collectors.toList()).get(0);

        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login";
    }

}

package com.nhnacademy.mart.support.controller;

import com.nhnacademy.mart.support.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(InvalidPassword.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Password")
    public void invalidPassword() {
        // TODO document why this method is empty
    }

    @ExceptionHandler(LoginSessionNotExist.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Did Not Login")
    public void needLogin() {
        // TODO document why this method is empty
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Data Not Found")
    public void userNotFound() {
        // TODO document why this method is empty
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post Not Found")
    public void postNotFound() {
        // TODO document why this method is empty
    }

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Login Request")
    public String invalidValue(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);
        return "thymeleaf/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);
        return "thymeleaf/error";
    }


}

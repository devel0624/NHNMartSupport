package com.nhnacademy.mart.support.domain;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class LoginRequest {

    @NotBlank
    String id;

    @NotBlank
    String password;
}

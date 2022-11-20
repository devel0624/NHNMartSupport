package com.nhnacademy.mart.support.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostRegisterRequest {

    @NotBlank
    String category;

    @Length(min = 2,max = 200)
    String title;

    @Length(max = 40000)
    String content;
}

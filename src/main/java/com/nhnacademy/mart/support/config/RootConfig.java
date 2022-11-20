package com.nhnacademy.mart.support.config;

import com.nhnacademy.mart.support.Base;
import com.nhnacademy.mart.support.domain.Post;
import com.nhnacademy.mart.support.repository.PostRepository;
import com.nhnacademy.mart.support.repository.PostRepositoryImpl;
import com.nhnacademy.mart.support.repository.UserRepository;
import com.nhnacademy.mart.support.repository.UserRepositoryImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = Base.class,
        excludeFilters = { @ComponentScan.Filter(Controller.class)})
public class RootConfig {
    @Bean
    public UserRepository userRepository() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.register("customer01", "12345", "김고객","kim@customer.com", false);
        userRepository.register("customer02", "12345", "최고객","choi@customer.com", false);
        userRepository.register("manager01", "12345", "윤담당","yun@manager.com", true);

        return userRepository;
    }

    @Bean
    public PostRepository postRepository() {
        PostRepository postRepository = new PostRepositoryImpl();

        Post post = postRepository.register("Suggest","테스트용","테스트용 문의 게시글입니다.");
        post.setWriterId("customer01");
        post.setWriteTime("2022-11-19");

        Post reply = postRepository.register("Reply","테스트 답변","테스트용 답변 게시글입니다.");

        reply.setWriterId("CSManager");
        reply.setWriteTime("2022-11-19");

//        post.setReply(reply);

        return postRepository;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("message");

        return messageSource;
    }

}


package com.nhnacademy.mart.support.controller;

import com.nhnacademy.mart.support.domain.LoginCookie;
import com.nhnacademy.mart.support.domain.Post;
import com.nhnacademy.mart.support.domain.User;
import com.nhnacademy.mart.support.domain.Admin;
import com.nhnacademy.mart.support.repository.PostRepository;
import com.nhnacademy.mart.support.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    UserRepository userRepository;
    PostRepository postRepository;

    public UserController(UserRepository userRepository,PostRepository postRepository ){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @ModelAttribute("user")
    public User getUser(HttpServletRequest request){

        String userId = LoginCookie.getLoginCookie(request);

        return userRepository.getUser(userId);
    }

    @GetMapping
    public String userPage(@ModelAttribute("user") User user, Model model){

        model.addAttribute("user",user);

        log.info(user.getId());

        List<Post> list;
        if(user instanceof Admin){
            list = postRepository.getListUnanswered();
            model.addAttribute("admin",true);
        }else {
            list = postRepository.getListByUserId(user.getId());
            model.addAttribute("admin",false);
        }

        log.info(String.valueOf(list.size()));

        model.addAttribute("list",list);

        return "thymeleaf/userView";
    }
}

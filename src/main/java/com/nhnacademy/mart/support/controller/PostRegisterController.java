package com.nhnacademy.mart.support.controller;

import com.nhnacademy.mart.support.domain.Post;
import com.nhnacademy.mart.support.domain.PostRegisterRequest;
import com.nhnacademy.mart.support.exception.LoginSessionNotExist;
import com.nhnacademy.mart.support.exception.ValidationFailedException;
import com.nhnacademy.mart.support.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/post/register")
public class PostRegisterController {

    PostRepository postRepository;

    public PostRegisterController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @ModelAttribute("LoginUser")
    public String getUserId(HttpServletRequest request){
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(x -> x.getName().equals("LoginSession"))
                .collect(Collectors.toList())
                .get(0);

        if(Objects.isNull(cookie)){
            throw new LoginSessionNotExist();
        }

        return cookie.getValue();
    }



    @GetMapping
    public String postRegisterForm(@ModelAttribute("LoginUser") String userId,
                                   Model model){

        model.addAttribute("userId",userId);
        return "thymeleaf/inquiryRegister";
    }

    @PostMapping
    public ModelAndView registerPost(@Valid @ModelAttribute PostRegisterRequest request,
                               @ModelAttribute("LoginUser") String userId,
                               @RequestParam(value = "postId", required = false) Long postId,
                               BindingResult bindingResult,
                               ModelAndView mav){

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Post post = postRepository.register(request.getCategory(),request.getTitle(),request.getContent());

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        post.setWriterId(userId);
        post.setWriteTime(time);

        if(post.getCategory().equals("Reply")){
            postRepository.getPost(postId).setReply(post);
            post.setReply(new Post());
            mav.setViewName("redirect:/user");
        }else {
            mav.setViewName("thymeleaf/postView");
            mav.addObject("post",post);
        }
        return mav;
    }

    @GetMapping("/{postId}")
    public String registerPost(@PathVariable("postId") long postId,
                               @ModelAttribute("LoginUser") String userId,
                               Model model){
        Post post = postRepository.getPost(postId);

        model.addAttribute("userId",userId);
        model.addAttribute("post",post);

        return "thymeleaf/replyRegister";
    }
}

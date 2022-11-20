package com.nhnacademy.mart.support.controller;

import com.nhnacademy.mart.support.domain.Admin;
import com.nhnacademy.mart.support.domain.LoginCookie;
import com.nhnacademy.mart.support.domain.Post;
import com.nhnacademy.mart.support.exception.PostNotFoundException;
import com.nhnacademy.mart.support.repository.PostRepository;
import com.nhnacademy.mart.support.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
public class PostController {

    UserRepository userRepository;
    PostRepository postRepository;

    public PostController(UserRepository userRepository,PostRepository postRepository ){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @ModelAttribute("admin")
    public boolean getUser(HttpServletRequest request){

        String userId = LoginCookie.getLoginCookie(request);

        return userRepository.getUser(userId) instanceof Admin;
    }

    @GetMapping("/{postId}")
    public String postView(@PathVariable(value = "postId") long postId,
                           @ModelAttribute("admin") boolean admin,
                           Model model){

        if(!postRepository.exists(postId)){
            throw new PostNotFoundException();
        }

        Post post = postRepository.getPost(postId);

        model.addAttribute("post",post);
        model.addAttribute("reply",post.getReply());
        model.addAttribute("admin", admin);

        return "thymeleaf/postView";
    }

    @PostMapping("/filter")
    public String postFilter(@RequestParam("category") String category,
                             HttpServletRequest request,
                             Model model){

        String userId = LoginCookie.getLoginCookie(request);

        List<Post> list = postRepository.getFilteredList(category,userId);

        model.addAttribute("list",list);
        model.addAttribute("user",userRepository.getUser(userId));

        return "thymeleaf/userView";
    }
}

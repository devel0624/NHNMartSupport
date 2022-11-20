package com.nhnacademy.mart.support.repository;

import com.nhnacademy.mart.support.domain.Post;

import java.util.List;

public interface PostRepository {
    Post getPost(long postNo);
    Post register(String category, String title, String content);
    boolean exists(long postId);
    List<Post> getListByUserId(String id);
    List<Post> getListUnanswered();

    List<Post> getFilteredList(String category, String userId);
}

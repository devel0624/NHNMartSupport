package com.nhnacademy.mart.support.repository;

import com.nhnacademy.mart.support.domain.Post;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class PostRepositoryImpl implements PostRepository{
    private final Map<Long , Post> posts = new HashMap<>();

    @Override
    public Post getPost(long postNo) {
        return posts.get(postNo);
    }

    @Override
    public Post register(String category, String title, String content) {
        long postId = posts.keySet()
                .stream()
                .max(Comparator.comparing(Function.identity()))
                .map(l -> l + 1)
                .orElse(1L);

        Post post = new Post(
                postId,
                category,
                title,
                content
        );

        posts.put(postId,post);

        return post;
    }

    @Override
    public boolean exists(long postId) {
        return posts.containsKey(postId);
    }

    @Override
    public List<Post> getListByUserId(String userId) {
        return posts.values().stream()
                .filter(post -> Objects.equals(post.getWriterId(), userId))
                .sorted(Comparator.comparing(Post::getPostId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getListUnanswered() {
        return posts.values().stream()
                .filter(post -> Objects.isNull(post.getReply()))
                .sorted(Comparator.comparing(Post::getPostId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getFilteredList(String category, String userId) {
        return getListByUserId(userId).stream()
                .filter(post -> Objects.equals(post.getCategory(), category))
                .collect(Collectors.toList());
    }
}

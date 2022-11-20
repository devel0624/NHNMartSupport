package com.nhnacademy.mart.support.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Post {

    @Getter
    long postId;

    @Getter
    @Setter
    String writeTime;
    @Getter
    @Setter
    String writerId;

    @Getter
    String category;
    @Getter
    String title;
    @Getter
    String content;

    @Getter
    @Setter
    Post reply;

    public Post(long id, String category, String title, String content) {
        this.postId = id;
        this.category = category;
        this.title = title;
        this.content = content;
    }


}

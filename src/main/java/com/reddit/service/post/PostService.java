package com.reddit.service.post;

import com.reddit.dto.post.PostRequest;
import com.reddit.dto.post.PostResponse;

import java.util.List;

public interface PostService {
    void createPost(PostRequest postRequest);

    PostResponse getPostById(Long postId);

    List<PostResponse> getAllPost();

    List<PostResponse> getPostBySubreddit(Long subredditId);

    List<PostResponse> getPostByUsername(String userName);
}

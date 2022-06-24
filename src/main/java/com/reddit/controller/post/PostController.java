package com.reddit.controller.post;

import com.reddit.dto.post.PostRequest;
import com.reddit.dto.post.PostResponse;
import com.reddit.model.Post;
import com.reddit.service.post.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.createPost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostById(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPost());
    }

    @GetMapping("/by-subreddit/{subredditId}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long subredditId){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostBySubreddit(subredditId));

    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<PostResponse>> getPostByUsername(String userName){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostByUsername(userName));

    }

}

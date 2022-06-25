package com.reddit.controller.comment;

import com.reddit.dto.comment.CommentsDto;
import com.reddit.model.Comment;
import com.reddit.service.comment.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RequestMapping("/api/comments/")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
         commentService.createComment(commentsDto);
         return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{userName}/")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable String userName){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAllCommentsByUser(userName));
    }

}

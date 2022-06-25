package com.reddit.service.comment;

import com.reddit.dto.comment.CommentsDto;
import com.reddit.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    void createComment(CommentsDto commentDto);

    List<CommentsDto> getAllCommentsForPost(Long postId);

    List<CommentsDto>  getAllCommentsByUser(String userName);
}

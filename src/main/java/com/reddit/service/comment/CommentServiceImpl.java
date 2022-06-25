package com.reddit.service.comment;

import com.reddit.config.mail.MailContentBuilder;
import com.reddit.config.mail.MailService;
import com.reddit.config.mail.NotificationEmail;
import com.reddit.config.security.admin.UserInfo;
import com.reddit.config.security.admin.UserServiceInfo;
import com.reddit.dto.comment.CommentsDto;
import com.reddit.exception.SpringRedditException;
import com.reddit.mapper.CommentMapper;
import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserServiceInfo userServiceInfo;
    private final CommentMapper commentMapper;

    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final UserRepository userRepository;


    @Override
    public void createComment(CommentsDto commentsDto) {
       Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(() -> new SpringRedditException("No post found with id: " + commentsDto.getPostId()));
       UserInfo currentUser = this.userServiceInfo.authenticateUser();
       if (currentUser == null){
           throw new SpringRedditException("UnAuthorize user ");
       }
       //Comment comment = commentMapper.map(commentsDto, post, currentUser.getUser()); //mapstruct not generation post id
       this.mapCommentDtoToCommentEntity(commentsDto, post, currentUser.getUser());
       String message = this.mailContentBuilder.build(post.getUser() + " posted a comment on your post ");
       sendCommentNotification(message, post.getUser());
    }

    @Override
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new SpringRedditException("Post id not found " + postId));
        return this.commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentsDto> getAllCommentsByUser(String userName) {
        User user = this.userRepository.findByUsername(userName).orElseThrow(()-> new SpringRedditException("User not found " + userName));
        System.out.println(user + " THIS IS USER ");
        return this.commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    private void sendCommentNotification(String message, User user) {
      mailService.sendSimpleMessage(new NotificationEmail(user.getUsername() + "commented on your post",user.getEmail(), message));
    }

    private void mapCommentDtoToCommentEntity(CommentsDto commentsDto, Post post, User user) {
        Comment comment = new Comment();
        comment.setText(commentsDto.getText());
        comment.setUser(post.getUser());
        comment.setCreatedDate( java.time.Instant.now() );
        comment.setPost(post);
        commentRepository.save(comment);
    }
}

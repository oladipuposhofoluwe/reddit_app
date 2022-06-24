package com.reddit.service.post;

import com.reddit.config.security.admin.UserInfo;
import com.reddit.config.security.admin.UserServiceInfo;
import com.reddit.dto.post.PostRequest;
import com.reddit.dto.post.PostResponse;
import com.reddit.exception.SpringRedditException;
import com.reddit.mapper.PostMapper;
import com.reddit.model.Post;
import com.reddit.model.Subreddit;
import com.reddit.model.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import com.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private  final PostMapper postMapper;
    private final UserRepository userRepository;
    private final UserServiceInfo userServiceInfo;
    @Override
    public void createPost(PostRequest postRequest) {
            Optional<Subreddit> subreddit = Optional.ofNullable(subredditRepository.findByName(postRequest.getSubredditName())
                    .orElseThrow(() -> new SpringRedditException(postRequest.getPostName() + " Not found ")));

        UserInfo currentUser = userServiceInfo.authenticateUser();
        if (currentUser == null){
            throw new SpringRedditException("Unauthorized User");
        }
        postRepository.save(postMapper.map(postRequest, subreddit.get(), currentUser.getUser()));
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new SpringRedditException("Post with "+postId+ "now found"));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getAllPost() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getPostBySubreddit(Long subredditId) {
        Subreddit subreddit = this.subredditRepository.findById(subredditId)
                .orElseThrow(()-> new SpringRedditException("Subreddit now found"));
        System.out.println("SUB REDDIT FOUNG ");
        List<Post> posts = this.postRepository.findAllBySubreddit(subreddit);
        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getPostByUsername(String userName) {
        User user = this.userRepository
                .findByUsername(userName)
                .orElseThrow(() -> new SpringRedditException("User not found " + userName));

       return this.postRepository.findByUser(user).stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }


}

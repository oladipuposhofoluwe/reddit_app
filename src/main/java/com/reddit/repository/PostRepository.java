package com.reddit.repository;//package com.reddit.app.repository;

import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.Subreddit;
import com.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ServiceLoader;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);

    List<Post> findAllByUser(User user);
}

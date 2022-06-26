package com.reddit.repository;//package com.reddit.app.repository;

import com.reddit.model.Post;
import com.reddit.model.User;
import com.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);

    Collection<Vote> findByVoteId(Vote vote);
}

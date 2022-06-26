package com.reddit.service.vote;

import com.reddit.config.security.admin.UserInfo;
import com.reddit.config.security.admin.UserServiceInfo;
import com.reddit.dto.vote.VoteDto;
import com.reddit.exception.SpringRedditException;
import com.reddit.model.Post;
import com.reddit.model.Vote;
import com.reddit.repository.PostRepository;
import com.reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.reddit.model.VoteType.UPVOTE;

@AllArgsConstructor
@Service
public class VoteServiceImpl implements VoteService{
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserServiceInfo userServiceInfo;

    @Override
    public void votePost(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(()-> new SpringRedditException("post NOT FOUND with ID: " + voteDto.getPostId()));
        UserInfo currentUser = userServiceInfo.authenticateUser();
        if (currentUser == null){
            throw new SpringRedditException("UnAuthorized user...");
        }
        Optional<Vote> voteByPostAndUser = this.voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser.getUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + " for this post ");
        }if (UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(this.mapVoteDtoToVote(voteDto, post, currentUser));
        postRepository.save(post);
    }

    private Vote mapVoteDtoToVote(VoteDto voteDto, Post post, UserInfo currentUser) {

        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(currentUser.getUser())
                .build();
//        Vote vote = new Vote();
//        vote.setVoteType(voteDto.getVoteType());
//        vote.setPost(post);
//        vote.setUser(currentUser.getUser());
//        return vote;
    }
}

package com.reddit.service.vote;

import com.reddit.dto.vote.VoteDto;

public interface VoteService {
    void votePost(Long postId, VoteDto voteDto);
}

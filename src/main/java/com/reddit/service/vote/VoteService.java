package com.reddit.service.vote;

import com.reddit.dto.vote.VoteDto;

public interface VoteService {
    void votePost(VoteDto voteDto);
}

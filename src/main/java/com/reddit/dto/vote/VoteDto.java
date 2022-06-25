package com.reddit.dto.vote;

import com.reddit.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jboss.jandex.VoidType;

@Data
@AllArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}

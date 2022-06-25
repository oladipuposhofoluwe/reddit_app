package com.reddit.controller.vote;

import com.reddit.dto.vote.VoteDto;
import com.reddit.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/vote/")
@RestController
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> vote(@PathVariable Long postId, @RequestBody VoteDto voteDto){
        voteService.votePost(postId, voteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

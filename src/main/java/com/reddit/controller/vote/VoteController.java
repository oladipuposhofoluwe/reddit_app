package com.reddit.controller.vote;

import com.reddit.dto.vote.VoteDto;
import com.reddit.service.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/votes")
@RestController
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
        voteService.votePost(voteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

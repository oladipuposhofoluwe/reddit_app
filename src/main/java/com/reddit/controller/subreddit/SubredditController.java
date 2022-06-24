package com.reddit.controller.subreddit;//package com.reddit.app.controller.subreddit;



import com.reddit.dto.subreddit.SubredditDto;
import com.reddit.service.subreddit.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {
    private final SubredditService subRedditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){
     return ResponseEntity.status(HttpStatus.CREATED).body(this.subRedditService.createSubreddit(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getASllSubreddits(){
      return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getASllSubreddits());
    }

    @GetMapping("/{subredditId}")
    public ResponseEntity<SubredditDto> getSubredditByid(@PathVariable Long subredditId){
        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getSubredditByid(subredditId));
    }
}

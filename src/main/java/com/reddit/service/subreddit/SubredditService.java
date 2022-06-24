package com.reddit.service.subreddit;//package com.reddit.app.service.subreddit;

import com.reddit.dto.subreddit.SubredditDto;

import java.util.List;

public interface SubredditService {
    SubredditDto createSubreddit(SubredditDto subredditDto);
    List<SubredditDto> getASllSubreddits();

    SubredditDto getSubredditByid(Long subredditId);
}

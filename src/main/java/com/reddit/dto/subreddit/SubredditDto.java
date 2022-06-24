package com.reddit.dto.subreddit;//package com.reddit.app.dto.subreddit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDto {
    private long id;
    private String name;
    private String description;
    private int numberOfPosts;
}

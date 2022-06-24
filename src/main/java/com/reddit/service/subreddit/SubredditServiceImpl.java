package com.reddit.service.subreddit;//package com.reddit.app.service.subreddit;

import com.reddit.dto.subreddit.SubredditDto;
import com.reddit.exception.SpringRedditException;
import com.reddit.mapper.SubredditMapper;
import com.reddit.model.Subreddit;
import com.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SubredditServiceImpl implements SubredditService{

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    @Transactional
    @Override
    public SubredditDto  createSubreddit(SubredditDto subredditDto) {
      Subreddit subreddit = subredditRepository.save(Objects.requireNonNull(subredditMapper.mapDtoToSubreddit(subredditDto)));
      subredditDto.setId(subreddit.getId());
      return subredditDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubredditDto> getASllSubreddits() {
       return subredditRepository.findAll().stream()
                .map(subredditMapper::mapSubredditDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubredditDto getSubredditByid(Long subredditId) {
       Subreddit subreddit = subredditRepository.findById(subredditId)
               .orElseThrow(()-> new SpringRedditException("No subreddit found with the id " + subredditId));
        return subredditMapper.mapSubredditDto(subreddit);
    }

//    private SubredditDto mapToDto(Subreddit subreddit) {
////        SubredditDto subredditDto = new SubredditDto();
////        subredditDto.setId(subreddit.getId());
////        subredditDto.setName(subreddit.getName());
////        subredditDto.setNumberOfPosts(subreddit.getPosts().size());
////        return subredditDto;
//
//       return SubredditDto.builder()
//                .id(subreddit.getId())
//                .name(subreddit.getName())
//                .numberOfPosts(subreddit.getPosts().size())
//                .build();
//    }

//    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
//        return Subreddit.builder()
//                .name(subredditDto.getName())
//                .description(subredditDto.getDescription())
//                .build();
////
////        Subreddit subreddit = new Subreddit();
////        subreddit.setName(subredditDto.getName());
////        subreddit.setDescription(subredditDto.getDescription());
////        return subreddit;
//    }
}

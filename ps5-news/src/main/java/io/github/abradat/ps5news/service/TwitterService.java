package io.github.abradat.ps5news.service;

import io.github.abradat.ps5news.common.ResultStatus;
import io.github.abradat.ps5news.common.ServiceResult;
import io.github.abradat.ps5news.model.Tweet;
import io.github.abradat.ps5news.model.dto.FindAllTweetsResponse;
import io.github.abradat.ps5news.repository.TweetRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterService {
    private TweetRepository tweetRepository;
    private DozerBeanMapper mapper;

    @Autowired
    public TwitterService(TweetRepository tweetRepository, DozerBeanMapper mapper) {
        this.tweetRepository = tweetRepository;
        this.mapper = mapper;
    }

    public ServiceResult<List<FindAllTweetsResponse>> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findTop25ByOrderByPublishDateDesc();
        if(tweets == null) {
            return new ServiceResult<>(null, ResultStatus.NOT_FOUND, "TWEETS WERE NOT FOUND");
        } else {
            List<FindAllTweetsResponse> responseList = new ArrayList<>();
            for(Tweet tweet: tweets) {
                responseList.add(mapper.map(tweet, FindAllTweetsResponse.class));
            }
            return new ServiceResult<>(responseList, ResultStatus.SUCCESSFUL, "TWEETS FOUND SUCCESSFULLY");
        }
    }
}

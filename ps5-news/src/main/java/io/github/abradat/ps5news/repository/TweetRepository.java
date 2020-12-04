package io.github.abradat.ps5news.repository;

import io.github.abradat.ps5news.model.Tweet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends PagingAndSortingRepository<Tweet, Long> {
    List<Tweet> findTop25ByOrderByPublishDateDesc();
    Tweet findTweetById(long id);
}

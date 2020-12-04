package io.github.abradat.ps5news.service;

import io.github.abradat.ps5news.model.Tweet;
import io.github.abradat.ps5news.repository.TweetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TwitterCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterCrawler.class);

    @Value("${oauth.consumer-key}")
    private String consumerKey;

    @Value("${oauth.consumer-secret}")
    private String consumerSecret;

    @Value("${oauth.access-token}")
    private String accessToken;

    @Value("${oauth.access-token-secret}")
    private String accessTokenSecret;

    TwitterFactory twitterFactory;
    SentimentAnalyzer sentimentAnalyzer;
    TweetRepository tweetRepository;

    @Autowired
    public TwitterCrawler(SentimentAnalyzer sentimentAnalyzer, TweetRepository tweetRepository) {
        this.sentimentAnalyzer = sentimentAnalyzer;
        this.tweetRepository = tweetRepository;
    }

    @PostConstruct
    public void init() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
        twitterFactory = new TwitterFactory(configurationBuilder.build());
        crawlTweets("ps5");
    }

    public void crawlTweets(String topic) {
        Twitter twitter = twitterFactory.getInstance();
        Query query = new Query(topic + " -filter:retweets -filter:links -filter:replies -filter:images");
        query.setCount(25);
        query.setLocale("en");
        query.setLang("en");
        List< Status > tweets = Collections.emptyList();
        ArrayList<Tweet> tweetsToSave = new ArrayList<Tweet>();

        try {
            QueryResult queryResult = twitter.search(query);
            tweets = queryResult.getTweets();
        } catch (TwitterException e) {
            LOGGER.error("TWITTER ERROR");
        }
        for (Status tweet: tweets) {
            if(tweetRepository.findTweetById(tweet.getId()) != null) {
                continue;
            }
            Tweet tmpTweet = new Tweet();
            tmpTweet.setId(tweet.getId());
            tmpTweet.setPublishDate(tweet.getCreatedAt());
            tmpTweet.setTweetText(tweet.getText());
            tmpTweet.setUsername(tweet.getUser().getName());
            tmpTweet.setSentiment(sentimentAnalyzer.analyzeSentiment(tweet.getText()));
            tweetsToSave.add(tmpTweet);
        }
        tweetRepository.saveAll(tweetsToSave);
        return;
    }

//    public void saveTweets(List<Tweet> tweets) {
//        for (Tweet tweet: tweets) {
//            if(tweetRepository.findTweetById(tweet.getId()) == null) {
//
//            }
//        }
//    }
}

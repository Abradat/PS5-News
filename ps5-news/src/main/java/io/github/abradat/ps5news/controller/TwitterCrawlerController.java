package io.github.abradat.ps5news.controller;

import io.github.abradat.ps5news.common.ServiceResult;
import io.github.abradat.ps5news.model.dto.FindAllTweetsResponse;
import io.github.abradat.ps5news.service.TwitterCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/api/twitter/actions")
public class TwitterCrawlerController {

    private TwitterCrawler twitterCrawler;

    @Autowired
    public TwitterCrawlerController(TwitterCrawler twitterCrawler) {
        this.twitterCrawler = twitterCrawler;
    }

    @GetMapping(path = "/crawl", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FindAllTweetsResponse>> crawlTwitter() {
        ServiceResult result = twitterCrawler.crawlTweets();
        return new ResponseEntity<>((List<FindAllTweetsResponse>) result.getData(), HttpStatus.OK);
    }
}

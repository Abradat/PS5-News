package io.github.abradat.ps5news.controller;

import io.github.abradat.ps5news.common.ResultStatus;
import io.github.abradat.ps5news.common.ServiceResult;
import io.github.abradat.ps5news.model.dto.FindAllTweetsResponse;
import io.github.abradat.ps5news.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/api/twitter/tweets")
public class TwitterController {

    private TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FindAllTweetsResponse>> getAllTweets() {
        ServiceResult result = twitterService.getAllTweets();
        if(result.getResultStatus() == ResultStatus.SUCCESSFUL) {
            return new ResponseEntity<>((List<FindAllTweetsResponse>) result.getData(), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException(result.getDescription());
        }
    }
}

package io.github.abradat.ps5news.controller;

import io.github.abradat.ps5news.common.EntityNotFoundException;
import io.github.abradat.ps5news.common.ResultStatus;
import io.github.abradat.ps5news.common.ServiceResult;
import io.github.abradat.ps5news.model.dto.FindAllNewsResponse;
import io.github.abradat.ps5news.service.CnnCrawler;
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
@RequestMapping(path = "/api/cnn/actions")
public class CnnCrawlerController {

    private CnnCrawler cnnCrawler;

    @Autowired
    public CnnCrawlerController(CnnCrawler cnnCrawler) {
        this.cnnCrawler = cnnCrawler;
    }

    @GetMapping(path = "/crawl", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FindAllNewsResponse>> crawlCnn() {
        ServiceResult result = cnnCrawler.crawlNews();
        return new ResponseEntity<>((List<FindAllNewsResponse>) result.getData(), HttpStatus.OK);
    }
}

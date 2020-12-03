package io.github.abradat.ps5news.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CnnCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CnnCrawler.class);

    @Value("${crawler.cnn.base-url}")
    private String cnnUrl;

    @PostConstruct
    public void init() {

    }
}

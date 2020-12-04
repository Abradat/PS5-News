package io.github.abradat.ps5news.controller;

import io.github.abradat.ps5news.common.EntityNotFoundException;
import io.github.abradat.ps5news.common.ResultStatus;
import io.github.abradat.ps5news.common.ServiceResult;
import io.github.abradat.ps5news.model.dto.FindAllNewsResponse;
import io.github.abradat.ps5news.model.dto.FindNewsResponse;
import io.github.abradat.ps5news.service.CnnNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/api/cnn/news")
public class CnnNewsController {

    private CnnNewsService cnnNewsService;

    @Autowired
    public CnnNewsController(CnnNewsService cnnNewsService) {
        this.cnnNewsService = cnnNewsService;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FindAllNewsResponse>> getAllNews() {
        ServiceResult result = cnnNewsService.getAlLNews();
        if(result.getResultStatus() == ResultStatus.SUCCESSFUL) {
            return new ResponseEntity<>((List<FindAllNewsResponse>) result.getData(), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException(result.getDescription());
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FindNewsResponse> findNews(@PathVariable("id") long id) {
        ServiceResult result = cnnNewsService.getNews(id);
        if(result.getResultStatus() == ResultStatus.SUCCESSFUL) {
            return new ResponseEntity<>((FindNewsResponse) result.getData(), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException(result.getDescription());
        }
    }
}

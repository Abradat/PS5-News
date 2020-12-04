package io.github.abradat.ps5news.service;

import io.github.abradat.ps5news.common.ResultStatus;
import io.github.abradat.ps5news.common.ServiceResult;
import io.github.abradat.ps5news.model.CnnNews;
import io.github.abradat.ps5news.model.dto.FindAllNewsResponse;
import io.github.abradat.ps5news.model.dto.FindNewsResponse;
import io.github.abradat.ps5news.repository.CnnNewsRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CnnNewsService {
    private CnnNewsRepository cnnNewsRepository;
    private DozerBeanMapper mapper;

    @Autowired
    public CnnNewsService(CnnNewsRepository cnnNewsRepository,
                          DozerBeanMapper mapper) {
        this.cnnNewsRepository = cnnNewsRepository;
        this.mapper = mapper;
    }

    public ServiceResult<List<FindAllNewsResponse>> getAlLNews() {
        List<CnnNews> news = cnnNewsRepository.findTop25ByOrderByPublishDateDesc();
        if(news == null) {
            return new ServiceResult<>(null, ResultStatus.NOT_FOUND, "NEWS WERE NOT FOUND");
        } else {
            List<FindAllNewsResponse> responseList = new ArrayList<>();
            for (CnnNews result: news) {
                responseList.add(mapper.map(result, FindAllNewsResponse.class));
            }
            return new ServiceResult<>(responseList, ResultStatus.SUCCESSFUL, "NEWS FETCHED SUCCESSFULLY");
        }
    }

    public ServiceResult<FindNewsResponse> getNews(long id) {
        CnnNews news = cnnNewsRepository.findCnnNewsById(id);
        if(news == null) {
            return new ServiceResult<>(null, ResultStatus.NOT_FOUND, "NEWS WITH SUCH ID WAS NOT FOUND");
        } else {
            FindNewsResponse response = mapper.map(news, FindNewsResponse.class);
            return new ServiceResult<>(response, ResultStatus.SUCCESSFUL, "NEWS FOUND SUCCESSFULLY");
        }
    }
}

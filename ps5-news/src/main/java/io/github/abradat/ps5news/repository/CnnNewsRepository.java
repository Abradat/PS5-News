package io.github.abradat.ps5news.repository;

import io.github.abradat.ps5news.model.CnnNews;
import io.github.abradat.ps5news.model.dto.FindAllNewsResponse;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnnNewsRepository extends PagingAndSortingRepository<CnnNews, Long> {
    CnnNews findCnnNewsById(long id);
    CnnNews findCnnNewsByUrl(String url);
    List<CnnNews> findTop25ByOrderByPublishDateDesc();
}

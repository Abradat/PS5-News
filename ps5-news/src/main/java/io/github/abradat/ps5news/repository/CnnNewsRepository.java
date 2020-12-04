package io.github.abradat.ps5news.repository;

import io.github.abradat.ps5news.model.CnnNews;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CnnNewsRepository extends PagingAndSortingRepository<CnnNews, Long> {
    CnnNews findCnnNewsById(long id);
    CnnNews findCnnNewsByUrl(String url);
}

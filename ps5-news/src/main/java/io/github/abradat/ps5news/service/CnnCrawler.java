package io.github.abradat.ps5news.service;

import com.google.common.base.Function;
import io.github.abradat.ps5news.common.ResultStatus;
import io.github.abradat.ps5news.common.ServiceResult;
import io.github.abradat.ps5news.model.CnnNews;
import io.github.abradat.ps5news.model.dto.FindAllNewsResponse;
import io.github.abradat.ps5news.repository.CnnNewsRepository;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.dozer.DozerBeanMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CnnCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CnnCrawler.class);

    @Value("${crawler.cnn.base-url}")
    private String cnnUrl;

    @Value("${selenium.url}")
    private String seleniumUrl;

    private WebDriver driver;
    private CnnNewsRepository cnnNewsRepository;
    private DozerBeanMapper mapper;
    private boolean firstTime;

    @Autowired
    public CnnCrawler(CnnNewsRepository cnnNewsRepository,
                      DozerBeanMapper mapper) {
        this.cnnNewsRepository = cnnNewsRepository;
        this.mapper = mapper;
        this.firstTime = true;
    }

    @PostConstruct
    public void init() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
    }

    public ServiceResult<List<FindAllNewsResponse>> crawlNews(){
        DesiredCapabilities dcap = DesiredCapabilities.chrome();
        try {
            this.driver = new RemoteWebDriver(new URL(seleniumUrl), dcap);
        } catch (MalformedURLException e) {
            LOGGER.error("REMOTE NOT SUCCESSFUL");
        }
        LOGGER.info("STARTING CRAWLING CNN");
        this.driver.get("https://edition.cnn.com/search?q=ps5&size=25");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        if(firstTime) {
            WebElement button = wait.until(new Function<WebDriver, WebElement>() {
                @NullableDecl
                @Override
                public WebElement apply(@NullableDecl WebDriver webDriver) {
                    return driver.findElement(By.id("onetrust-accept-btn-handler"));
                }
            });
            this.firstTime = false;
            button.click();
        }

        Document doc = wait.until(new Function<WebDriver, Document>() {
            @NullableDecl
            @Override
            public Document apply(@NullableDecl WebDriver webDriver) {
                return Jsoup.parse(driver.getPageSource());
            }
        });
        this.driver.close();
        Elements results = doc.getElementsByClass("cnn-search__result");
        ArrayList<CnnNews> cnnNewsList = new ArrayList<CnnNews>();
        for(Element result: results) {
            String title = result.getElementsByClass("cnn-search__result-headline").first()
                    .getElementsByTag("a").first().
                    text();
            String url = "https://" + result.getElementsByClass("cnn-search__result-headline").first()
                    .getElementsByTag("a").first().attr("href").substring(2);
            String headerImageUrl = "http://" + result.getElementsByClass("cnn-search__result-thumbnail").first()
                    .getElementsByTag("img").first().attr("src").substring(2);
            String publishDateString = result.getElementsByClass("cnn-search__result-publish-date").first()
                    .getAllElements().first().getElementsByIndexEquals(1).first().text();
            String body = getNewsBody(url);
            Date publishDate = getNewsDate(publishDateString);
            LOGGER.info("String is: " + publishDateString);
            LOGGER.info(publishDate.toString());
            if(cnnNewsRepository.findCnnNewsByUrl(url) != null) {
                continue;
            }
            CnnNews cnnNews = new CnnNews();
            cnnNews.setTitle(title);
            cnnNews.setUrl(url);
            cnnNews.setHeaderImageUrl(headerImageUrl);
            cnnNews.setPublishDate(publishDate);
            cnnNews.setBody(body);

            cnnNewsList.add(cnnNews);
        }
        this.cnnNewsRepository.saveAll(cnnNewsList);
        List<FindAllNewsResponse> responseList = new ArrayList<>();
        for(CnnNews cnnNews: cnnNewsList) {
            responseList.add(mapper.map(cnnNews, FindAllNewsResponse.class));
        }
        LOGGER.info("CNN CRAWLED");
        return new ServiceResult<>(responseList, ResultStatus.SUCCESSFUL, "CRAWLED SUCCESSFULLY");
    }

    public String getNewsBody(String newsUrl) {
        try {
            Document newsDoc = Jsoup.connect(newsUrl).get();
            Element bodyElement = newsDoc.getElementsByTag("main").first();
            if(bodyElement != null) {
                return bodyElement.html();
            } else {
                return newsDoc.getElementsByClass("l-container").first().html();
            }
        } catch (IOException e) {
            return "";
        }

    }

    public Date getNewsDate(String publishDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        try {
            return formatter.parse(publishDate);
        } catch (ParseException e) {
            return null;
        }
    }
}

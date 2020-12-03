package io.github.abradat.ps5news.service;

import com.google.common.base.Function;
import io.github.abradat.ps5news.model.CnnNews;
import io.github.abradat.ps5news.repository.CnnNewsRepository;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
public class CnnCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CnnCrawler.class);

    @Value("${crawler.cnn.base-url}")
    private String cnnUrl;

    private WebDriver driver;
    private CnnNewsRepository cnnNewsRepository;

    @Autowired
    public CnnCrawler(CnnNewsRepository cnnNewsRepository) {
        this.cnnNewsRepository = cnnNewsRepository;
    }

    @PostConstruct
    public void init() {
        System.setProperty("webdriver.chrome.driver", "/Users/abradat/Desktop/Work/Code/Cor-Paul/driver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        this.driver = new ChromeDriver(options);
//        this.driver = new ChromeDriver();
//        this.crawlNews();
    }

    public void crawlNews(){
        this.driver.get("https://edition.cnn.com/search?q=ps5&size=25");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        WebElement button = wait.until(new Function<WebDriver, WebElement>() {
            @NullableDecl
            @Override
            public WebElement apply(@NullableDecl WebDriver webDriver) {
                return driver.findElement(By.id("onetrust-accept-btn-handler"));
            }
        });
        button.click();

        Document doc = wait.until(new Function<WebDriver, Document>() {
            @NullableDecl
            @Override
            public Document apply(@NullableDecl WebDriver webDriver) {
//                return driver.findElements(By.className("cnn-search__result"));
                return Jsoup.parse(driver.getPageSource());
            }
        });
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
            LOGGER.info(title);
            CnnNews cnnNews = new CnnNews();
            cnnNews.setTitle(title);
            cnnNews.setUrl(url);
            cnnNews.setHeaderImageUrl(headerImageUrl);
            cnnNews.setPublishDate(publishDate);
            cnnNews.setBody(body);

            cnnNewsList.add(cnnNews);
        }
        this.cnnNewsRepository.saveAll(cnnNewsList);
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
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, YYYY");
        try {
            return formatter.parse(publishDate);
        } catch (ParseException e) {
            return null;
        }
    }
}

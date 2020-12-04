# PS5 NEWS
## Introduction
This project is developed for crawling recent articles from CNN and recent tweets from people about the latest released console, PS5.

## Components

  - **Spring Boot Server Application:** This application is responsible for crawling data from CNN and Twitter. Also, it exposes REST endpoints for fetching crawled data.
  - **Angular Application:** The GUI web application for viewing crawled data and requesting for further crawls.
  - **Selenium:** Selenium provides powerful features for web browsing automation. It is used for crawling the CNN website. 
  - **PostgreSQL:** This databased is used for storing crawled data from CNN and Twitter.

## Data Crawling
### CNN Crawling
The latest articles about PS5 is fetched from search section of CNN's website. Like many other modern websites, CNN pages are dynamic. It makes AJAX calls for receiving the latest search results. Hence, utilizing traditional parsers are not useful for crawling the search results.

Selenium can be utilized for automated crawling complex websites. it gives the user full control of the browser. For example, in crawling search results, the results will arrive after some time. So, the result elements are not available, and the user has to wait for the elements to be completely loaded. Selenium **wait** functions gives the user the ability to wait for some elements to load.

After the elements are loaded, their title, URL, and title image's URL are extracted. The crawler then sends request to CNN for the article's page and extracts its body. Fetched data get persisted in database for further use.

### Twitter Crawling
Instead of using selenium, Twitter's Developer API is used for crawling data. Each time the latest 25 tweets containing the word **PS5** are fetched from Twitter. Username, publish date, and text of the tweets are extracted from the results.

Then, the body of tweet is analyzed by **Stanford Core NLP** for extracting the tweet's sentiment and labeling it in the GUI application. There are five possible values including, [VERY POSITIVE, POSITIVE, NEUTRAL, NEGATIVE, and VERY_NEGATIVE]. Tweet's sentiment is persisted with other fields extracted to the database.

## Data Presentation
Data collected is available through REST endpoints exposed by the Spring Boot application and Angular application. Angular Application is a simple dashboard, containing two menus for CNN and Twitter. They both also supports re-crawling the latest 25 news and tweets about PS5. Tweets are labled with their sentiments.

Re-Crawl procedure is blocking, and user has to wait for the crawl to finish. As the number of users tweeting about PS5 are far more than articles being published on CNN, re-crawling twitter in short time periods often will receive new results.

## Running
### Running Back-End Services
Spring boot application, Selenium, and PostgreSQL are dockerized. After cloning the project simply run:
```
docker-compose up -d
```

For running the Angular application, first run ```npm install``` for installing the dependencies. Then run ```npm start```.

**NOTE:** You have to change the BASE_URL environment variable to your desired URL.

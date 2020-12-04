import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Tweet, TweetData} from "../data/Tweet";
import {Observable} from "rxjs";

@Injectable()
export class TwitterService extends TweetData{

  constructor(private http: HttpClient) {
    super();
  }

  getTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>('http://185.235.40.19:8080/api/twitter/tweets');
  }

  crawlTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>('http://185.235.40.19:8080/api/twitter/actions/crawl');
  }
}

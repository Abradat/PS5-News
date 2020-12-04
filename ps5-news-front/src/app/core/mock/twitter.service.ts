import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Tweet, TweetData} from "../data/Tweet";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable()
export class TwitterService extends TweetData{

  constructor(private http: HttpClient) {
    super();
  }

  getTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(environment.BASE_URL + '/api/twitter/tweets');
  }

  crawlTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(environment.BASE_URL + '/api/twitter/actions/crawl');
  }
}

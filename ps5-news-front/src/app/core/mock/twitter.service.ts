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
    return this.http.get<Tweet[]>('http://localhost:8080/api/twitter/tweets');
  }
}

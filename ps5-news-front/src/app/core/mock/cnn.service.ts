import { Injectable } from '@angular/core';
import {News, NewsData} from "../data/News";
import {HttpClient} from "@angular/common/http";
import { Observable , of as observableOf} from 'rxjs';
import {environment} from "../../../environments/environment";

@Injectable()
export class CnnService extends NewsData {

  private currentNews: News;
  constructor(private http: HttpClient) {
    super();
  }

  getAllNews(): Observable<News[]> {
    return this.http.get<News[]>(environment.BASE_URL + '/api/cnn/news');
  }

  getNews(id: number): Observable<News> {
    return this.http.get<News>(environment.BASE_URL + '/api/cnn/news/' + id);
  }
  setCurrentNews(news): void {
    this.currentNews = news;
  }
  getCurrentNews(): Observable<News> {
    return observableOf(this.currentNews);
  }

  crawlNews(): Observable<News[]> {
    return this.http.get<News[]>(environment.BASE_URL + '/api/cnn/actions/crawl');
  }
}

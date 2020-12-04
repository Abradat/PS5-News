import {Observable} from "rxjs";

export interface News {
    id: number,
    url: string,
    title: string,
    publishDate: Date,
    headerImageUrl: string,
    body: string,
}

export abstract class NewsData {
    abstract getAllNews(): Observable<News[]>;
    abstract getNews(newsId: number): Observable<News>;
    abstract crawlNews(): Observable<News[]>;
}

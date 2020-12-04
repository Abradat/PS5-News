import {Observable} from "rxjs";

export interface Tweet {
    username: string,
    tweetText: string,
    sentiment: string,
    publishDate: Date,
}

export abstract class TweetData {
    abstract getTweets(): Observable<Tweet[]>;
}

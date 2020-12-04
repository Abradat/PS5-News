import { Component, OnInit } from '@angular/core';
import {TwitterService} from "../../../core/mock/twitter.service";
import {Tweet} from "../../../core/data/Tweet";

@Component({
  selector: 'mp01-tweet-list',
  templateUrl: './tweet-list.component.html',
  styleUrls: ['./tweet-list.component.scss']
})
export class TweetListComponent implements OnInit {

  constructor(private twitterService: TwitterService) { }
  tweets: Tweet[];
  crawlSpinner = false;

  ngOnInit(): void {
    this.twitterService.getTweets().subscribe(
        (data) => {
          this.tweets = data;
        },
    );
  }

  crawlTweets() {
    this.crawlSpinner = true;
    this.twitterService.crawlTweets().subscribe(
        (data) => {
          this.twitterService.getTweets().subscribe(
              (finalData) => {
                this.tweets = finalData;
                this.crawlSpinner = false;
              },
          );
        },
    );
  }
}

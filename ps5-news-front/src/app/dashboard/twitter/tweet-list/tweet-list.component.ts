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
  ngOnInit(): void {
    this.twitterService.getTweets().subscribe(
        (data) => {
          this.tweets = data;
        },
    );
  }
}

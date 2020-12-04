import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TwitterRoutingModule } from './twitter-routing.module';
import { TweetListComponent } from './tweet-list/tweet-list.component';
import {NbBadgeModule, NbCardModule} from "@nebular/theme";

@NgModule({
  declarations: [TweetListComponent],
  imports: [
    CommonModule,
    TwitterRoutingModule,
    NbCardModule,
    NbBadgeModule
  ]
})
export class TwitterModule { }

import { Component, OnInit } from '@angular/core';
import {CnnService} from "../../../core/mock/cnn.service";
import {ActivatedRoute, Router} from "@angular/router";
import {News} from "../../../core/data/News";

@Component({
  selector: 'mp01-cnn-list',
  templateUrl: './cnn-list.component.html',
  styleUrls: ['./cnn-list.component.scss']
})
export class CnnListComponent implements OnInit {

  constructor(private cnnService: CnnService,
              private router: Router,
              private route: ActivatedRoute) { }
  news: News[];
  ngOnInit(): void {
    this.cnnService.getAllNews().subscribe(
        (data) => {
          this.news = data;
        },
    );
  }

  showDetails(news: News) {
    this.cnnService.setCurrentNews(news);
    this.router.navigate(['./', news.id], {relativeTo: this.route});
  }

}

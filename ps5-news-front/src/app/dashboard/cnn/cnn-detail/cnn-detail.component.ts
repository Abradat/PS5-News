import { Component, OnInit } from '@angular/core';
import {CnnService} from "../../../core/mock/cnn.service";
import {ActivatedRoute} from "@angular/router";
import {News} from "../../../core/data/News";

@Component({
  selector: 'mp01-cnn-detail',
  templateUrl: './cnn-detail.component.html',
  styleUrls: ['./cnn-detail.component.scss']
})
export class CnnDetailComponent implements OnInit {

  selectedNews: News;
  constructor(private cnnService: CnnService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params.id;
    this.cnnService.getNews(id).subscribe(
        (data) => {
          this.selectedNews = data;
        },
    );
  }

}

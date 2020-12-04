import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CnnDetailComponent} from "./cnn-detail/cnn-detail.component";
import {CnnListComponent} from "./cnn-list/cnn-list.component";

const routes: Routes = [
  {
    path: '',
    component: CnnListComponent,
  },
  {
    path: ':id',
    component: CnnDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CnnRoutingModule { }

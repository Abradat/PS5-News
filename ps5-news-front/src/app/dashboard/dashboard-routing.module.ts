import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashboardComponent} from './dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      {
        path: '',
        redirectTo: 'cnn',
        pathMatch: 'full',
      },
      {
        path: 'cnn',
        loadChildren: () => import('./cnn/cnn.module')
          .then((m) => m.CnnModule),
      },
      {
        path: 'twitter',
        loadChildren: () => import('./twitter/twitter.module')
          .then((m) => m.TwitterModule),
      },
      {
        path: '**',
        redirectTo: 'cnn',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import {NbMenuModule} from '@nebular/theme';
import {SharedModule} from '../shared/shared.module';
import { CnnModule } from './cnn/cnn.module';
import { TwitterModule } from './twitter/twitter.module';

@NgModule({
  declarations: [DashboardComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    NbMenuModule,
    SharedModule,
    CnnModule,
    TwitterModule,
  ]})
export class DashboardModule { }

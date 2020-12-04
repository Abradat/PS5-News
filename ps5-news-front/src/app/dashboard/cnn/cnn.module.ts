import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CnnRoutingModule } from './cnn-routing.module';
import {NbButtonModule, NbCardModule, NbSpinnerModule} from "@nebular/theme";
import { CnnDetailComponent } from './cnn-detail/cnn-detail.component';
import { CnnListComponent } from './cnn-list/cnn-list.component';

@NgModule({
  declarations: [CnnDetailComponent, CnnListComponent],
  imports: [
    CommonModule,
    CnnRoutingModule,
    NbCardModule,
    NbButtonModule,
    NbSpinnerModule,
  ],
})
export class CnnModule { }

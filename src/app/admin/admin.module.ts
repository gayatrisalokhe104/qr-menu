import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { OrdersComponent } from './components/orders/orders.component';
import { FoodComponent } from './components/food/food.component';
import { MatModule } from '../_materials/mat.module';
import { CustomSidenavComponent } from './components/custom-sidenav/custom-sidenav.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    OrdersComponent,
    FoodComponent,
    CustomSidenavComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class AdminModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FoodComponent } from './components/food/food.component';
import { OrdersComponent } from './components/orders/orders.component';
import { authGuardGuard } from '../_guards/auth-guard.guard';

const routes: Routes = [
  { 
    path: 'admin', component: AdminComponent, canActivate:[authGuardGuard], children: [
    { path: 'dashboard', component: DashboardComponent, canActivate:[authGuardGuard] },
    { path: 'food', component: FoodComponent, canActivate:[authGuardGuard] },
    { path: 'orders', component: OrdersComponent, canActivate:[authGuardGuard] }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }

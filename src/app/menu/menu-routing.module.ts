import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './menu.component';
import { CartComponent } from './components/cart/cart.component';
import { MyOrderComponent } from './components/my-order/my-order.component';
import { MenuItemsComponent } from './components/menu-items/menu-items.component';

const routes: Routes = [
  {
    path: 'menu', component: MenuComponent, children: [
      { path: 'card', component: MenuItemsComponent},
      { path: 'cart', component: CartComponent },
      { path: 'my-order', component: MyOrderComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenuRoutingModule { }

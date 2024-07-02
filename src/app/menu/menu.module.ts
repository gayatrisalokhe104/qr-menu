import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuRoutingModule } from './menu-routing.module';
import { CartComponent } from './components/cart/cart.component';
import { MyOrderComponent } from './components/my-order/my-order.component';
import { HeaderComponent } from './components/header/header.component';
import { MenuComponent } from './menu.component';
import { MatModule } from '../_materials/mat.module';
import { MenuItemsComponent } from './components/menu-items/menu-items.component';
import { QrCodeComponent } from './components/qr-code/qr-code.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    CartComponent,
    MyOrderComponent,
    HeaderComponent,
    MenuComponent,
    MenuItemsComponent,
    QrCodeComponent
  ],
  imports: [
    CommonModule,
    MenuRoutingModule,
    MatModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class MenuModule { }

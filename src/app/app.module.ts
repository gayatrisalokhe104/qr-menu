import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuModule } from './menu/menu.module';
import { AdminModule } from './admin/admin.module';
import { AuthModule } from './auth/auth.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatModule } from './_materials/mat.module';
import { ToastrModule } from 'ngx-toastr';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { PromptComponent } from './_popups/prompt/prompt.component';
import { AddCategoryComponent } from './_popups/add-category/add-category.component';
import { AddMenuItemComponent } from './_popups/add-menu-item/add-menu-item.component';
import { OrderDetailsComponent } from './_popups/order-details/order-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TokenInterceptor } from './_interceptor/token.interceptor';
import { CartService } from './_services/cart.service';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    PromptComponent,
    AddCategoryComponent,
    AddMenuItemComponent,
    OrderDetailsComponent
  ],
  imports: [
    BrowserModule,
    MenuModule,
    AdminModule,
    AuthModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatModule,
    HttpClientModule,
    ToastrModule.forRoot(),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    CartService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

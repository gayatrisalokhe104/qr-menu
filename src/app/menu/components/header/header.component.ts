import { Component } from '@angular/core';
import { CartService } from 'src/app/_services/cart.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  menu: boolean=true;

  constructor(
    private cart: CartService
  ){}

  getCartItemLength(): number{
    return this.cart.getCartItemLength()
  }

  changeIcon(){
    if(this.menu){
      this.menu=false;
    }
    else{
      this.menu=true;
    }
  }
}

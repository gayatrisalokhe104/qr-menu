import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { orderFood } from 'src/app/_models/order-food.model';
import { ApiService } from 'src/app/_services/api.service';
import { CartService } from 'src/app/_services/cart.service';

@Component({
  selector: 'app-menu-items',
  templateUrl: './menu-items.component.html',
  styleUrls: ['./menu-items.component.scss']
})
export class MenuItemsComponent implements OnInit {
  
  menuItems: any;
  public productList: any;
  public filterCategory: any;
  searchKey: string = '';
  tableNo: any;

  // orderItems: any[] = []

  constructor(
    private api: ApiService,
    private cart: CartService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.api.getMenu().subscribe((res) => {
      this.menuItems = res;
      console.log(this.menuItems);
    });

    this.activatedRoute.params.subscribe((val) => {
      this.tableNo = val['id'];

      const tableNoExists: boolean = localStorage.getItem('TableNO') !== null;
      if (tableNoExists) {
        
        this.router.navigate(['menu/card']);
      } else {
        this.api.storeTableNO(this.tableNo);
        console.log('table no', this.api.getTableNo());
        this.router.navigate(['menu/card']);
      }
      
    });
}

addtocart(food: any) {
  const item: orderFood = {
    name: food.name,
    price: food.price,
    quantity: 1,
    note:'',
    showNote:false
  };
  this.cart.addtoCart(item);
}

}

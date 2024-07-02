import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/_services/api.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  totalCategories: any;
  ordersSize: any;

  constructor(
    private api: ApiService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.api.getAllCategries().subscribe(res => {
      this.totalCategories = res
      console.log(this.totalCategories);
      
    })
    this.api.getAllOrders().subscribe(res => {
      this.ordersSize=res;
    })
  }

  getFoodSize(): number {
    let totalFoods = 0;
    if (this.totalCategories && Array.isArray(this.totalCategories)) {
      this.totalCategories.forEach((category: any) => {
        if (category && category.foodList && Array.isArray(category.foodList)) {
          totalFoods += category.foodList.length;
        }
      });
    }
    return totalFoods;
  }

  foodPage(){
    this.router.navigate(['admin/food'])
  }
  ordersPage(){
    this.router.navigate(['admin/orders'])
  }
}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from 'src/app/_services/api.service';

@Component({
  selector: 'app-my-order',
  templateUrl: './my-order.component.html',
  styleUrls: ['./my-order.component.scss'],
})
export class MyOrderComponent implements OnInit {
  myOrder: any;

  constructor(
    private api: ApiService,
    private router: Router,
    private tostr: ToastrService
  ) {}

  ngOnInit(): void {
    const orderID = this.api.getOrderId();
    const OrderId = orderID ? parseInt(orderID, 10) : 0;

    this.api.getOrder(OrderId).subscribe(
      (res) => {
        this.myOrder = res;
        console.log(this.myOrder);
      },
      (error) => {
        if (error && error.status === 404) {
          this.router.navigate(['menu/card']);
          this.tostr.error('First Order The Food', 'Error', { timeOut: 1000 });
        }
      }
    );
  }

  getStatus(status: boolean): string {
    if (status) {
      return 'green';
    }
    return 'red';
  }
}

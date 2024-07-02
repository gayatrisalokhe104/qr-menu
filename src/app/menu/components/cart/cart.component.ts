import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Order } from 'src/app/_models/order.model';
import { ApiService } from 'src/app/_services/api.service';
import { CartService } from 'src/app/_services/cart.service';
import { environment } from 'src/environment/enviroment';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit{

  paymentHandler: any = null;
  stripeToken!: any;

  contactForm!: FormGroup;
  idFoodsVisible: boolean = true;

  orderData: any[] = [];
  total = this.getTotal();
  paymentSuccess = false;

  isFormVisible: boolean = false;

  public orderFoods: any[] = [];
  public grandTotal!: number;

  constructor(
    private cart: CartService,
    private router: Router,
    private fb: FormBuilder,
    private api: ApiService,
    private toastr: ToastrService
  ) {}
  
  ngOnInit(): void {
    this.orderData = this.cart.getOrderData();
    console.log(this.orderData, 'order data');

    this.cart.getOrderFoods().subscribe((res) => {
      this.orderFoods = res;
    });

    this.contactForm = this.fb.group({
      customerName: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]*$'), Validators.minLength(3), Validators.maxLength(40)]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]+$'), Validators.minLength(10), Validators.maxLength(11)]]
    });

    this.getTotal();
    this.invokeStripe();
  }

  makePayment() {
    if(this.contactForm.valid){
      const paymentHandler = (<any>window).StripeCheckout.configure({
        key: environment.stripeKey,
        locale: 'auto',
        token: function (stripeToken: any) {
          console.log(stripeToken,'stToken');
          this.stripeToken = stripeToken;
          createOrder(stripeToken);
        },
      });
    
    const createOrder = (stripeToken: any) => {
      const tableNoString = this.api.getTableNo();
      const tableNumber = tableNoString ? parseInt(tableNoString, 10) : 0;
  
      const orderPayload: Order = {
        customerName: this.contactForm.value.customerName,
        phone: this.contactForm.value.phone,
        totalAmount: this.getTotal(),
        tableNumber: tableNumber,
        orderFoods: this.orderFoods,
        token: stripeToken
      };
     
      this.api.createOrder(orderPayload).subscribe((res) => {
          this.toastr.success("Order Placed Successfully...", "Success", { timeOut: 1000 });
          this.api.storeOrderId(res.orderId);
          this.cart.removeAllCart();
          this.router.navigate(['menu/my-order']);
        
      },
      (error) => {
        if (error && error.status === 500) {
          this.toastr.error("Something Went Wrong", "Payment Failed", { timeOut: 2000 });
        } 
      }
      );
    
    }
    paymentHandler.open({
      name: 'Trio Restro',
      description: 'Food Payment',
      amount: this.getTotal() * 100,
      currency: 'inr'
    }
    );
  }
    
  }
  invokeStripe() {
    if (!window.document.getElementById('stripe-script')) {
      const script = window.document.createElement('script');
      script.id = 'stripe-script';
      script.type = 'text/javascript';
      script.src = 'https://checkout.stripe.com/checkout.js';
      script.onload = () => {
        this.paymentHandler = (<any>window).StripeCheckout.configure({
          key: environment.stripeKey,
          locale: 'auto',
          token: function (stripeToken: any) {
            this.stripeToken = stripeToken;
            console.log(stripeToken);
            alert('Payment has been successfull!');
          },
        });
      };
      window.document.body.appendChild(script);
    }
  }

  removeItem(item: any) {
    this.cart.removeCartItem(item);
  }
  emptycart() {
    this.cart.removeAllCart();
  }

  decreseQuantity(i: number) {
    if (this.orderFoods[i].quantity === 1) {
      this.orderFoods.splice(i, 1);
      this.total = this.getTotal();
      this.router.navigate(['menu/cart']);
    } else if (this.orderFoods[i].quantity > 0) {
      this.orderFoods[i].quantity -= 1;
      this.total = this.total - this.orderFoods[i].price;
    }
  }

  increaseQuantity(i: number) {
    this.orderFoods[i].quantity += 1;
    this.total = this.total + this.orderFoods[i].price;
  }
  getTotal(): number {
    let sum = 0;
    if (Array.isArray(this.orderFoods)) {
      for (let food of this.orderFoods) {
        sum += food.price * food.quantity;
      }
    }
    return sum;
  }
  getCartItems() {
    this.idFoodsVisible = true;
    this.isFormVisible = false;
  }

  toggleNote(i: number) {
    this.orderFoods[i].showNote = !this.orderFoods[i].showNote;
  }

}

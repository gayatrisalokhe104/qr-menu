import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Stripe, loadStripe } from '@stripe/stripe-js';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environment/enviroment';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private stripePromise!: Promise<Stripe | null>;
  
  private orderDataSubject: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  orderData$ = this.orderDataSubject.asObservable();

  public cartItemList : any =[]
  public orderFoods = new BehaviorSubject<any>([]);
  public search = new BehaviorSubject<string>("");

  constructor(
    private toastr: ToastrService,
    private snakebar: MatSnackBar,
    private router: Router
  ){
    this.stripePromise = this.loadStripe()
  }
  
  private loadStripe(): Promise<Stripe | null> {
    return loadStripe(environment.stripeKey) // Replace with your Stripe publishable key
      .catch(error => {
        console.error('Error loading Stripe:', error);
        return null;
      });
  }

  getStripe(): Promise<Stripe | null> {
    return this.stripePromise;
  }
  
  setOrderData(data: any): void {
    const currentData = this.orderDataSubject.getValue();
    this.orderDataSubject.next([...currentData, data]);
  }

  getOrderData(): any[] {
    return this.orderDataSubject.getValue();
  }


  getOrderFoods(){
    return this.orderFoods.asObservable();
  }

  setOrderFoods(food : any){
    this.cartItemList.push(...food);
    this.orderFoods.next(food);
  }
  addtoCart(food : any){
      const isAlreadyInCart = this.cartItemList.some((item: { name: string; }) => item.name === food.name);
    
      if (!isAlreadyInCart) {
        this.cartItemList.push(food);
        this.orderFoods.next(this.cartItemList);
        console.log(this.cartItemList)
        this.snakebar.open("Food Added To Cart", "VIEW CART", {
          duration: 2000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom'
        }).onAction().subscribe(() => {
          // Call your function here
          this.goToCart();
        })

      } else {
        this.toastr.warning("food is already in a cart", "Already Exist", {timeOut:1000})
      }
    }

    goToCart(){
      this.router.navigate(['menu/cart'])
    }
    
  removeCartItem(food: any){
    this.cartItemList.map((a:any, index:any)=>{
      if(food.id=== a.id){
        this.cartItemList.splice(index,1);
      }
    })
  }
  
  getCartItemLength(): number{
    return this.cartItemList.length
  }

  removeAllCart(){
    this.cartItemList = []
    this.orderFoods.next(this.cartItemList);
  }
}

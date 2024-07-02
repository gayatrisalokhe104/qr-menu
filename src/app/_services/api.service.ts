import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environment/enviroment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl: string = environment.CONNECTION_URL

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  storeTableNO(tableNo: string) {
    localStorage.setItem('TableNO', tableNo);
  }
  
  getTableNo() {
    return localStorage.getItem('TableNO');
  }

  //SAve order id
  storeOrderId(OrderId: string) {
    localStorage.setItem('OrderId', OrderId);
    this.autoCleanOrderID();
  }
  
  getOrderId() {
    return localStorage.getItem('OrderId');
  }

  removeOrderID() {
    localStorage.removeItem("OrderId");
  }

  private autoCleanOrderID() {
    const expiryTime = 2 * 60 * 60 * 1000; // 2 hours 
    setTimeout(() => {
      this.removeOrderID();
      this.router.navigate(['menu/card']) 
    }, expiryTime);
  }

  // get Menu

  getMenu() {
    return this.http.get<any>(`${this.baseUrl}/v1/user/menu`);
  }

  createOrder(data: any) {
    return this.http.post<any>(`${this.baseUrl}/v1/user/order/create`, data);
  }

  getOrder(id: number) {
    return this.http.get<any>(`${this.baseUrl}/v1/user/order/${id}`)
  }


  // get All Orders Admin
  getAllOrders() {
    return this.http.get<any>(`${this.baseUrl}/v1/admin/order/getAll`)
  }
  
  deleteOrder(id: number) {
    return this.http.delete<any>(`${this.baseUrl}/v1/admin/order/${id}/delete`)
  }

  deleteAllOrders() {
    return this.http.delete<any>(`${this.baseUrl}/v1/admin/order/deleteAll`)
  }

  // Category API's

  addCategory(data: any) {
    return this.http.post<any>(`${this.baseUrl}/v1/admin/category/create-category`, data);
  }

  updateCategory(id: number, data: any) {
    return this.http.put<any>(`${this.baseUrl}/v1/admin/category/${id}/update`, data)
  }

  deleteCategory(id: number) {
    return this.http.delete<any>(`${this.baseUrl}/v1/admin/category/${id}/delete`)
  }

  getAllCategries() {
    return this.http.get<any>(`${this.baseUrl}/v1/admin/category/menu`);
  }

  // Food API'S

  addFood(id: number, data: any) {
    return this.http.post<any>(`${this.baseUrl}/v1/admin/food/category/${id}/create-food`, data);
  }

  updateFood(id: number, data: any){
    return this.http.put<any>(`${this.baseUrl}/v1/admin/food/${id}/update`, data)
  }

  deleteFood(id: number) {
    return this.http.delete<any>(`${this.baseUrl}/v1/admin/food/${id}/delete`)
  }

}

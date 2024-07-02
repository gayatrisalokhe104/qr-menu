import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environment/enviroment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl: string = environment.CONNECTION_URL;

  constructor(private http: HttpClient, private router: Router) {}

  // Signup API
  register(data: any) {
    return this.http.post<any>(`${this.baseUrl}/auth/signUpAdmin`, data);
  }

  // Login API
  login(data: any) {
    return this.http.post<any>(`${this.baseUrl}/auth/signIn`, data);
  }

  // Store Token
  storeToken(tokenValue: string) {
    localStorage.setItem('Token', tokenValue);
    this.scheduleAutoSignOut()
  }
  getToken() {
    return localStorage.getItem('Token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('Token');
  }

  signOut() {
    localStorage.removeItem("Token");
    localStorage.removeItem("Name");
    this.router.navigate(['auth/login']);
  }

  // Store Name
  storeName(name: string) {
    localStorage.setItem('Name', name);
  }
  getName() {
    return localStorage.getItem('Name');
  }

  private scheduleAutoSignOut() {
    const expiryTime = 12 * 60 * 60 * 1000; // 12 hours
    setTimeout(() => {
      this.signOut();
    }, expiryTime);
  }
}

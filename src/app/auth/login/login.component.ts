import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/_services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  loginForm!: FormGroup

  constructor(
    private auth: AuthService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]]
    })
  }

  onSubmit(){
    if(this.loginForm.valid){
      this.auth.login(this.loginForm.value).subscribe(res => {
        this.auth.storeToken(res.accessToken)
        this.auth.storeName(res.userName)
        this.toastr.success('Log In Success..', 'Success', {timeOut: 1000})
        this.loginForm.reset();
        this.router.navigate(['admin/dashboard'])
      },
      (error) => {
        if (error && error.status === 500) {
          this.toastr.error("Something Went Wrong..", "Error");
        } 
      })
    }
  }


}

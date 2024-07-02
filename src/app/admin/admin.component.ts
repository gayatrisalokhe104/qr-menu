import { Component, computed, signal } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { PromptService } from '../_services/prompt.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent {

  collapsed = signal(false);
  sidenavWidth = computed(()=> this.collapsed() ? '65px' : '250px')

  constructor(
    private auth: AuthService,
    private prompt: PromptService,
    private toastr: ToastrService,
    private router: Router
  ){}

  logout() {
    const data = {
      title : "Confirm Logout",
      text: "Are You Sure Want To Logout ?"
    }
    this.prompt.openPrompt(data).subscribe(res => {
      if(res) {
          localStorage.removeItem('Token')
          this.toastr.success('Logout Successfully...', 'Success', { timeOut:1000 });
          this.router.navigate(['auth/login'])
         }else {
        this.toastr.info("Action Cancelled", "Info", { timeOut:700 })
      }
    })    
  }
}

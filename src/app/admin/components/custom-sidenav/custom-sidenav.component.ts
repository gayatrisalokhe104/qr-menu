import { Component, Input, computed, signal } from '@angular/core';
import { AuthService } from 'src/app/_services/auth.service';


export type MenuItems = {
  icon: string,
  label: string,
  route: string
}

@Component({
  selector: 'app-custom-sidenav',
  templateUrl: './custom-sidenav.component.html',
  styleUrls: ['./custom-sidenav.component.scss']
})
export class CustomSidenavComponent {

  sideNavCollapsed = signal(false)
  @Input() set collapsed(val: boolean){
    this.sideNavCollapsed.set(val);
  }

  constructor(
    private auth: AuthService
  ){}
  
  userName = this.auth.getName()
  profilePicSize = computed(()=> this.sideNavCollapsed() ? '32' : '100')

  menuItems = signal<MenuItems[]>([
    {
      icon: "dashboard",
      label: "Dashboard",
      route: "dashboard"
    },
    {
      icon: 'fastfood',
      label: 'Food',
      route: 'food'
    },
    {
      icon: 'payment',
      label: 'Orders',
      route: 'orders'
    }
  ])
}

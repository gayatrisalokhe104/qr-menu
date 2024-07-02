import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

export const authGuardGuard: CanActivateFn = (route, state) => {

  let router = inject(Router);
  let toastr = inject(ToastrService);

  if(!localStorage.getItem('Token')) {
    toastr.error("You Are Not Authorized..!", "Error", {timeOut: 1000})
    router.navigate(['auth/login'])
  }
  return true;
};

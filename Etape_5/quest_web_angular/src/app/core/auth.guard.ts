import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);
 
  const localData = localStorage.getItem('angular17token');
  if (localData && localData.length > 2) {
    return true;
  } else {
    console.log('angular17token not found or not valid');
    router.navigateByUrl('/login');
    return false;
  }


};
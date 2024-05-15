import { HttpInterceptorFn } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {
  debugger;

  if (req.url.endsWith('/register')) {
    return next(req);
  }
  const myToken = localStorage.getItem('angular17token');

  const cloneRequest =  req.clone({
    setHeaders:{
      Authorization: `Bearer ${myToken}`
    }
  });
  return next(cloneRequest);
};
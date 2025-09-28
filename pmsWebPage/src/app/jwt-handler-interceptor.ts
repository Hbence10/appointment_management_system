import { HttpInterceptorFn } from '@angular/common/http';

export const jwtHandlerInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};

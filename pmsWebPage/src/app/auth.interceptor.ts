import { HttpEvent, HttpEventType, HttpHandlerFn, HttpHeaders, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable, tap } from "rxjs";
import { UserService } from "./services/user-service";

export function asdInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const userService = inject(UserService)
  let cloneReq = req.clone()

  let httpHeaders = new HttpHeaders();
  if (req.url.includes("http://localhost:8080/users/login")) {
    httpHeaders = httpHeaders.append('Authorization', 'Basic ' + btoa(req.params.get("username") + ':' + req.params.get("password")));
  } else {
    if (userService.token != "") {
      httpHeaders = httpHeaders.append('Authorization', userService.token);
    }
  }

  cloneReq = req.clone({
    headers: httpHeaders
  })

  console.log(cloneReq)
  return next(cloneReq)
}

import { HttpEvent, HttpHandlerFn, HttpHeaders, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { UserService } from "./services/user-service";

export function asdInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const userService = inject(UserService)
  let cloneReq = req.clone()
  let body: any = cloneReq.body


  let httpHeaders = new HttpHeaders();
  if (req.url.includes("http://localhost:8080/users/login")) {
    httpHeaders = httpHeaders.append('Authorization', 'Basic ' + btoa(body.username + ':' + body.password));
  } else {
    if (userService.token != "") {
      httpHeaders = httpHeaders.append('Authorization', userService.token);
    }
  }

  cloneReq = req.clone({
    headers: httpHeaders
  })

  return next(cloneReq)
}

import { HttpEvent, HttpEventType, HttpHandlerFn, HttpHeaders, HttpRequest } from "@angular/common/http";
import { Observable, tap } from "rxjs";

export function asdInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  let cloneReq = req.clone()
  if (req.url.includes("http://localhost:8080/users/login")) {
    let httpHeaders = new HttpHeaders();

    httpHeaders = httpHeaders.append('Authorization', 'Basic ' + btoa('securityTest' + ':' + 'test5.Asd'));
    cloneReq = req.clone({
      headers: httpHeaders
    })
  }

  return next(cloneReq)
}

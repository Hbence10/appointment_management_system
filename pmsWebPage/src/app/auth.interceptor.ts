import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";

export function asdInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const cloneReq = req.clone({
    headers: req.headers.append("Authorization", "Basic " + btoa("securityTest" + ":" + "test5.Asd"))
  })


  console.log(cloneReq)
  return next(cloneReq)
}

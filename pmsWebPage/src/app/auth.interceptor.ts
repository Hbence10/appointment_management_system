import { HttpEvent, HttpHandlerFn, HttpHeaders, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { Observable } from "rxjs";

export function asdInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const cookieService = inject(CookieService)
  let cloneReq = req.clone()
  let body: any = cloneReq.body


  let httpHeaders = new HttpHeaders();
  if (req.url.includes("http://localhost:8080/users/login")) {
    console.log(body.id)
    httpHeaders = httpHeaders.append('Authorization', 'Basic ' + btoa(body.username + ':' + body.password));
  } else {
    if (cookieService.get("pmsToken") != "") {
      httpHeaders = httpHeaders.append('Authorization', cookieService.get("pmsJwtToken"));
    }
  }

  cloneReq = req.clone({
    headers: httpHeaders
  })

  return next(cloneReq)
}

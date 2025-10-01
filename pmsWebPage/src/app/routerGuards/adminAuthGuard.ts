import { inject, Injectable } from "@angular/core";
import { CanMatch, RedirectCommand, Route, Router, UrlSegment } from "@angular/router";
import { UserService } from "../services/user-service";

@Injectable({
  providedIn: "root"
})
export class adminAuthGuard implements CanMatch {
  userService = inject(UserService)
  router = inject(Router)

  canMatch(route: Route, segments: UrlSegment[]) {
    if (this.userService.user()?.getRole.getName == "ROLE_admin") {
      return true
    }

    return new RedirectCommand(this.router.parseUrl("/unauthorized"))
  }
}

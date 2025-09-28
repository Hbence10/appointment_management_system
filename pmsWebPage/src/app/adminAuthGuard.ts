import { inject, Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, CanMatch, GuardResult, MaybeAsync, RedirectCommand, Route, Router, RouterStateSnapshot, UrlSegment } from "@angular/router";
import { UserService } from "./services/user-service";

@Injectable({
  providedIn: "root"
})
export class adminAuthGuard implements CanMatch {
  userService = inject(UserService)
  router = inject(Router)

  canMatch(route: Route, segments: UrlSegment[]) {
    if (this.userService.user()?.role.name == "ROLE_admin") {
      return true
    }

    return new RedirectCommand(this.router.parseUrl(""))
  }
}

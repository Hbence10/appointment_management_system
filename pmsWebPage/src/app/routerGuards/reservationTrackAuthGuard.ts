import { inject, Injectable } from "@angular/core";
import { CanMatch, GuardResult, MaybeAsync, RedirectCommand, Route, UrlSegment } from "@angular/router";
import { UserService } from "../services/user-service";
import { Router } from "express";

@Injectable({
  providedIn: "root"
})

export class reservationTrackAuthGuard implements CanMatch {
  userService = inject(UserService)
  router = inject(Router)

  canMatch(route: Route, segments: UrlSegment[]) {
    if (this.userService.user() == null) {
      return true
    }

    return new RedirectCommand(this.router.parseUrl("/unauthorized"))
  }
}

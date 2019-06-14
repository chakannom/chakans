import { Injectable, isDevMode } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AccountService, UserProfileService } from '../';
import { RedirectService } from '../redirect/redirect.service';
import { StateStorageService } from '../auth/state-storage.service';

@Injectable({ providedIn: 'root' })
export class UserProfileRouteAccessService implements CanActivate {
  constructor(
    private router: Router,
    private redirectService: RedirectService,
    private accountService: AccountService,
    private userProfileService: UserProfileService,
    private stateStorageService: StateStorageService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const authorities = route.data['authorities'];
    // We need to call the checkSignIn / and so the accountService.identity() function, to ensure,
    // that the client has a principal too, if they already logged in by the server.
    // This could happen on a page refresh.
    return this.checkSignIn(authorities, state.url).then(isSignIn => {
      if (isSignIn) {
        return this.checkExistProfile(state.url);
      }
      return false;
    });
  }

  checkSignIn(authorities: string[], url: string): Promise<boolean> {
    return this.accountService.identity().then(account => {
      if (!authorities || authorities.length === 0) {
        return true;
      }

      if (account) {
        const hasAnyAuthority = this.accountService.hasAnyAuthority(authorities);
        if (hasAnyAuthority) {
          return true;
        }
        if (isDevMode()) {
          console.error('User has not any of required authorities: ', authorities);
        }
        return false;
      }

      // only show the signIn page, if the user hasn't logged in yet
      this.redirectService.goAccountsSignIn();
      return false;
    });
  }

  checkExistProfile(url: string): Promise<boolean> {
    return this.userProfileService.identity().then(myProfile => {
      if (myProfile) {
        return this.go(url, '/blog.cb');
      }
      return this.go(url, '/initial.cb');
    });
  }

  private go(currentUrl: string, navigateUrl: string) {
    if (currentUrl.startsWith(navigateUrl)) {
      return true;
    }
    this.router.navigate([navigateUrl]);
    return false;
  }
}

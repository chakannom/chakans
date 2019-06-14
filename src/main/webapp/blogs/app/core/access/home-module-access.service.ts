import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AccountService } from '../';

@Injectable({ providedIn: 'root' })
export class HomeModuleAccessService implements CanActivate {
  constructor(private router: Router, private accountService: AccountService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    return this.accessible();
  }

  accessible(): Promise<boolean> {
    return this.accountService.identity().then(account => {
      if (account) {
        this.router.navigate(['/blog.cb']);
        return false;
      }
      return true;
    });
  }
}

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { AccountService } from '../auth/account.service';

@Injectable({ providedIn: 'root' })
export class HomeRouteAccessService implements CanActivate {
    constructor(private router: Router, private accountService: AccountService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
        return this.checkLogin();
    }

    checkLogin(): Promise<boolean> {
        return this.accountService.identity().then(account => {
            if (account) {
                this.router.navigate(['/blog.cb']);
                return false;
            }
            return true;
        });
    }
}

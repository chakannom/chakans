import { Injectable } from '@angular/core';
import { PlatformLocation } from '@angular/common';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class ContinueParamValidatorService implements CanActivate {

    constructor(
        private platformLocation: PlatformLocation,
        private router: Router
    ) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
        let continueUrl: string = route.queryParams.continue;
        if (continueUrl === undefined || continueUrl === '') {
            continueUrl =  window.location.protocol + '//' + window.location.host + this.platformLocation.getBaseHrefFromDOM();
            this.router.navigate([route.routeConfig.path], { queryParams : { continue : continueUrl }, queryParamsHandling: 'merge'});
        }
        return this.checkUrl(continueUrl);
    }

    checkUrl(continueUrl: string): Promise<boolean> {
        if (continueUrl.indexOf(window.location.protocol + '//' + window.location.host) !== 0) {
            this.router.navigate(['error']);
            return Promise.resolve(false);
        }
        return Promise.resolve(true);
    }
}

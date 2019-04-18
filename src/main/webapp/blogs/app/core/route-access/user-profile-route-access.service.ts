import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { MyProfile } from '../user/my-profile.service';

@Injectable({ providedIn: 'root' })
export class UserProfileRouteAccessService implements CanActivate {
    constructor(private router: Router, private myProfile: MyProfile) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
        // This could happen on a page refresh.
        return this.checkExistProfile(state.url);
    }

    checkExistProfile(url: string): Promise<boolean> {
        const myProfile = this.myProfile;
        return Promise.resolve(
            myProfile.get().then(userProfile => {
                if (userProfile === null) {
                    return this.go(url, '/initial.cb');
                }
                return this.go(url, '/blog.cb');
            })
        );
    }

    go(currentUrl: string, navigateUrl: string) {
        if (currentUrl.startsWith(navigateUrl)) {
            return true;
        }
        this.router.navigate([navigateUrl]);
        return false;
    }
}

import { Injectable } from '@angular/core';

import { UserProfileService } from './user-profile.service';
import { UserProfile } from './user-profile.model';

@Injectable({ providedIn: 'root' })
export class MyProfile {
    private userProfile: UserProfile;

    constructor(private userProfileService: UserProfileService) {}

    get(): Promise<UserProfile> {
        // check and see if we have retrieved the userProfile data from the server.
        // if we have, reuse it by immediately resolving
        if (this.userProfile) {
            return Promise.resolve(this.userProfile);
        }

        // retrieve the userProfile data from the server, update the userProfile object, and then resolve.
        return this.userProfileService
            .getMyProfile()
            .toPromise()
            .then(response => {
                const userProfile = response.body;
                if (userProfile.nickname) {
                    this.userProfile = userProfile;
                } else {
                    this.userProfile = null;
                }
                return this.userProfile;
            })
            .catch(err => {
                this.userProfile = null;
                return null;
            });
    }

    set(userProfile: UserProfile) {
        this.userProfile = userProfile;
    }
}

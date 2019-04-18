import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserProfileService, UserProfile } from '../../core';

@Component({
    selector: 'cks-blogs-initial',
    templateUrl: './initial.component.html',
    styleUrls: ['initial.css']
})
export class InitialComponent implements OnInit {
    error: string;
    userProfile: UserProfile = {};

    constructor(private userProfileService: UserProfileService, private router: Router) {}

    ngOnInit() {}

    start() {
        this.error = null;
        const userProfile = { nickname: this.userProfile.nickname };
        this.userProfileService
            .patchMyProfile(userProfile, ['nickname'])
            .subscribe(() => this.router.navigate(['/blog.cb']), response => this.processError(response));
    }

    private processError(response) {
        this.error = 'ERROR';
    }
}

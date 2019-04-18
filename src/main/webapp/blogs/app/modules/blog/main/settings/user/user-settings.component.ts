import { Component, OnInit } from '@angular/core';
import { map, mergeMap } from 'rxjs/operators';

import { MyProfile, StorageService, UserProfile, UserProfileService } from '../../../../../core';
import { createImgproxySignatureUrl } from '../../../../../shared';

@Component({
    selector: 'cks-blogs-blog-user-settings',
    templateUrl: './user-settings.component.html',
    styleUrls: ['../../main.css', '../settings.css', './user-settings.css']
})
export class BlogUserSettingsComponent implements OnInit {
    userProfile: UserProfile;
    isSaving: boolean;
    profileImgFile: any;

    constructor(private myProfile: MyProfile, private userProfileService: UserProfileService, private storageService: StorageService) {}

    ngOnInit() {
        this.isSaving = false;
        this.myProfile.get().then(userProfile => {
            this.userProfile = this.copyUserProfile(userProfile);
        });
    }

    savePrivacy() {
        this.isSaving = true;
        const userProfile = { openedProfile: this.userProfile.openedProfile, openedEmail: this.userProfile.openedEmail };
        this.userProfileService
            .patchMyProfile(userProfile, ['openedProfile', 'openedEmail'])
            .subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
    }

    saveProfileImage() {
        this.isSaving = true;
        if (this.userProfile.imageUrl !== '' && this.userProfile.imageUrl.startsWith('data:image/')) {
            this.storageService
                .uploadImageFile(this.profileImgFile)
                .pipe(
                    map(response => response.url.split('?')[0]),
                    map(imgFileUrl => createImgproxySignatureUrl('fit', 120, 120, 'ce', 0, imgFileUrl, 'png')),
                    map(profileImgUrl => (this.userProfile.imageUrl = profileImgUrl)),
                    mergeMap(profileImgUrl => this.userProfileService.patchMyProfile({ imageUrl: profileImgUrl }, ['imageUrl']))
                )
                .subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
        } else {
            this.userProfileService
                .patchMyProfile({ imageUrl: this.userProfile.imageUrl }, ['imageUrl'])
                .subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
        }
    }

    saveUserInfo() {
        this.isSaving = true;
        const userProfile = { email: this.userProfile.email, nickname: this.userProfile.nickname };
        this.userProfileService
            .patchMyProfile(userProfile, ['email', 'nickname'])
            .subscribe(response => this.onSuccessSave(response), () => this.onErrorSave());
    }

    previewImage(event) {
        const reader = new FileReader();
        reader.onload = (e: ProgressEvent & { target: { result: string } }) => {
            this.userProfile.imageUrl = e.target.result;
            this.profileImgFile = event.target.files[0];
        };
        reader.readAsDataURL(event.target.files[0]);
    }

    removeImage() {
        this.userProfile.imageUrl = '';
        this.profileImgFile = null;
    }

    private copyUserProfile(userProfile) {
        return {
            openedProfile: userProfile.openedProfile,
            openedEmail: userProfile.openedEmail,
            imageUrl: userProfile.imageUrl,
            email: userProfile.email,
            nickname: userProfile.nickname
        };
    }

    private onSuccessSave(response) {
        this.isSaving = false;
        this.myProfile.set(response.body);
    }

    private onErrorSave() {
        this.isSaving = false;
    }
}

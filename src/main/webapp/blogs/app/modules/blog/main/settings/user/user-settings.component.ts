import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, mergeMap } from 'rxjs/operators';

import { StorageService, IUserProfile, UserProfileService } from '../../../../../core';
import { createImgproxySignatureUrl } from '../../../../../shared';

@Component({
  selector: 'cks-blog-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['../../main.scss', '../settings.scss', './user-settings.scss']
})
export class BlogUserSettingsComponent implements OnInit {
  isSaving: boolean;
  profileImageFile: File;
  profileImageUrl: string;
  privacyForm = this.fb.group({
    openedProfile: [false],
    openedEmail: [false]
  });
  profileImageForm = this.fb.group({});
  userInfoForm = this.fb.group({
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email, Validators.required]],
    nickname: ['', [Validators.minLength(3), Validators.maxLength(100), Validators.required]]
  });

  constructor(private userProfileService: UserProfileService, private storageService: StorageService, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.userProfileService.identity().then(userProfile => {
      this.privacyForm.setValue({ openedProfile: userProfile.openedProfile, openedEmail: userProfile.openedEmail });
      this.profileImageUrl = userProfile.imageUrl;
      this.userInfoForm.setValue({ email: userProfile.email, nickname: userProfile.nickname });
    });
  }

  savePrivacy() {
    this.isSaving = true;
    const userProfile = {
      openedProfile: this.privacyForm.get(['openedProfile']).value,
      openedEmail: this.privacyForm.get(['openedEmail']).value
    };
    this.userProfileService
      .patchMyProfile(userProfile, ['openedProfile', 'openedEmail'])
      .subscribe(() => this.onSuccessSave(), () => this.onError());
  }

  saveProfileImage() {
    console.log('1');
    this.isSaving = true;
    if (this.profileImageUrl && this.profileImageUrl.startsWith('data:image/')) {
      this.storageService
        .uploadImageFile(this.profileImageFile)
        .pipe(
          map((response: any) => response.url.split('?')[0]),
          map((imgFileUrl: string) => createImgproxySignatureUrl('fit', 120, 120, 'ce', 0, imgFileUrl, 'png')),
          map((profileImageUrl: string) => (this.profileImageUrl = profileImageUrl)),
          mergeMap((profileImageUrl: string) => this.userProfileService.patchMyProfile({ imageUrl: profileImageUrl }, ['imageUrl']))
        )
        .subscribe(() => this.onSuccessSave(), () => this.onError());
    } else {
      this.userProfileService
        .patchMyProfile({ imageUrl: this.profileImageUrl }, ['imageUrl'])
        .subscribe(() => this.onSuccessSave(), () => this.onError());
    }
  }

  saveUserInfo() {
    this.isSaving = true;
    const userProfile = { email: this.userInfoForm.get(['email']).value, nickname: this.userInfoForm.get(['nickname']).value };
    this.userProfileService.patchMyProfile(userProfile, ['email', 'nickname']).subscribe(() => this.onSuccessSave(), () => this.onError());
  }

  previewImage(event) {
    const reader = new FileReader();
    reader.onload = (e: ProgressEvent & { target: { result: string } }) => {
      this.profileImageUrl = e.target.result;
      this.profileImageFile = event.target.files[0];
    };
    reader.readAsDataURL(event.target.files[0]);
  }

  removeImage() {
    this.profileImageUrl = '';
    this.profileImageFile = null;
  }

  private onSuccessSave() {
    this.userProfileService.identity(true).then(() => {
      this.isSaving = false;
    });
  }

  private onError() {
    this.isSaving = false;
  }
}

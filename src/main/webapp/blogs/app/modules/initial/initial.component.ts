import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { UserProfileService } from '../../core';

@Component({
  selector: 'cks-initial',
  templateUrl: './initial.component.html',
  styleUrls: ['initial.scss']
})
export class InitialComponent implements OnInit {
  error: string;
  registerUserProfileForm = this.fb.group({
    nickname: ['', [Validators.minLength(3), Validators.maxLength(100), Validators.required]]
  });

  constructor(private userProfileService: UserProfileService, private router: Router, private fb: FormBuilder) {}

  ngOnInit() {}

  registerUserProfile() {
    if (this.registerUserProfileForm.valid) {
      this.error = null;
      const userProfile = { nickname: this.registerUserProfileForm.get(['nickname']).value };
      this.userProfileService.createMyProfile(userProfile).subscribe(() => this.onSuccess(), response => this.onError(response));
    }
  }

  private onSuccess() {
    this.userProfileService.identity(true).then(() => {
      this.router.navigate(['/blog.cb']);
    });
  }

  private onError(response) {
    this.error = 'ERROR';
  }
}

import { Component, OnInit, AfterViewInit, Renderer2, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { PasswordResetFinishService } from './password-reset-finish.service';

@Component({
  selector: 'cks-password-reset-finish',
  templateUrl: './password-reset-finish.component.html'
})
export class PasswordResetFinishComponent implements OnInit, AfterViewInit {
  doNotMatch: string;
  error: string;
  keyMissing: boolean;
  success: string;
  key: string;

  passwordForm = this.fb.group({
    newPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]]
  });

  constructor(
    private passwordResetFinishService: PasswordResetFinishService,
    private route: ActivatedRoute,
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.key = params['key'];
    });
    this.keyMissing = !this.key;
  }

  ngAfterViewInit() {
    if (this.elementRef.nativeElement.querySelector('#password') != null) {
      setTimeout(() => this.renderer.selectRootElement('#password').focus(), 0);
    }
  }

  finishReset() {
    this.doNotMatch = null;
    this.error = null;
    const password = this.passwordForm.get(['newPassword']).value;
    const confirmPassword = this.passwordForm.get(['confirmPassword']).value;
    if (password !== confirmPassword) {
      this.doNotMatch = 'ERROR';
    } else {
      this.passwordResetFinishService.save({ key: this.key, newPassword: password }).subscribe(
        () => {
          this.success = 'OK';
        },
        () => {
          this.success = null;
          this.error = 'ERROR';
        }
      );
    }
  }

  signIn() {}
}

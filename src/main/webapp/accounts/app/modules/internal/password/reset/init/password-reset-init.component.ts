import { Component, AfterViewInit, Renderer2, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { EMAIL_NOT_FOUND_TYPE } from '../../../../../shared';
import { PasswordResetInitService } from './password-reset-init.service';

@Component({
  selector: 'cks-password-reset-init',
  templateUrl: './password-reset-init.component.html'
})
export class PasswordResetInitComponent implements AfterViewInit {
  error: string;
  errorEmailNotExists: string;
  success: string;
  resetRequestForm = this.fb.group({
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]]
  });

  constructor(
    private passwordResetInitService: PasswordResetInitService,
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private fb: FormBuilder
  ) {}

  ngAfterViewInit() {
    setTimeout(() => this.renderer.selectRootElement('#email').focus(), 0);
  }

  requestReset() {
    this.error = null;
    this.errorEmailNotExists = null;

    this.passwordResetInitService.save(this.resetRequestForm.get(['email']).value).subscribe(
      () => {
        this.success = 'OK';
      },
      response => {
        this.success = null;
        if (response.status === 400 && response.error.type === EMAIL_NOT_FOUND_TYPE) {
          this.errorEmailNotExists = 'ERROR';
        } else {
          this.error = 'ERROR';
        }
      }
    );
  }
}

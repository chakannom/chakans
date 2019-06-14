import { Component, OnInit, AfterViewInit, Renderer2 } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';

import { EMAIL_ALREADY_USED_TYPE } from '../../../shared';
import { SignUpService } from './sign-up.service';

@Component({
  selector: 'cks-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['sign-up.scss']
})
export class SignUpComponent implements OnInit, AfterViewInit {
  confirmPassword: string;
  doNotMatch: string;
  error: string;
  errorEmailExists: string;
  agree: boolean;
  continueUrl: string;
  signUpPolicyForm = this.fb.group({
    all: [false],
    terms: [false, [Validators.required]],
    privacy: [false, [Validators.required]],
    promotionalEmails: [false]
  });
  signUpForm = this.fb.group({
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email, Validators.required]],
    password: ['', [Validators.minLength(4), Validators.maxLength(50), Validators.required]],
    confirmPassword: ['', [Validators.minLength(4), Validators.maxLength(50), Validators.required]]
  });

  constructor(
    private languageService: JhiLanguageService,
    private signUpService: SignUpService,
    private renderer: Renderer2,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    console.log(this.signUpPolicyForm);
    this.route.queryParams.subscribe(queryParams => {
      this.continueUrl = queryParams['continue'];
    });
    this.agree = false;
  }

  ngAfterViewInit() {}

  onChangeAll() {
    const isChecked = this.signUpPolicyForm.get(['all']).value;
    this.signUpPolicyForm.patchValue({ terms: isChecked, privacy: isChecked, promotionalEmails: isChecked });
  }

  onChangeAgreementItem() {
    const isCheckedAll =
      this.signUpPolicyForm.get(['terms']).value &&
      this.signUpPolicyForm.get(['privacy']).value &&
      this.signUpPolicyForm.get(['promotionalEmails']).value;
    this.signUpPolicyForm.patchValue({ all: isCheckedAll });
  }

  agreePolicy() {
    if (this.signUpPolicyForm.valid) {
      this.agree = true;
      setTimeout(() => this.renderer.selectRootElement('#email').focus());
    }
  }

  signUp() {
    if (this.signUpForm.valid) {
      const password = this.signUpForm.get(['password']).value;
      const confirmPassword = this.signUpForm.get(['confirmPassword']).value;
      if (password !== confirmPassword) {
        this.doNotMatch = 'ERROR';
      } else {
        const signUpAccount: any = {
          agreements: this.signUpPolicyForm.value,
          email: this.signUpForm.get(['email']).value,
          password: this.signUpForm.get(['password']).value
        };
        this.doNotMatch = null;
        this.error = null;
        this.errorEmailExists = null;
        this.languageService.getCurrent().then(key => {
          signUpAccount.langKey = key;
          this.signUpService.save(signUpAccount).subscribe(
            () => {
              this.router.navigate(['/signup/result', 'success'], { queryParams: { continue: this.continueUrl } });
            },
            response => this.processError(response)
          );
        });
      }
    }
  }

  private processError(response) {
    if (response.status === 400 && response.json().type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = 'ERROR';
    } else {
      this.error = 'ERROR';
    }
  }
}

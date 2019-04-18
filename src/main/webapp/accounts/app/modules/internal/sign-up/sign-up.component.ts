import { Component, OnInit, AfterViewInit, Renderer2, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';

import { EMAIL_ALREADY_USED_TYPE } from '../../../shared';
import { SignUpService } from './sign-up.service';

@Component({
    selector: 'cks-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['sign-up.css']
})
export class SignUpComponent implements OnInit, AfterViewInit {
    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    signUpAccount: any;
    agree: boolean;
    agreedAll: boolean;
    continueUrl: string;

    constructor(
        private languageService: JhiLanguageService,
        private signUpService: SignUpService,
        private renderer: Renderer2,
        private elementRef: ElementRef,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.route.queryParams.subscribe(queryParams => {
            this.continueUrl = queryParams['continue'];
        });
        this.agree = false;
        this.signUpAccount = {
            agreements: {
                terms: false,
                privacy: false,
                promotional_emails: false
            }
        };
    }

    ngAfterViewInit() {}

    onChangeAll(event) {
        this.signUpAccount.agreements.terms = this.agreedAll;
        this.signUpAccount.agreements.privacy = this.agreedAll;
        this.signUpAccount.agreements.promotional_emails = this.agreedAll;
    }

    onChangeAgreementItem(agreementName, event) {
        if (this.agreedAll && !this.signUpAccount.agreements[agreementName]) {
            this.agreedAll = false;
        } else if (
            !this.agreedAll &&
            this.signUpAccount.agreements.terms &&
            this.signUpAccount.agreements.privacy &&
            this.signUpAccount.agreements.promotional_emails
        ) {
            this.agreedAll = true;
        }
    }

    agreePolicy() {
        this.agree = true;
        setTimeout(() => this.renderer.selectRootElement('#email').focus());
    }

    signUp() {
        if (this.signUpAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then(key => {
                this.signUpAccount.langKey = key;
                this.signUpService.save(this.signUpAccount).subscribe(
                    () => {
                        this.router.navigate(['/signup/result', 'success'], { queryParams: { continue: this.continueUrl } });
                    },
                    response => this.processError(response)
                );
            });
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

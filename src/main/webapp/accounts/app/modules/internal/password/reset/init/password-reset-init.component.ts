import { Component, OnInit, AfterViewInit, Renderer2 } from '@angular/core';
import { EMAIL_NOT_FOUND_TYPE } from '../../../../../shared';
import { PasswordResetInitService } from './password-reset-init.service';
import { NavbarService } from '../../../../../layouts/navbar/navbar.service';

@Component({
    selector: 'cks-password-reset-init',
    templateUrl: './password-reset-init.component.html'
})
export class PasswordResetInitComponent implements OnInit, AfterViewInit {
    error: string;
    errorEmailNotExists: string;
    resetAccount: any;
    success: string;

    constructor(
        private passwordResetInitService: PasswordResetInitService,
        private navbarService: NavbarService,
        private renderer: Renderer2
    ) {}

    ngOnInit() {
        this.resetAccount = {};
    }

    ngAfterViewInit() {
        setTimeout(() => this.renderer.selectRootElement('#email').focus(), 0);
    }

    requestReset() {
        this.error = null;
        this.errorEmailNotExists = null;

        this.passwordResetInitService.save(this.resetAccount.email).subscribe(
            () => {
                this.success = 'OK';
            },
            response => {
                this.success = null;
                if (response.status === 400 && response.json().type === EMAIL_NOT_FOUND_TYPE) {
                    this.errorEmailNotExists = 'ERROR';
                } else {
                    this.error = 'ERROR';
                }
            }
        );
    }
}

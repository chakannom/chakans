import { Component, OnInit } from '@angular/core';

import { AccountService } from '../../../../core';
import { PasswordChangeService } from './password-change.service';

@Component({
    selector: 'cks-password-change',
    templateUrl: './password-change.component.html'
})
export class PasswordChangeComponent implements OnInit {
    doNotMatch: string;
    error: string;
    success: string;
    account: any;
    password: string;
    confirmPassword: string;

    constructor(private passwordService: PasswordChangeService, private accountService: AccountService) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.account = account;
        });
    }

    changePassword() {
        if (this.password !== this.confirmPassword) {
            this.error = null;
            this.success = null;
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.passwordService.save(this.password).subscribe(
                () => {
                    this.error = null;
                    this.success = 'OK';
                },
                () => {
                    this.success = null;
                    this.error = 'ERROR';
                }
            );
        }
    }
}

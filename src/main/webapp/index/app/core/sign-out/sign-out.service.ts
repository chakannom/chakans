import { Injectable } from '@angular/core';

import { AccountService } from '../auth/account.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';

@Injectable({ providedIn: 'root' })
export class SignOutService {
    constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

    do() {
        this.authServerProvider.signOut().subscribe();
        this.accountService.authenticate(null);
    }
}

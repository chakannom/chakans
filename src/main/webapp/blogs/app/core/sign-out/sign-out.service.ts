import { Injectable } from '@angular/core';

import { AccountService } from '../auth/account.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';

@Injectable({ providedIn: 'root' })
export class SignOutService {
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  signOut() {
    this.authServerProvider.signOut().subscribe({ next: null, error: null, complete: () => this.accountService.authenticate(null) });
  }
}

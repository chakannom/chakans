import { Injectable } from '@angular/core';

import { AccountService } from '../auth/account.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';

@Injectable({ providedIn: 'root' })
export class SignInService {
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  signIn(credentials, callback?) {
    const cb = callback || function() {};

    return new Promise((resolve, reject) => {
      this.authServerProvider.signIn(credentials).subscribe(
        data => {
          this.accountService.identity(true).then(account => {
            resolve(data);
          });
          return cb();
        },
        err => {
          this.signOut();
          reject(err);
          return cb(err);
        }
      );
    });
  }

  signInWithToken(jwt, rememberMe) {
    return this.authServerProvider.signInWithToken(jwt, rememberMe);
  }

  signOut() {
    this.authServerProvider.signOut().subscribe({ next: null, error: null, complete: () => this.accountService.authenticate(null) });
  }
}

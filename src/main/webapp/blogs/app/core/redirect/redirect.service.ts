import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class RedirectService {
  constructor() {}

  go(url: string) {
    window.location.href = url;
  }

  goAccountsSignIn() {
    this.go('/accounts/signin?continue=' + encodeURIComponent(window.location.href));
  }
}

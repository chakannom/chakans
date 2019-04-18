import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class SignInService {
    constructor() {}

    do() {
        window.location.href = '/accounts/signin?continue=' + encodeURIComponent(window.location.href);
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_ACCOUNT_ANONYMOUS } from '../../shared/constants/http.constants';

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  constructor(private http: HttpClient, private $localStorage: LocalStorageService, private $sessionStorage: SessionStorageService) {}

  getToken() {
    return this.$localStorage.retrieve('authenticationToken') || this.$sessionStorage.retrieve('authenticationToken');
  }

  signIn(credentials): Observable<any> {
    const data = {
      username: credentials.username,
      password: credentials.password,
      rememberMe: credentials.rememberMe
    };
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ANONYMOUS);
    return this.http
      .post(SERVER_API_URL + 'apis/authenticate', data, { headers: headerOptions, observe: 'response' })
      .pipe(map(authenticateSuccess.bind(this)));

    function authenticateSuccess(resp) {
      const bearerToken = resp.headers.get('Authorization');
      if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
        const jwt = bearerToken.slice(7, bearerToken.length);
        this.storeAuthenticationToken(jwt, credentials.rememberMe);
        return jwt;
      }
    }
  }

  signInWithToken(jwt, rememberMe) {
    if (jwt) {
      this.storeAuthenticationToken(jwt, rememberMe);
      return Promise.resolve(jwt);
    } else {
      return Promise.reject('auth-jwt-service Promise reject'); // Put appropriate error message here
    }
  }

  storeAuthenticationToken(jwt, rememberMe) {
    if (rememberMe) {
      this.$localStorage.store('authenticationToken', jwt);
    } else {
      this.$sessionStorage.store('authenticationToken', jwt);
    }
  }

  signOut(): Observable<any> {
    return new Observable(observer => {
      this.$localStorage.clear('authenticationToken');
      this.$sessionStorage.clear('authenticationToken');
      observer.complete();
    });
  }
}

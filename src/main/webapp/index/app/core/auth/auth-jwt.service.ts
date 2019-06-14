import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  constructor(private $localStorage: LocalStorageService, private $sessionStorage: SessionStorageService) {}

  getToken() {
    return this.$localStorage.retrieve('authenticationToken') || this.$sessionStorage.retrieve('authenticationToken');
  }

  signOut(): Observable<any> {
    return new Observable(observer => {
      this.$localStorage.clear('authenticationToken');
      this.$sessionStorage.clear('authenticationToken');
      observer.complete();
    });
  }
}

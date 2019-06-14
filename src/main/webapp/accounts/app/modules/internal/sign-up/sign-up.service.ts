import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../../app.constants';
import { HEADER_ACCEPT_ACCOUNT_ANONYMOUS } from '../../../shared/constants/http.constants';

@Injectable({ providedIn: 'root' })
export class SignUpService {
  constructor(private http: HttpClient) {}

  save(account: any): Observable<any> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ANONYMOUS);
    return this.http.post(SERVER_API_URL + 'apis/register', account, { headers: headerOptions });
  }
}

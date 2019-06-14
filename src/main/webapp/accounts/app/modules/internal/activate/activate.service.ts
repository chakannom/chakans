import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../../app.constants';
import { HEADER_ACCEPT_ACCOUNT_ANONYMOUS } from '../../../shared/constants/http.constants';

@Injectable({ providedIn: 'root' })
export class ActivateService {
  constructor(private http: HttpClient) {}

  get(key: string): Observable<any> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ANONYMOUS);
    const paramOptions = new HttpParams().set('key', key);
    return this.http.get(SERVER_API_URL + 'apis/activate', { headers: headerOptions, params: paramOptions });
  }
}

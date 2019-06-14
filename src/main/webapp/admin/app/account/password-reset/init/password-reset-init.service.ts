import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../../app.constants';

@Injectable({ providedIn: 'root' })
export class PasswordResetInitService {
  constructor(private http: HttpClient) {}

  save(mail: string): Observable<any> {
    return this.http.post(SERVER_API_URL + 'apis/account/reset-password/init', mail);
  }
}

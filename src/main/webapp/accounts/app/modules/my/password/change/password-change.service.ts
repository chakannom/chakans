import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../../../app.constants';
import { HEADER_ACCEPT_ACCOUNT_ANONYMOUS } from '../../../../shared/constants/http.constants';

@Injectable({ providedIn: 'root' })
export class PasswordChangeService {
    constructor(private http: HttpClient) {}

    save(newPassword: string): Observable<any> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ANONYMOUS);
        return this.http.post(SERVER_API_URL + 'apis/account/change-password', newPassword, { headers: headers });
    }
}

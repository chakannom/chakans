import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../../app.constants';
import { HEADER_ACCEPT_ACCOUNT_ANONYMOUS } from '../../../shared/constants/http.constants';

@Injectable({ providedIn: 'root' })
export class ActivateService {
    constructor(private http: HttpClient) {}

    get(key: string): Observable<any> {
        return this.http.get(SERVER_API_URL + 'apis/activate', {
            headers: new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ANONYMOUS),
            params: new HttpParams().set('key', key)
        });
    }
}

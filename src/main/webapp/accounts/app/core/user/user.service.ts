import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_ACCOUNT_ADMIN } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';
import { IUser } from './user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
    public resourceUrl = SERVER_API_URL + 'apis/users';

    constructor(private http: HttpClient) {}

    create(user: IUser): Observable<HttpResponse<IUser>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
        return this.http.post<IUser>(this.resourceUrl, user, { headers: headers, observe: 'response' });
    }

    update(user: IUser): Observable<HttpResponse<IUser>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
        return this.http.put<IUser>(this.resourceUrl, user, { headers: headers, observe: 'response' });
    }

    find(login: string): Observable<HttpResponse<IUser>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
        return this.http.get<IUser>(`${this.resourceUrl}/${login}`, { headers: headers, observe: 'response' });
    }

    query(req?: any): Observable<HttpResponse<IUser[]>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
        const options = createRequestOption(req);
        return this.http.get<IUser[]>(this.resourceUrl, { headers: headers, params: options, observe: 'response' });
    }

    delete(login: string): Observable<HttpResponse<any>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
        return this.http.delete(`${this.resourceUrl}/${login}`, { headers: headers, observe: 'response' });
    }

    authorities(): Observable<string[]> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
        return this.http.get<string[]>(`${this.resourceUrl}/authorities`, { headers: headers });
    }
}

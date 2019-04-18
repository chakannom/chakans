import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { IUserProfile } from './user-profile.model';

@Injectable({ providedIn: 'root' })
export class UserProfileService {
    public resourceUrl = SERVER_API_URL + 'apis/blog/v1/users';

    constructor(private http: HttpClient) {}

    updateMyProfile(userProfile: IUserProfile): Observable<HttpResponse<IUserProfile>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        return this.http.put(`${this.resourceUrl}/my`, userProfile, { headers: headers, observe: 'response' });
    }

    patchMyProfile(userProfile: IUserProfile, fields: string[]): Observable<HttpResponse<IUserProfile>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Fields', fields);
        return this.http.patch(`${this.resourceUrl}/my`, userProfile, { headers: headers, observe: 'response' });
    }

    getMyProfile(): Observable<HttpResponse<IUserProfile>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        return this.http.get<IUserProfile>(`${this.resourceUrl}/my`, { headers: headers, observe: 'response' });
    }
}

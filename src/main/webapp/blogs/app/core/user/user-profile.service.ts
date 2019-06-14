import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { IUserProfile } from './user-profile.model';

@Injectable({ providedIn: 'root' })
export class UserProfileService {
  private resourceUrl = SERVER_API_URL + 'apis/blog/v1/users';
  private userProfileIdentity: any;
  private userProfileState = new Subject<any>();

  constructor(private http: HttpClient) {}

  createMyProfile(userProfile: IUserProfile): Observable<HttpResponse<any>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http.post(`${this.resourceUrl}/my`, userProfile, { headers: headerOptions, observe: 'response' });
  }

  patchMyProfile(userProfile: IUserProfile, fields: string[]): Observable<HttpResponse<any>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Fields', fields);
    return this.http.patch(`${this.resourceUrl}/my`, userProfile, { headers: headerOptions, observe: 'response' });
  }

  getMyProfile(): Observable<HttpResponse<IUserProfile>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http.get<IUserProfile>(`${this.resourceUrl}/my`, { headers: headerOptions, observe: 'response' });
  }

  identity(force?: boolean): Promise<IUserProfile> {
    if (force) {
      this.userProfileIdentity = undefined;
    }

    // check and see if we have retrieved the userProfileIdentity data from the server.
    // if we have, reuse it by immediately resolving
    if (this.userProfileIdentity) {
      return Promise.resolve(this.userProfileIdentity);
    }

    // retrieve the userProfileIdentity data from the server, update the identity object, and then resolve.
    return this.getMyProfile()
      .toPromise()
      .then(response => {
        const myProfile = response.body;
        if (myProfile) {
          this.userProfileIdentity = myProfile;
        } else {
          this.userProfileIdentity = null;
        }
        this.userProfileState.next(this.userProfileIdentity);
        return this.userProfileIdentity;
      })
      .catch(err => {
        this.userProfileIdentity = null;
        this.userProfileState.next(this.userProfileIdentity);
        return null;
      });
  }
}

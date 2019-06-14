import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_ACCOUNT_ADMIN } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';
import { IUser } from './user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  private resourceUrl = SERVER_API_URL + 'apis/users';

  constructor(private http: HttpClient) {}

  create(user: IUser): Observable<HttpResponse<IUser>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
    return this.http.post<IUser>(this.resourceUrl, user, { headers: headerOptions, observe: 'response' });
  }

  update(user: IUser): Observable<HttpResponse<IUser>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
    return this.http.put<IUser>(this.resourceUrl, user, { headers: headerOptions, observe: 'response' });
  }

  find(login: string): Observable<HttpResponse<IUser>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
    return this.http.get<IUser>(`${this.resourceUrl}/${login}`, { headers: headerOptions, observe: 'response' });
  }

  query(req?: any): Observable<HttpResponse<IUser[]>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl, { headers: headerOptions, params: options, observe: 'response' });
  }

  delete(login: string): Observable<HttpResponse<any>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
    return this.http.delete(`${this.resourceUrl}/${login}`, { headers: headerOptions, observe: 'response' });
  }

  authorities(): Observable<string[]> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_ACCOUNT_ADMIN);
    return this.http.get<string[]>(`${this.resourceUrl}/authorities`, { headers: headerOptions });
  }
}

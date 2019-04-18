import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITheme } from './theme.model';
import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';

@Injectable({ providedIn: 'root' })
export class ThemeService {
    private resourceUrl = SERVER_API_URL + 'apis/blog/v1/themes';

    constructor(private http: HttpClient) {}

    getThemes(langKey: string, req?: any): Observable<HttpResponse<ITheme[]>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Lang-Key', langKey);
        const options = createRequestOption(req);
        return this.http.get<ITheme[]>(this.resourceUrl, { headers: headers, params: options, observe: 'response' });
    }

    getTheme(themeId: number, langKey: string): Observable<HttpResponse<ITheme>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Lang-Key', langKey);
        return this.http.get<ITheme>(`${this.resourceUrl}/${themeId}`, { headers: headers, observe: 'response' });
    }
}

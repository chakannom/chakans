import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBlog } from '../';
import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';

@Injectable({ providedIn: 'root' })
export class BlogService {
    private resourceUrl = SERVER_API_URL + 'apis/blog/v1/blogs';

    constructor(private http: HttpClient) {}

    createBlog(blog: IBlog): Observable<HttpResponse<IBlog>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        return this.http.post<IBlog>(this.resourceUrl, blog, { headers: headers, observe: 'response' });
    }

    patchBlog(blog: IBlog, fields: string[]): Observable<HttpResponse<IBlog>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Fields', fields);
        return this.http.patch<IBlog>(this.resourceUrl, blog, { headers: headers, observe: 'response' });
    }

    getBlogs(req?: any): Observable<HttpResponse<IBlog[]>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        const options = createRequestOption(req);
        return this.http.get<IBlog[]>(this.resourceUrl, { headers: headers, params: options, observe: 'response' });
    }

    getBlog(blogId: number): Observable<HttpResponse<IBlog>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        return this.http.get<IBlog>(`${this.resourceUrl}/${blogId}`, { headers: headers, observe: 'response' });
    }

    checkSubdomainAvailability(subdomain: string): Observable<any> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        return this.http.get<any>(`${this.resourceUrl}/check-subdomain-availability`, {
            headers: headers,
            params: { subdomain: subdomain },
            observe: 'response'
        });
    }
}

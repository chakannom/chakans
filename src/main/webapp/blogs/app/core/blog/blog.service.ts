import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBlog } from './blog.model';
import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';

@Injectable({ providedIn: 'root' })
export class BlogService {
  private resourceUrl = SERVER_API_URL + 'apis/blog/v1/blogs';
  private blogs: IBlog[];

  constructor(private http: HttpClient) {}

  createBlog(blog: IBlog): Observable<HttpResponse<IBlog>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http.post<IBlog>(this.resourceUrl, blog, { headers: headerOptions, observe: 'response' });
  }

  patchBlog(blog: IBlog, fields: string[]): Observable<HttpResponse<IBlog>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Fields', fields);
    return this.http.patch<IBlog>(this.resourceUrl, blog, { headers: headerOptions, observe: 'response' });
  }

  getBlogs(req?: any): Observable<HttpResponse<IBlog[]>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    const paramOptions = createRequestOption(req);
    return this.http.get<IBlog[]>(this.resourceUrl, { headers: headerOptions, params: paramOptions, observe: 'response' });
  }

  getBlog(blogId: number): Observable<HttpResponse<IBlog>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http.get<IBlog>(`${this.resourceUrl}/${blogId}`, { headers: headerOptions, observe: 'response' });
  }

  checkSubdomainAvailability(subdomainValue: string): Observable<any> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http.get<any>(`${this.resourceUrl}/check-subdomain-availability`, {
      headers: headerOptions,
      params: { subdomain: subdomainValue },
      observe: 'response'
    });
  }

  list(force?: boolean): Promise<IBlog[]> {
    if (force === true) {
      this.blogs = undefined;
    }

    // check and see if we have retrieved the userProfile data from the server.
    // if we have, reuse it by immediately resolving
    if (this.blogs) {
      return Promise.resolve(this.blogs);
    }

    // retrieve the userProfile data from the server, update the userProfile object, and then resolve.
    return this.getBlogs({ unpaged: 'true' })
      .toPromise()
      .then(response => {
        const blogs = response.body;
        if (blogs) {
          this.blogs = blogs;
        } else {
          this.blogs = null;
        }
        return this.blogs;
      })
      .catch(err => {
        this.blogs = null;
        return null;
      });
  }
}

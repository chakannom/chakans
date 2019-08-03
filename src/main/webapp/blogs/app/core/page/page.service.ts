import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { delay } from 'rxjs/operators';

import { IPage } from './page.model';
import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';

@Injectable({ providedIn: 'root' })
export class PageService {
  private resourceUrl = SERVER_API_URL + 'apis/blog/v1/blogs';

  constructor(private http: HttpClient) {}

  createPage(blogId: number, page: IPage): Observable<HttpResponse<IPage>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .post<IPage>(`${this.resourceUrl}/${blogId}/pages`, page, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  updatePage(blogId: number, page: IPage): Observable<HttpResponse<IPage>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .put<IPage>(`${this.resourceUrl}/${blogId}/pages`, page, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  patchPage(blogId: number, page: IPage, fields: string[]): Observable<HttpResponse<IPage>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Fields', fields);
    return this.http
      .patch<IPage>(`${this.resourceUrl}/${blogId}/pages`, page, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  getPages(blogId: number, req?: any): Observable<HttpResponse<IPage[]>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    const paramOptions = createRequestOption(req);
    return this.http
      .get<IPage[]>(`${this.resourceUrl}/${blogId}/pages`, { headers: headerOptions, params: paramOptions, observe: 'response' })
      .pipe(delay(100));
  }

  getPage(blogId: number, pageId: number): Observable<HttpResponse<IPage>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .get<IPage>(`${this.resourceUrl}/${blogId}/pages/${pageId}`, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  deletePage(blogId: number, pageId: number): Observable<HttpResponse<any>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .delete(`${this.resourceUrl}/${blogId}/pages/${pageId}`, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }
}

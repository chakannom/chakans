import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { delay } from 'rxjs/operators';

import { IPost } from '../';
import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';

@Injectable({ providedIn: 'root' })
export class PostService {
  private resourceUrl = SERVER_API_URL + 'apis/blog/v1/blogs';

  constructor(private http: HttpClient) {}

  createPost(blogId: number, post: IPost): Observable<HttpResponse<IPost>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .post<IPost>(`${this.resourceUrl}/${blogId}/posts`, post, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  updatePost(blogId: number, post: IPost): Observable<HttpResponse<IPost>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .put<IPost>(`${this.resourceUrl}/${blogId}/posts`, post, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  patchPost(blogId: number, post: IPost, fields: string[]): Observable<HttpResponse<IPost>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Fields', fields);
    return this.http
      .patch<IPost>(`${this.resourceUrl}/${blogId}/posts`, post, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  getPosts(blogId: number, req?: any): Observable<HttpResponse<IPost[]>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    const paramOptions = createRequestOption(req);
    return this.http.get<IPost[]>(`${this.resourceUrl}/${blogId}/posts`, {
      headers: headerOptions,
      params: paramOptions,
      observe: 'response'
    });
  }

  getPost(blogId: number, postId: number): Observable<HttpResponse<IPost>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .get<IPost>(`${this.resourceUrl}/${blogId}/posts/${postId}`, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }

  deletePost(blogId: number, postId: number): Observable<HttpResponse<any>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http
      .delete(`${this.resourceUrl}/${blogId}/posts/${postId}`, { headers: headerOptions, observe: 'response' })
      .pipe(delay(100));
  }
}

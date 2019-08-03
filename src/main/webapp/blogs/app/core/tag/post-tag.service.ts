import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { delay } from 'rxjs/operators';

import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';
import { IPostTag } from './post-tag.model';

@Injectable({ providedIn: 'root' })
export class PostTagService {
  private resourceUrl = SERVER_API_URL + 'apis/blog/v1/blogs';

  constructor(private http: HttpClient) {}

  getPostTags(blogId: number, req?: any): Observable<HttpResponse<IPostTag[]>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    const paramOptions = createRequestOption(req);
    return this.http
      .get<IPostTag[]>(`${this.resourceUrl}/${blogId}/posts/tags`, { headers: headerOptions, params: paramOptions, observe: 'response' })
      .pipe(delay(100));
  }
}

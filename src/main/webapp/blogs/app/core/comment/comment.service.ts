import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { delay } from 'rxjs/operators';

import { IComment } from '../';
import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';
import { createRequestOption } from '../../shared/util/request-util';

@Injectable({ providedIn: 'root' })
export class CommentService {
    private resourceUrl = SERVER_API_URL + 'apis/blog/v1/blogs';

    constructor(private http: HttpClient) {}

    patchComment(blogId: number, comment: IComment, fields: string[]): Observable<HttpResponse<IComment>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER).set('Fields', fields);
        return this.http
            .patch<IComment>(`${this.resourceUrl}/${blogId}/comments`, comment, { headers: headers, observe: 'response' })
            .pipe(delay(100));
    }

    getComments(blogId: number, req?: any): Observable<HttpResponse<IComment[]>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        const options = createRequestOption(req);
        return this.http
            .get<IComment[]>(`${this.resourceUrl}/${blogId}/comments`, { headers: headers, params: options, observe: 'response' })
            .pipe(delay(100));
    }

    deleteComment(blogId: number, commentId: number): Observable<HttpResponse<any>> {
        const headers = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
        return this.http
            .delete(`${this.resourceUrl}/${blogId}/comments/${commentId}`, { headers: headers, observe: 'response' })
            .pipe(delay(100));
    }
}

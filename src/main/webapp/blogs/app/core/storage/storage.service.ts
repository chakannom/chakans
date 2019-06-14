import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { SERVER_API_URL } from '../../app.constants';
import { HEADER_ACCEPT_BLOG_V1_USER } from '../../shared/constants/http.constants';

@Injectable({ providedIn: 'root' })
export class StorageService {
  private resourceMyUrl = SERVER_API_URL + 'apis/blog/v1/storages';

  constructor(private http: HttpClient) {}

  getMyDefaultPresignedPutUrl(filenameValue: string): Observable<HttpResponse<any>> {
    const headerOptions = new HttpHeaders().set('Accept', HEADER_ACCEPT_BLOG_V1_USER);
    return this.http.get(`${this.resourceMyUrl}/default/presigned-put-url`, {
      headers: headerOptions,
      params: { filename: filenameValue },
      observe: 'response'
    });
  }

  uploadImageFile(file): Observable<HttpResponse<any>> {
    return this.getMyDefaultPresignedPutUrl(file.name).pipe(
      mergeMap((response: any & { body: { url: string } }) => this.http.put(response.body.url, file, { observe: 'response' }))
    );
  }
}

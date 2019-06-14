import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageService, PostService } from '../../../../core';

@Injectable({ providedIn: 'root' })
export class EditorService {
  constructor(private pageService: PageService, private postService: PostService) {}

  createContent(editorType: string, blogId: number, content: any): Observable<HttpResponse<any>> {
    if (editorType === 'page') {
      return this.pageService.createPage(blogId, content);
    } else {
      return this.postService.createPost(blogId, content);
    }
  }

  updateContent(editorType: string, blogId: number, content: any): Observable<HttpResponse<any>> {
    if (editorType === 'page') {
      return this.pageService.updatePage(blogId, content);
    } else {
      return this.postService.updatePost(blogId, content);
    }
  }

  patchContent(editorType: string, blogId: number, content: any, fields: string[]): Observable<HttpResponse<any>> {
    if (editorType === 'page') {
      return this.pageService.patchPage(blogId, content, fields);
    } else {
      return this.postService.patchPost(blogId, content, fields);
    }
  }

  getContent(editorType: string, blogId: number, editorId: number): Observable<HttpResponse<any>> {
    if (editorType === 'page') {
      return this.pageService.getPage(blogId, editorId);
    } else {
      return this.postService.getPost(blogId, editorId);
    }
  }
}

import { Injectable } from '@angular/core';
import { Route, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';

import { BlogService, UserProfileRouteAccessService, BlogRouteAccessService } from '../../core';
import {
  BlogComponent,
  BlogMakerComponent,
  BlogWelcomeComponent,
  BlogViewBlogComponent,
  BlogPagesComponent,
  BlogPostsComponent,
  BlogCommentsComponent,
  BlogEditorComponent,
  BlogBasicSettingsComponent,
  BlogOtherSettingsComponent,
  BlogUserSettingsComponent
} from './';

@Injectable({ providedIn: 'root' })
export class BlogResolveBlogIdParam implements Resolve<any> {
  constructor(private blogService: BlogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const blogId = route.queryParams['blogId'] ? route.queryParams['blogId'] : null;
    return this.blogService.list().then(blogs => {
      if (blogs.length > 0) {
        const blog = blogs.find(b => b.id.toString() === blogId);
        return blog ? blog.id : blogs[0].id;
      }
      return null;
    });
  }
}

export const BLOG_ROUTE: Route = {
  path: 'blog.cb',
  component: BlogComponent,
  resolve: {
    blogIdParam: BlogResolveBlogIdParam,
    pagingParams: JhiResolvePagingParams
  },
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'blog.title',
    mainComponents: {
      emptyBlog: {
        maker: BlogMakerComponent,
        welcome: BlogWelcomeComponent,
        usersettings: BlogUserSettingsComponent
      },
      existBlog: {
        maker: BlogMakerComponent,
        viewblog: BlogViewBlogComponent,
        pages: BlogPagesComponent,
        posts: BlogPostsComponent,
        comments: BlogCommentsComponent,
        editor: BlogEditorComponent,
        basicsettings: BlogBasicSettingsComponent,
        othersettings: BlogOtherSettingsComponent,
        usersettings: BlogUserSettingsComponent
      }
    }
  },
  canActivate: [UserProfileRouteAccessService, BlogRouteAccessService]
};

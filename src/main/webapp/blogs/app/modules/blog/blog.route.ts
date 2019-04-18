import { Route } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';

import { UserRouteAccessService, UserProfileRouteAccessService, BlogRouteAccessService } from '../../core';
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

export const BLOG_ROUTE: Route = {
    path: 'blog.cb',
    component: BlogComponent,
    resolve: {
        pagingParams: JhiResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'blog.title',
        mainComps: {
            noblog: {
                maker: BlogMakerComponent,
                welcome: BlogWelcomeComponent,
                usersettings: BlogUserSettingsComponent
            },
            existblog: {
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
    canActivate: [UserRouteAccessService, UserProfileRouteAccessService, BlogRouteAccessService]
};

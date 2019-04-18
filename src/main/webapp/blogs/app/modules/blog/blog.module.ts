import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EditorModule } from 'chakannom-tinymce-angular';

import { BlogsSharedModule } from '../../shared';
import { BlogCommonsModule } from './commons';
import { BlogSidebarService } from './sidebar/sidebar.service';

import {
    BLOG_ROUTE,
    BlogComponent,
    BlogSidebarComponent,
    BlogMakerComponent,
    BlogWelcomeComponent,
    BlogViewBlogComponent,
    BlogPagesComponent,
    BlogPostsComponent,
    BlogCommentsComponent,
    BlogEditorComponent,
    BlogEditorRestoreModalComponent,
    BlogBasicSettingsComponent,
    BlogOtherSettingsComponent,
    BlogUserSettingsComponent
} from './';

@NgModule({
    imports: [EditorModule, BlogsSharedModule, BlogCommonsModule, RouterModule.forChild([BLOG_ROUTE])],
    declarations: [
        BlogComponent,
        BlogSidebarComponent,
        BlogMakerComponent,
        BlogWelcomeComponent,
        BlogViewBlogComponent,
        BlogPagesComponent,
        BlogPostsComponent,
        BlogCommentsComponent,
        BlogEditorComponent,
        BlogEditorRestoreModalComponent,
        BlogBasicSettingsComponent,
        BlogOtherSettingsComponent,
        BlogUserSettingsComponent
    ],
    entryComponents: [
        BlogMakerComponent,
        BlogWelcomeComponent,
        BlogViewBlogComponent,
        BlogPagesComponent,
        BlogPostsComponent,
        BlogCommentsComponent,
        BlogEditorComponent,
        BlogEditorRestoreModalComponent,
        BlogBasicSettingsComponent,
        BlogOtherSettingsComponent,
        BlogUserSettingsComponent
    ],
    providers: [BlogSidebarService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogsBlogModule {}

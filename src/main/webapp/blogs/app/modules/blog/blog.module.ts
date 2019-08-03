import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EditorModule } from 'chakannom-tinymce-angular';
import { BlogsSharedModule } from '../../shared';
import { BlogCommonModule } from './common';
import {
  BLOG_ROUTE,
  BlogComponent,
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
  imports: [EditorModule, BlogsSharedModule, BlogCommonModule, RouterModule.forChild([BLOG_ROUTE])],
  declarations: [
    BlogComponent,
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
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogsBlogModule {}

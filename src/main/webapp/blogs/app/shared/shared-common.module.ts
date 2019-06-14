import { NgModule } from '@angular/core';

import { BlogsSharedLibsModule, EllipsisPipe, FindLanguageFromKeyPipe, PrettyDatePipe, AlertComponent, AlertErrorComponent } from './';

@NgModule({
  imports: [BlogsSharedLibsModule],
  declarations: [EllipsisPipe, FindLanguageFromKeyPipe, PrettyDatePipe, AlertComponent, AlertErrorComponent],
  exports: [BlogsSharedLibsModule, EllipsisPipe, FindLanguageFromKeyPipe, PrettyDatePipe, AlertComponent, AlertErrorComponent]
})
export class BlogsSharedCommonModule {}

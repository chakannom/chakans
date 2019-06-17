import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BlogsSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BlogsSharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  entryComponents: [],
  exports: [BlogsSharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogsSharedModule {
  static forRoot() {
    return {
      ngModule: BlogsSharedModule
    };
  }
}

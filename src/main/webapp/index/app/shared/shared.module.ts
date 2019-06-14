import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { IndexSharedLibsModule, IndexSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [IndexSharedLibsModule, IndexSharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  entryComponents: [],
  exports: [IndexSharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IndexSharedModule {
  static forRoot() {
    return {
      ngModule: IndexSharedModule
    };
  }
}

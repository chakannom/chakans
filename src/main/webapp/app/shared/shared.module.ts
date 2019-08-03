import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ChakansSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ChakansSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ChakansSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChakansSharedModule {
  static forRoot() {
    return {
      ngModule: ChakansSharedModule
    };
  }
}

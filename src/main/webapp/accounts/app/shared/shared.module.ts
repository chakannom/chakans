import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AccountsSharedLibsModule, AccountsSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AccountsSharedLibsModule, AccountsSharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  entryComponents: [],
  exports: [AccountsSharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountsSharedModule {
  static forRoot() {
    return {
      ngModule: AccountsSharedModule
    };
  }
}

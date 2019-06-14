import { NgModule } from '@angular/core';

import { AccountsSharedLibsModule, FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent } from './';

@NgModule({
  imports: [AccountsSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent],
  exports: [AccountsSharedLibsModule, FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent]
})
export class AccountsSharedCommonModule {}

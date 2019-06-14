import { NgModule } from '@angular/core';

import { IndexSharedLibsModule, FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent } from './';

@NgModule({
  imports: [IndexSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent],
  exports: [IndexSharedLibsModule, FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent]
})
export class IndexSharedCommonModule {}

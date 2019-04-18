import { NgModule } from '@angular/core';

import {
    BlogsSharedLibsModule,
    FindLanguageFromKeyPipe,
    PrettyDatePipe,
    EllipsisPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

@NgModule({
    imports: [BlogsSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, PrettyDatePipe, EllipsisPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [BlogsSharedLibsModule, FindLanguageFromKeyPipe, PrettyDatePipe, EllipsisPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BlogsSharedCommonModule {}

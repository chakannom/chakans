import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { BlogsSharedLibsModule, BlogsSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [BlogsSharedLibsModule, BlogsSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [
        {
            provide: NgbDateAdapter,
            useClass: NgbDateMomentAdapter
        }
    ],
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

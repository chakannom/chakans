import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { IndexSharedLibsModule, IndexSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [IndexSharedLibsModule, IndexSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [
        {
            provide: NgbDateAdapter,
            useClass: NgbDateMomentAdapter
        }
    ],
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

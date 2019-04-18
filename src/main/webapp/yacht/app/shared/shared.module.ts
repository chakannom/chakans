import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { YachtSharedLibsModule, YachtSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [YachtSharedLibsModule, YachtSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [],
    exports: [YachtSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YachtSharedModule {
    static forRoot() {
        return {
            ngModule: YachtSharedModule
        };
    }
}

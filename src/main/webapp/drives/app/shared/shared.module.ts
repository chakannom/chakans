import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { DrivesSharedLibsModule, DrivesSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [DrivesSharedLibsModule, DrivesSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [
        {
            provide: NgbDateAdapter,
            useClass: NgbDateMomentAdapter
        }
    ],
    entryComponents: [],
    exports: [DrivesSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DrivesSharedModule {
    static forRoot() {
        return {
            ngModule: DrivesSharedModule
        };
    }
}

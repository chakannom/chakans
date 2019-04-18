import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { AccountsSharedLibsModule, AccountsSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [AccountsSharedLibsModule, AccountsSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [
        {
            provide: NgbDateAdapter,
            useClass: NgbDateMomentAdapter
        }
    ],
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

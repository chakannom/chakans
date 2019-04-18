import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccountsSharedModule } from '../../shared';

import { PasswordChangeComponent, PasswordStrengthBarComponent, SettingsComponent, myState } from './';

@NgModule({
    imports: [
        AccountsSharedModule,
        RouterModule.forChild(myState)
    ],
    declarations: [
        PasswordChangeComponent,
        PasswordStrengthBarComponent,
        SettingsComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountsMyModule {}

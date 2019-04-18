import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccountsSharedModule } from '../../shared';

import { ActivateComponent, PasswordResetFinishComponent, PasswordResetInitComponent, SignInComponent, SignUpResultComponent, SignUpComponent, internalState } from './';

@NgModule({
    imports: [
        AccountsSharedModule,
        RouterModule.forChild(internalState)
    ],
    declarations: [
        ActivateComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SignInComponent,
        SignUpResultComponent,
        SignUpComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountsInternalModule {}

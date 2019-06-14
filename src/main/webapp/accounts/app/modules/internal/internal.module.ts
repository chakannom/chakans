import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccountsSharedModule } from '../../shared';

import {
  SignInComponent,
  SignUpComponent,
  SignUpResultComponent,
  ActivateComponent,
  PasswordResetInitComponent,
  PasswordResetFinishComponent,
  INTERNAL_ROUTES
} from './';

@NgModule({
  imports: [AccountsSharedModule, RouterModule.forChild(INTERNAL_ROUTES)],
  declarations: [
    ActivateComponent,
    SignInComponent,
    SignUpResultComponent,
    SignUpComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountsInternalModule {}

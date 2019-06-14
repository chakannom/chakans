import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccountsSharedModule } from '../../shared';

import { PasswordChangeComponent, PasswordStrengthBarComponent, SettingsComponent, SELF_ROUTES } from './';

@NgModule({
  imports: [AccountsSharedModule, RouterModule.forChild(SELF_ROUTES)],
  declarations: [PasswordChangeComponent, PasswordStrengthBarComponent, SettingsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountsSelfModule {}

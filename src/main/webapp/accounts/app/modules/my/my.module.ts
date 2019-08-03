import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AccountsSharedModule } from '../../shared';
import { MyComponent, MyHomeComponent, PasswordChangeComponent, PasswordStrengthBarComponent, SettingsComponent, MY_ROUTES } from './';

@NgModule({
  imports: [AccountsSharedModule, RouterModule.forChild(MY_ROUTES)],
  declarations: [MyComponent, MyHomeComponent, PasswordChangeComponent, PasswordStrengthBarComponent, SettingsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountsMyModule {}

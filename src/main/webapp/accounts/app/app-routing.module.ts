import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ERROR_ROUTES, NAVBAR_ROUTE } from './layouts';
import { DEBUG_INFO_ENABLED } from './app.constants';

const LAYOUT_ROUTES = [...ERROR_ROUTES, NAVBAR_ROUTE];

@NgModule({
  imports: [RouterModule.forRoot([...LAYOUT_ROUTES], { useHash: false, enableTracing: DEBUG_INFO_ENABLED })],
  exports: [RouterModule]
})
export class AccountsAppRoutingModule {}

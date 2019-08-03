import { Route } from '@angular/router';
import { NavbarComponent } from './navbar.component';

export const NAVBAR_ROUTE: Route = {
  path: '',
  component: NavbarComponent,
  outlet: 'navbar'
};

import { Routes } from '@angular/router';
import { MyComponent } from './my.component';
import { MY_HOME_ROUTE, PASSWORD_CHANGE_ROUTE, SETTINGS_ROUTE } from './';

export const MY_ROUTES: Routes = [
  {
    path: 'my',
    component: MyComponent,
    children: [MY_HOME_ROUTE, PASSWORD_CHANGE_ROUTE, SETTINGS_ROUTE]
  }
];

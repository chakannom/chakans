import { Route } from '@angular/router';
import { MyHomeComponent } from './home.component';

export const MY_HOME_ROUTE: Route = {
  path: '',
  component: MyHomeComponent,
  data: {
    authorities: [],
    pageTitle: 'my.home.title'
  }
};

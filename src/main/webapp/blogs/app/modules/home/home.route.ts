import { Route } from '@angular/router';

import { HomeRouteAccessService } from '../../core';
import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    },
    canActivate: [HomeRouteAccessService]
};

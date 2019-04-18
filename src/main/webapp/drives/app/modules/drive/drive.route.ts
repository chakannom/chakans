import { Route } from '@angular/router';

import { DriveComponent } from './';

export const DRIVE_ROUTE: Route = {
    path: 'drive',
    component: DriveComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'drive.title'
    },
    canActivate: []
};

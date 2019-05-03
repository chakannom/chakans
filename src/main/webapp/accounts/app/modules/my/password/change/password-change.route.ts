import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../../core';
import { PasswordChangeComponent } from './password-change.component';

export const passwordChangeRoute: Route = {
    path: 'password/change',
    component: PasswordChangeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'global.menu.account.password'
    },
    canActivate: [UserRouteAccessService]
};
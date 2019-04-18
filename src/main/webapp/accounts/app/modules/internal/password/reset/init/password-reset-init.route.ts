import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../../../../core';
import { PasswordResetInitComponent } from './password-reset-init.component';

export const passwordResetInitRoute: Route = {
    path: 'password/reset/init',
    component: PasswordResetInitComponent,
    data: {
        authorities: [],
        pageTitle: 'global.menu.account.password'
    },
    canActivate: [UserRouteAccessService]
};

import { Routes } from '@angular/router';

import {
    activateRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    signInRoute,
    signUpResultRoute,
    signUpRoute
} from './';

const INTERNAL_ROUTES = [
    activateRoute,
    passwordResetFinishRoute,
    passwordResetInitRoute,
    signInRoute,
    signUpResultRoute,
    signUpRoute
];

export const internalState: Routes = [{
    path: '',
    children: INTERNAL_ROUTES
}];

import { Routes } from '@angular/router';

import {
    passwordChangeRoute,
    settingsRoute
} from './';

const MY_ROUTES = [
    passwordChangeRoute,
    settingsRoute
];

export const myState: Routes = [{
    path: 'my',
    children: MY_ROUTES
}];

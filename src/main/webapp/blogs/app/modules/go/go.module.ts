import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogsSharedModule } from '../../shared';

import { GO_ROUTE, GoComponent } from './';

@NgModule({
    imports: [
        BlogsSharedModule,
        RouterModule.forChild([ GO_ROUTE ])
    ],
    declarations: [
        GoComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogsGoModule {}

import { NgModule } from '@angular/core';

import { BlogsSharedLibsModule } from '../../../shared';

import {
    PopoverComponent
} from './';

@NgModule({
    imports: [
        BlogsSharedLibsModule
    ],
    declarations: [
        PopoverComponent
    ],
    providers: [
    ],
    exports: [
        PopoverComponent
    ]
})
export class BlogCommonsModule {}
